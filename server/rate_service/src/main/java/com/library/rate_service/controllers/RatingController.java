package com.library.rate_service.controllers;

import com.library.rate_service.dtos.ApiResponse;
import com.library.rate_service.dtos.requests.RatingRequest;
import com.library.rate_service.dtos.responses.PageResponse;
import com.library.rate_service.dtos.responses.RatingResponse;
import com.library.rate_service.exceptions.AppException;
import com.library.rate_service.services.RatingService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/book")
    public ApiResponse<PageResponse<RatingResponse>> getRatingsByBookId(
            @RequestParam(defaultValue = "0") Long page,
            @RequestParam(defaultValue = "10") Long size,
            @RequestParam Long bookId) {
        return ApiResponse.<PageResponse<RatingResponse>>builder()
                .data(ratingService.getRatingsByBookId(bookId, page, size))
                .build();
    }

    @GetMapping("/user/book")
    public ApiResponse<RatingResponse> getRatingByUserAndBook(@RequestParam Long userId, @RequestParam Long bookId) throws AppException {
        return ApiResponse.<RatingResponse>builder()
                .data(ratingService.getRatingByUserAndBook(userId, bookId))
                .build();
    }

    @GetMapping("/book/average")
    public ApiResponse<Double> getAverageRatingForBook(@RequestParam Long bookId) {
        return ApiResponse.<Double>builder()
                .data(ratingService.getAverageRatingForBook(bookId))
                .build();
    }
}
