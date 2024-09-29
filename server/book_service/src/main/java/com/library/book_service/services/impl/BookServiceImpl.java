package com.library.book_service.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.library.book_service.dtos.requests.BookRequest;
import com.library.book_service.dtos.requests.NewBookRequest;
import com.library.book_service.dtos.responses.BookResponse;
import com.library.book_service.dtos.responses.PageResponse;
import com.library.book_service.entities.Book;
import com.library.book_service.exceptions.AppException;
import com.library.book_service.exceptions.ErrorCode;
import com.library.book_service.repositories.BookRepo;
import com.library.book_service.repositories.httpclient.AmazonS3Client;
import com.library.book_service.services.BookRedisService;
import com.library.book_service.services.BookService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class BookServiceImpl implements BookService{
    BookRepo bookRepo;
    CategoryServiceImpl categoryService;
    AmazonS3Client amazonS3Client;
    BookRedisService bookRedisService;

    @Override
    public PageResponse<BookResponse> search(String name, Long size, Long page){
        Pageable pageable = PageRequest.of(Math.toIntExact(size), Math.toIntExact(page));
        Page<Book> books = bookRepo.searchBookBy(name, pageable);
        return PageResponse.<BookResponse>builder()
                .totalPages(books.getTotalPages())
                .content(toBookResponses(books.getContent()))
                .pageSize(books.getSize())
                .totalElements(books.getTotalElements())
                .pageNumber(books.getNumber())
                .build();
    }

    @Override
    public Boolean returnBook(List<Long> bookIds){
        for(Long id : bookIds){
            returnBook(id);
        }
        return true;
    }

    @Override
    public void returnBook(Long id){
        Book book = bookRepo.findById(id).get();
        book.setNumber(book.getNumber() + 1);
        bookRepo.save(book);
    }

    @Override
    public Boolean borrow(List<Long> ids){
        for(Long id : ids){
            borrow(id);
        }
        return true;
    }

    @Override
    public void borrow(Long id){
        Book book = bookRepo.findById(id).get();
        book.setNumber(book.getNumber() - 1);
        bookRepo.save(book);
    }

    @Override
    public List<Long> getNumbers(List<Long> ids) throws JsonProcessingException {
        if(bookRedisService.getNumbers(ids) != null){
            return bookRedisService.getNumbers(ids);
        }
        List<Book> books = bookRepo.findAll(ids);
        List<Long> id = new ArrayList<>();
        for (Book book : books) id.add(book.getId());
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
    public List<BookResponse> getById(List<Long> id) throws JsonProcessingException, AppException {
        if(bookRedisService.get(id) != null){
            return bookRedisService.get(id);
        }
        List<Book> book = bookRepo.findAll(id);
        if(book.isEmpty()) throw new AppException(ErrorCode.NOT_EXIST_BOOK);
        List<BookResponse> response = book.stream().map(this::toBookResponse).toList();
        bookRedisService.saveGetBook(id,response);
        return response;
    }

    @Override
    public BookResponse getById(Long id) throws JsonProcessingException, AppException {
        if(bookRedisService.get(id) != null){
            return bookRedisService.get(id);
        }
        Optional<Book> book = bookRepo.findById(id);
        if(book.isEmpty()) throw new AppException(ErrorCode.NOT_EXIST_BOOK);
        BookResponse response = toBookResponse(book.get());
        bookRedisService.saveGetBook(id,response);
        return response;
    }

    //Update Book
    @Override
    public BookResponse updateBook(Long id, NewBookRequest updated){
        Optional<Book> optionalBook = bookRepo.findById(id);
        if (optionalBook.isEmpty())
            throw new RuntimeException("Book not found with id: " + id);
        Book book = optionalBook.get();
        book.setBookCode(updated.getBookCode());
        book.setCategories(categoryService.toCategories(updated.getCategories()));
        book.setAuthor(updated.getAuthor());
        book.setEdition(updated.getEdition());
        book.setNumberPage(updated.getNumberPage());
        book.setName(updated.getName());
        book.setNumber(updated.getNumber());
        book.setPrice(updated.getPrice());
        book.setPublicationDate(updated.getPublicationDate());
        book.setShortDescription(updated.getShortDescription());
        if(updated.getImage() != null){
            book.setImageUrl(amazonS3Client.uploadImage(updated.getImage()));
        }
        return toBookResponse(bookRepo.save(book));
    }

    @Override
    public void deleteBook(Long id){
        bookRepo.deleteById(id);
    }

    //CreateBook
    @Override
    public BookResponse createBook(NewBookRequest request){
        Book book = toBook(request);
        book.setImageUrl(amazonS3Client.uploadImage(request.getImage()));
        return toBookResponse(bookRepo.save(book));
    }

    @Override
    public List<BookResponse> toBookResponses(List<Book> books){
        return books.stream().map(this::toBookResponse).collect(Collectors.toList());
    }

    //convert NewBookRequest to Book
    @Override
    public Book toBook(NewBookRequest request){
        return Book.builder()
                .number(request.getNumber())
                .id(request.getId())
                .bookCode(request.getBookCode())
                .name(request.getName())
                .author(request.getAuthor())
                .categories(categoryService.toCategories(request.getCategories()))
                .price(request.getPrice())
                .language(request.getLanguage())
                .shortDescription(request.getShortDescription())
                .numberPage(request.getNumberPage())
                .publicationDate(request.getPublicationDate())
                .edition(request.getEdition())
                .build();
    }

    @Override
    public Book toBook(BookRequest request){
        return Book.builder()
                .id(request.getId())
                .bookCode(request.getBookCode())
                .name(request.getName())
                .author(request.getAuthor())
                .number(request.getNumber())
                .categories(categoryService.toCategories(request.getCategories()))
                .imageUrl(request.getImageUrl())
                .price(request.getPrice())
                .language(request.getLanguage())
                .shortDescription(request.getShortDescription())
                .numberPage(request.getNumberPage())
                .publicationDate(request.getPublicationDate())
                .edition(request.getEdition())
                .build();
    }

    @Override
    public BookResponse toBookResponse(Book request){
        return BookResponse.builder()
                .id(request.getId())
                .bookCode(request.getBookCode())
                .name(request.getName())
                .number(request.getNumber())
                .author(request.getAuthor())
                .categories(categoryService.toCategoryResponses(request.getCategories()))
                .price(request.getPrice())
                .imageUrl(request.getImageUrl())
                .language(request.getLanguage())
                .shortDescription(request.getShortDescription())
                .numberPage(request.getNumberPage())
                .publicationDate(request.getPublicationDate())
                .edition(request.getEdition())
                .build();
    }
}
