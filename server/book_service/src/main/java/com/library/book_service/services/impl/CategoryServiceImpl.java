package com.library.book_service.services.impl;

import com.library.book_service.dtos.requests.CategoryRequest;
import com.library.book_service.dtos.responses.CategoryResponse;
import com.library.book_service.entities.Category;
import com.library.book_service.repositories.CategoryRepo;
import com.library.book_service.services.CategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    CategoryRepo categoryRepo;

    @Override
    public Category findById(Long id){
        return categoryRepo.findById(id).get();
    }

    @Override
    public Category findByCategory(String name){
        return categoryRepo.findByCategory(name);
    }

    @Override
    public List<Category> findByIds(List<CategoryRequest> requests){
        List<Category> categories = new ArrayList<>();
        for(CategoryRequest request : requests){
            categories.add(categoryRepo.findById(request.getId()).get());
        }
        return categories;
    }

    @Override
    public List<CategoryResponse> getALl(){
        return categoryRepo.findAll().stream().map(this::toCategoryResponse).collect(Collectors.toList());
    }

    @Override
    public CategoryResponse createCategory(CategoryRequest request){
        return toCategoryResponse(categoryRepo.save(toCategory(request)));
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
                .collect(Collectors.toList());
    }

    @Override
    public Category toCategory(CategoryRequest request){
        return Category.builder()
                .category(request.getCategory())
                .id(request.getId())
                .build();
    }
}
