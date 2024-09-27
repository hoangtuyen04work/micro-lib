package com.library.book_service.services;

import com.library.book_service.dtos.requests.CategoryRequest;
import com.library.book_service.dtos.responses.CategoryResponse;
import com.library.book_service.entities.Category;

import java.util.List;

public interface CategoryService {
    List<CategoryResponse> getALl();

    CategoryResponse createCategory(CategoryRequest request);

    List<CategoryResponse> toCategoryResponses(List<Category> categories);

    CategoryResponse toCategoryResponse(Category category);

    List<Category> toCategories(List<CategoryRequest> request);

    Category toCategory(CategoryRequest request);
}
