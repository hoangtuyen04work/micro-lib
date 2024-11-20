package com.library.book_service.util;

import com.library.book_service.dtos.requests.BookRequest;
import com.library.book_service.dtos.requests.CategoryRequest;
import com.library.book_service.dtos.requests.NewBookRequest;
import com.library.book_service.dtos.responses.*;
import com.library.book_service.entities.Book;
import com.library.book_service.entities.Category;
import org.springframework.data.domain.Page;

import java.util.List;

public interface Mapping {
    void toBook(Book book, NewBookRequest updated, String imageUrl);

    PageResponse<BookSimpleResponse> toPagePageBookSimpleResponse(Page<Book> books);

    BookSimpleResponse toBookSimpleResponse(Book request);

    List<BookResponse> toBookResponses(List<Book> books);

    PageResponse<BookResponseSimple> toPageResponseSimple(Page<Book> books);

    PageResponse<BookResponse> toPageResponse(Page<Book> books);

    List<BookResponseSimple> toBookResponseSimple(List<Book> request);

    PageResponse<BookResponseSimple> toBookResponseSimple(Page<Book> request);

    BookResponseSimple toBookResponseSimple(Book request);

    BookResponse toBookResponse(Book book, NewBookRequest updated, String imageUrl);

    //convert NewBookRequest to Book
    Book toBook(NewBookRequest request);

    BookResponse toBookResponse(Book request);

    Book toBook(BookRequest request);

    List<CategoryResponse> toCategoryResponses(List<Category> categories);

    CategoryResponse toCategoryResponse(Category category);

    List<Category> toCategories(List<CategoryRequest> request);

    Category toCategory(CategoryRequest request);
}
