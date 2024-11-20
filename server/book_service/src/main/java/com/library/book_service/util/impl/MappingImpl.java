package com.library.book_service.util.impl;

import com.library.book_service.dtos.requests.BookRequest;
import com.library.book_service.dtos.requests.CategoryRequest;
import com.library.book_service.dtos.requests.NewBookRequest;
import com.library.book_service.dtos.responses.*;
import com.library.book_service.entities.Book;
import com.library.book_service.entities.Category;
import com.library.book_service.util.Mapping;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MappingImpl implements Mapping {

    @Override
    public void toBook(Book book, NewBookRequest updated, String imageUrl){
        book.setBookCode(updated.getBookCode());
        book.setCategories(toCategories(updated.getCategories()));
        book.setAuthor(updated.getAuthor());
        book.setEdition(updated.getEdition());
        book.setNumberPage(updated.getNumberPage());
        book.setName(updated.getName());
        book.setNumber(updated.getNumber());
        book.setPrice(updated.getPrice());
        book.setPublicationDate(updated.getPublicationDate());
        book.setShortDescription(updated.getShortDescription());
        if(imageUrl != null){
            book.setImageUrl(imageUrl);
        }
    }

    @Override
    public PageResponse<BookSimpleResponse> toPagePageBookSimpleResponse(Page<Book> books){
        return PageResponse.<BookSimpleResponse>builder()
                .totalPages(books.getTotalPages())
                .content(books.getContent().stream().map(this::toBookSimpleResponse).toList())
                .pageSize(books.getSize())
                .totalElements(books.getTotalElements())
                .pageNumber(books.getNumber())
                .build();
    }

    @Override
    public BookSimpleResponse toBookSimpleResponse(Book request){
        return BookSimpleResponse.builder()
                .id(request.getId())
                .price(request.getPrice())
                .numberBorrowed(request.getNumberBorrowed())
                .name(request.getName())
                .author(request.getAuthor())
                .language(request.getLanguage())
                .build();
    }

    @Override
    public List<BookResponse> toBookResponses(List<Book> books){
        return books.stream().map(this::toBookResponse).collect(Collectors.toList());
    }

    @Override
    public PageResponse<BookResponseSimple> toPageResponseSimple(Page<Book> books){
        return PageResponse.<BookResponseSimple>builder()
                .totalPages(books.getTotalPages())
                .content(toBookResponseSimple(books.getContent()))
                .pageSize(books.getSize())
                .totalElements(books.getTotalElements())
                .pageNumber(books.getNumber())
                .build();
    }

    @Override
    public PageResponse<BookResponse> toPageResponse(Page<Book> books){
        return PageResponse.<BookResponse>builder()
                .totalPages(books.getTotalPages())
                .content(toBookResponses(books.getContent()))
                .pageSize(books.getSize())
                .totalElements(books.getTotalElements())
                .pageNumber(books.getNumber())
                .build();
    }

    @Override
    public List<BookResponseSimple> toBookResponseSimple(List<Book> request){
        return request.stream().map(this::toBookResponseSimple).toList();
    }

    @Override
    public PageResponse<BookResponseSimple> toBookResponseSimple(Page<Book> request){
        return PageResponse.<BookResponseSimple>builder()
                .pageNumber(request.getNumber())
                .totalElements(request.getTotalElements())
                .pageSize(request.getSize())
                .content(request.getContent().stream().map(this::toBookResponseSimple).toList())
                .build();
    }

    @Override
    public BookResponseSimple toBookResponseSimple(Book request){
        return BookResponseSimple.builder()
                .id(request.getId())
                .imageUrl(request.getImageUrl())
                .name(request.getName())
                .numberBorrowed(request.getNumberBorrowed())
                .build();
    }

    @Override
    public BookResponse toBookResponse(Book book, NewBookRequest updated, String imageUrl){
        toBook(book, updated, imageUrl);
        return toBookResponse(book);
    }

    //convert NewBookRequest to Book
    @Override
    public Book toBook(NewBookRequest request){
        return Book.builder()
                .number(request.getNumber())
                .numberBorrowed(0L)
                .id(request.getId())
                .bookCode(request.getBookCode())
                .name(request.getName())
                .author(request.getAuthor())
                .categories(toCategories(request.getCategories()))
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
                .numberBorrowed(request.getNumberBorrowed())
                .bookCode(request.getBookCode())
                .name(request.getName())
                .number(request.getNumber())
                .author(request.getAuthor())
                .categories(toCategoryResponses(request.getCategories()))
                .price(request.getPrice())
                .imageUrl(request.getImageUrl())
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
                .categories(toCategories(request.getCategories()))
                .imageUrl(request.getImageUrl())
                .price(request.getPrice())
                .language(request.getLanguage())
                .shortDescription(request.getShortDescription())
                .numberPage(request.getNumberPage())
                .publicationDate(request.getPublicationDate())
                .edition(request.getEdition())
                .numberBorrowed(request.getNumberBorrowed())
                .build();
    }

    @Override
    public List<CategoryResponse> toCategoryResponses(List<Category> categories) {
        return categories.stream()
                .map(this::toCategoryResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponse toCategoryResponse(Category category){
        return CategoryResponse.builder()
                .category(category.getCategory())
                .id(category.getId())
                .build();
    }

    @Override
    public List<Category> toCategories(List<CategoryRequest> request){
        return request.stream()
                .map(this::toCategory)
                .toList();
    }

    @Override
    public Category toCategory(CategoryRequest request){
        return Category.builder()
                .category(request.getCategory())
                .id(request.getId())
                .build();
    }
}
