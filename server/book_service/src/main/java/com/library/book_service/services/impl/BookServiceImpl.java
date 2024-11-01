package com.library.book_service.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.library.kafkaObject.BorrowNotificationRequest;
import com.library.book_service.dtos.requests.NewBookRequest;
import com.library.kafkaObject.ReturnNotificationRequest;
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
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    KafkaTemplate<String, ReturnNotificationRequest> kafkaReturn;
    KafkaTemplate<String, BorrowNotificationRequest> kafkaBorrow;
    @NonFinal
    @Value("${kafka.return}")
    String returnTopic;
    @Value("${kafka.borrow}")
    @NonFinal
    String borrowTopic;

    @Override
    public PageResponse<BookResponseSimple> getTop(Integer size, Integer page, Integer typeId) throws JsonProcessingException {
        PageResponse<BookResponseSimple> responses = bookRedisService.getTop(typeId, size, page);
        if(responses != null) return responses;
       Pageable pageable = PageRequest.of(Math.toIntExact(page - 1), Math.toIntExact(size), Sort.by("numberBorrowed").descending());
       PageResponse<BookResponseSimple> response;
       if(typeId == 0){
           response =  mapping.toBookResponseSimple(bookRepo.findAll(pageable));
           bookRedisService.saveGetTop(typeId, size, page, response);
           return response;
       }
       else{
           response =  mapping.toBookResponseSimple(bookRepo.findByCategoriesIn(List.of(categoryService.findById(Long.valueOf(typeId))), pageable));
           bookRedisService.saveGetTop(typeId, size, page, response);
           return response;
       }
    }

    @Override
    public List<BookResponse> getAll() throws JsonProcessingException {
        List<BookResponse> responses;
        responses = bookRedisService.getAll();
        if(responses != null) return responses;
        responses = bookRepo.findAll().stream().map(mapping::toBookResponse).toList();
        bookRedisService.saveGetAll(responses);
        return responses;
    }

    @Override
    public PageResponse<BookResponseSimple> search(String name, Integer size, Integer page) throws JsonProcessingException {
        PageResponse<BookResponseSimple> responses;
        responses = bookRedisService.search(name, size, page);
        if(responses != null) return responses;
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Book> books = bookRepo.findByNameContaining(name, pageable);
        PageResponse<BookResponseSimple> response = mapping.toPageResponseSimple(books);
        bookRedisService.saveSearch(name, size, page, response);
        return response;
    }

    @Override
    public Boolean returnBook(List<Long> bookIds, Long userId){
        List<String> name = new ArrayList<>();
        for(Long id : bookIds){
            name.add(returnBook(id, userId));
        }
        kafkaBorrow.send(borrowTopic, BorrowNotificationRequest.builder()
                        .bookName(name)
                        .userId(userId)
                        .borrowTime(LocalDate.now())
                .build());
        return true;
    }

    @Override
    public String returnBook(Long id, Long userId){
        Book book = bookRepo.findById(id).get();
        Long number = book.getNumber();
        book.setNumber(number + 1);
        bookRepo.save(book);
        return book.getName();
    }

    @Override
    public Boolean borrow(List<Long> ids, Long userId){
        List<String> name = new ArrayList<>();
        for(Long id : ids){
            name.add(borrow(id, userId));
        }
        kafkaReturn.send(returnTopic, ReturnNotificationRequest.builder()
                        .bookName(name)
                        .userId(userId)
                        .borrowTime(LocalDate.now())
                .build());
        return true;
    }

    @Override
    public String borrow(Long id, Long userId){
        Book book = bookRepo.findById(id).get();
        book.setNumberBorrowed((book.getNumberBorrowed() == null ? 0 : book.getNumberBorrowed()) + 1);
        book.setNumber(book.getNumber() - 1);
        bookRepo.save(book);
        kafkaReturn.send(returnTopic, ReturnNotificationRequest.builder()
                .bookName(List.of(book.getName()))
                .userId(userId)
                .borrowTime(LocalDate.now())
                .build());
        return book.getName();
    }

    @Override
    public List<Long> getNumbers(List<Long> ids) throws JsonProcessingException {
        if(bookRedisService.getNumbers(ids) != null){
            return bookRedisService.getNumbers(ids);
        }
        List<Book> books = new ArrayList<>();
        for(Long id : ids){
            books.add(bookRepo.findById(id).get());
        }
        List<Long> id = new ArrayList<>();
        for (Book book : books) id.add(book.getNumber());
        bookRedisService.saveGetNumbers(ids, id);
        return id;
    }

    @Override
    public Long getNumberById(Long id) throws JsonProcessingException {
        if(bookRedisService.getNumberById(id) != null){
            return bookRedisService.getNumberById(id);
        }
        Long number = bookRepo.findById(id).get().getNumber();
        bookRedisService.saveGetNumberById(id, number);
        return number;
    }

    @Override
    public List<BookResponse> getById(List<Long> ids) throws JsonProcessingException, AppException {
        if(bookRedisService.get(ids) != null){
            return bookRedisService.get(ids);
        }
        List<Book> book = new ArrayList<>();
        for (Long id : ids){
            book.add(bookRepo.findById(id).get());
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
        if(book.isEmpty()) throw new AppException(ErrorCode.NOT_EXIST_BOOK);
        BookResponse response = mapping.toBookResponse(book.get());
        bookRedisService.saveGetBook(id,response);
        return response;
    }

    @Override
    public BookResponse updateBook(Long id, NewBookRequest updated){
        Optional<Book> optionalBook = bookRepo.findById(id);
        if (optionalBook.isEmpty())
            throw new RuntimeException("Book not found with id: " + id);
        String imageUrl = null;
        if(updated.getImage() != null){
            imageUrl = amazonS3Client.uploadImage(updated.getImage());
        }
        return mapping.toBookResponse(bookRepo.save(optionalBook.get()), updated, imageUrl);
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


}
