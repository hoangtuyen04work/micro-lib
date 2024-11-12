package com.library.book_service.controllers;

import com.library.book_service.dtos.ApiResponse;
import com.library.book_service.dtos.requests.CategoryRequest;
import com.library.book_service.dtos.responses.CategoryResponse;
import com.library.book_service.services.CategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {
    CategoryService categoryService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/new")
    public ApiResponse<CategoryResponse> createCategory(@RequestBody CategoryRequest categoryRequest) {
        return ApiResponse.<CategoryResponse>builder()
                .data(categoryService.createCategory(categoryRequest))
                .build();
    }

    @PreAuthorize("hasAuthority('AMDIN')")
    @DeleteMapping()
    public ApiResponse<Boolean> deleteCategory(@RequestParam(required = true) Long id){
        categoryService.delete(id);
        return ApiResponse.<Boolean>builder()
                .data(true)
                .build();
    }

    @GetMapping("/all")
    public ApiResponse<List<CategoryResponse>> getAllCategories() {
        return ApiResponse.<List<CategoryResponse>>builder()
                .data(categoryService.getALl())
                .build();
    }
}
