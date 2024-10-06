package com.library.rate_service.controllers;

import com.library.rate_service.dtos.ApiResponse;
import com.library.rate_service.dtos.requests.RatingRequest;
import com.library.rate_service.dtos.responses.RatingResponse;
import com.library.rate_service.exceptions.AppException;
import com.library.rate_service.services.RatingService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rate")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RatingController {

    RatingService ratingService;

    @PostMapping()
    public ApiResponse<RatingResponse> rateBook(@RequestBody RatingRequest request) {
        return ApiResponse.<RatingResponse>builder()
                .data(ratingService.rateBook(request))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<RatingResponse> updateRating(@PathVariable Long id, @RequestBody RatingRequest request) {
        return ApiResponse.<RatingResponse>builder()
                .data(ratingService.updateRating(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteRating(@PathVariable Long id) {
        ratingService.deleteRating(id);
        return ApiResponse.<Void>builder()
                .data(null)
                .build();
    }

    @GetMapping("/book/{bookId}")
    public ApiResponse<List<RatingResponse>> getRatingsByBookId(@PathVariable Long bookId) {
        return ApiResponse.<List<RatingResponse>>builder()
                .data(ratingService.getRatingsByBookId(bookId))
                .build();
    }

    @GetMapping("/user/{userId}/book/{bookId}")
    public ApiResponse<RatingResponse> getRatingByUserAndBook(@PathVariable Long userId, @PathVariable Long bookId) throws AppException {
        return ApiResponse.<RatingResponse>builder()
                .data(ratingService.getRatingByUserAndBook(userId, bookId))
                .build();
    }

    @GetMapping("/book/{bookId}/average")
    public ApiResponse<Double> getAverageRatingForBook(@PathVariable Long bookId) {
        return ApiResponse.<Double>builder()
                .data(ratingService.getAverageRatingForBook(bookId))
                .build();
    }
}
