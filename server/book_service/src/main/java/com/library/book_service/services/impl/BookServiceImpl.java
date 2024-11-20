package com.library.book_service.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.library.book_service.dtos.responses.BookSimpleResponse;
import com.library.book_service.repositories.httpclient.BorrowClient;
import com.library.book_service.services.KafkaService;
import com.library.book_service.dtos.requests.NewBookRequest;
import com.library.book_service.dtos.responses.BookResponse;
import com.library.book_service.dtos.responses.BookResponseSimple;
import com.library.book_service.dtos.responses.PageResponse;
import com.library.book_service.entities.Book;
import com.library.book_service.exceptions.AppException;
import com.library.book_service.exceptions.ErrorCode;
import com.library.book_service.repositories.BookRepo;
import com.library.book_service.repositories.httpclient.AmazonS3Client;
import com.library.book_service.services.BookRedisService;
import com.library.book_service.services.BookService;
import com.library.book_service.services.CategoryService;
import com.library.book_service.util.Mapping;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class BookServiceImpl implements BookService{
    Mapping mapping;
    BookRepo bookRepo;
    CategoryService categoryService;
    AmazonS3Client amazonS3Client;
    BookRedisService bookRedisService;
    KafkaService kafkaService;
    BorrowClient borrowClient;

    @Override
    public PageResponse<BookSimpleResponse> getBookSimpleResponse(Integer page, Integer size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("numberBorrowed").descending());
        Page<Book> books = bookRepo.findAll(pageable);
        return mapping.toPagePageBookSimpleResponse(books);
    }

    @Override
    public PageResponse<BookResponse> getTopBorrow(Integer page, Integer size){
        PageResponse<Long> pages = borrowClient.getTopBooks(page, size).getData();
        return PageResponse.<BookResponse>builder()
                .content(getBooks(pages.getContent()))
                .pageSize(size)
                .totalElements(pages.getTotalElements())
                .pageNumber(pages.getPageNumber())
                .totalPages(pages.getTotalPages())
                .build();
    }

    @SneakyThrows
    @Override
    public List<BookResponse> getBooks(List<Long> bookIds){
        List<Book> books = new ArrayList<>();
        for(Long bookId : bookIds){
            Optional<Book> book = bookRepo.findById(bookId);
            book.ifPresent(books::add);
        }
        return mapping.toBookResponses(books);
    }

    @Override
    public PageResponse<BookResponseSimple> getTop(Integer size, Integer page, Integer typeId) throws JsonProcessingException {
        PageResponse<BookResponseSimple> responses = bookRedisService.getTop(typeId, size, page);
        if(responses != null) return responses;
        Pageable pageable = PageRequest.of(
               Math.toIntExact(page - 1), Math.toIntExact(size), Sort.by("numberBorrowed").descending());
        PageResponse<BookResponseSimple> response;
        if(typeId == 0){
           response =  mapping.toBookResponseSimple(bookRepo.findAll(pageable));
        }
        else{
           response =  mapping.toBookResponseSimple(bookRepo.findByCategoriesIn(
                   List.of(categoryService.findById(Long.valueOf(typeId))), pageable));
        }
        bookRedisService.saveGetTop(typeId, size, page, response);
        return response;
    }

    @Override
    public List<BookResponse> getAll(Integer page, Integer size, String sort) throws JsonProcessingException {
        List<BookResponse> responses = bookRedisService.getAll(page, size, sort);
        if(responses != null) return responses;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        responses = bookRepo.findAll(pageable).stream().map(mapping::toBookResponse).toList();
        bookRedisService.saveGetAll(responses, page, size, sort);
        return responses;
    }

    @Override
    public PageResponse<BookResponseSimple> search(String name, Integer size, Integer page) throws JsonProcessingException {
        PageResponse<BookResponseSimple> responses;
        responses = bookRedisService.search(name, size, page);
        if(responses != null) return responses;
        Pageable pageable = PageRequest.of(page, size);
        Page<Book> books = bookRepo.findByNameContaining(name, pageable);
        PageResponse<BookResponseSimple> response = mapping.toPageResponseSimple(books);
        bookRedisService.saveSearch(name, size, page, response);
        return response;
    }

    @Override
    public void returnBook(List<Long> bookIds, Long userId) throws AppException {
        for(Long id : bookIds){
            returnBook(id, userId);
        }
    }

    @Override
    public void returnBook(Long id, Long userId) throws AppException {
        Book book = findById(id);
        Long number = book.getNumber();
        book.setNumber(number + 1);
        bookRepo.save(book);
        kafkaService.returnNotify(id, userId, book.getName());
    }

    @Override
    public void borrow(List<Long> ids, Long userId) throws AppException {
        for(Long id : ids){
            borrow(id, userId);
        }
    }

    @Override
    public void borrow(Long id, Long userId) throws AppException {
        Book book = findById(id);
        book.setNumberBorrowed((book.getNumberBorrowed() == null ? 0 : book.getNumberBorrowed()) + 1);
        book.setNumber(book.getNumber() - 1);
        bookRepo.save(book);
        kafkaService.borrowNotify(id, userId, book.getName());
    }

    @Override
    public List<Long> getNumbers(List<Long> ids) throws JsonProcessingException, AppException {
        if(bookRedisService.getNumbers(ids) != null){
            return bookRedisService.getNumbers(ids);
        }
        List<Book> books = new ArrayList<>();
        for(Long id : ids){
            books.add(findById(id));
        }
        List<Long> id = books.stream().map(Book::getNumber).toList();
        bookRedisService.saveGetNumbers(ids, id);
        return id;
    }

    @Override
    public Long getNumberById(Long id) throws JsonProcessingException, AppException {
        if(bookRedisService.getNumberById(id) != null){
            return bookRedisService.getNumberById(id);
        }
        Book book = findById(id);
        Long number = book.getNumber();
        bookRedisService.saveGetNumberById(id, number);
        return number;
    }

    @Override
    public Book findById(Long id) throws AppException {
        return checkOptional(bookRepo.findById(id));
    }

    @Override
    public List<BookResponse> getByIds(List<Long> ids) throws JsonProcessingException, AppException {
        if(bookRedisService.get(ids) != null){
            return bookRedisService.get(ids);
        }
        List<Book> book = new ArrayList<>();
        for (Long id : ids){
            book.add(findById(id));
        }
        if(book.isEmpty()) throw new AppException(ErrorCode.NOT_EXIST_BOOK);
        List<BookResponse> response = book.stream().map(mapping::toBookResponse).toList();
        bookRedisService.saveGetBook(ids,response);
        return response;
    }

    @Override
    public BookResponse getById(Long id) throws JsonProcessingException, AppException {
        if(bookRedisService.get(id) != null){
            return bookRedisService.get(id);
        }
        Optional<Book> book = bookRepo.findById(id);
        BookResponse response = mapping.toBookResponse(checkOptional(book));
        bookRedisService.saveGetBook(id,response);
        return response;
    }

    @Override
    public BookResponse updateBook(Long id, NewBookRequest updated) throws AppException {
        Optional<Book> optionalBook = bookRepo.findById(id);
        String imageUrl = null;
        if(updated.getImage() != null){
            imageUrl = amazonS3Client.uploadImage(updated.getImage());
        }
        Book book = checkOptional(optionalBook);
        mapping.toBook(book, updated, imageUrl);
        return mapping.toBookResponse(bookRepo.save(book), updated, imageUrl);
    }

    @Override
    public void deleteBook(Long id){
        bookRepo.deleteById(id);
    }

    //CreateBook
    @Override
    public BookResponse createBook(NewBookRequest request){
        Book book = mapping.toBook(request);
        book.setCategories(categoryService.findByIds(request.getCategories()));
        book.setImageUrl(amazonS3Client.uploadImage(request.getImage()));
        book.setNumberBorrowed(0L);
        return mapping.toBookResponse(bookRepo.save(book));
    }

    @Override
    public Book checkOptional(Optional<Book> book) throws AppException {
        if(book.isEmpty()){
            throw new AppException(ErrorCode.NOT_EXIST_BOOK);
        }
        return book.get();
    }
}
