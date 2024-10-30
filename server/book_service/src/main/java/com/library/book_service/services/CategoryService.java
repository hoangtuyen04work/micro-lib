package com.library.book_service.services;

import com.library.book_service.dtos.requests.CategoryRequest;
import com.library.book_service.dtos.responses.CategoryResponse;
import com.library.book_service.entities.Category;

import java.util.List;

public interface CategoryService {
    Category findByCategory(String name);

    Category findById(Long id);

    List<Category> findByIds(List<CategoryRequest> requests);

    List<CategoryResponse> getALl();

    CategoryResponse createCategory(CategoryRequest request);
}
