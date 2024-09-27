package com.library.book_service.controllers;

import com.library.book_service.dtos.ApiResponse;
import com.library.book_service.dtos.requests.CategoryRequest;
import com.library.book_service.dtos.responses.CategoryResponse;
import com.library.book_service.services.CategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.DialectOverride;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {
    CategoryService categoryService;

    @PostMapping("/new")
    public ApiResponse<CategoryResponse> createCategory(@RequestBody CategoryRequest categoryRequest) {
        return ApiResponse.<CategoryResponse>builder()
                .data(categoryService.createCategory(categoryRequest))
                .build();
    }

    @GetMapping()
    public ApiResponse<List<CategoryResponse>> getAllCategories() {
        return ApiResponse.<List<CategoryResponse>>builder()
                .data(categoryService.getALl())
                .build();
    }
}
