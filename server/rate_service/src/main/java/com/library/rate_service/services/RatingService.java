package com.library.rate_service.services;

import com.library.rate_service.dtos.requests.RatingRequest;
import com.library.rate_service.dtos.responses.RatingResponse;
import com.library.rate_service.entities.Rating;
import com.library.rate_service.exceptions.AppException;

import java.util.List;

public interface RatingService {

    RatingResponse rateBook(RatingRequest request);

    RatingResponse updateRating(Long id, RatingRequest request);

    void deleteRating(Long id);

    List<RatingResponse> getRatingsByBookId(Long bookId);

    RatingResponse getRatingByUserAndBook(Long userId, Long bookId) throws AppException;

    Double getAverageRatingForBook(Long bookId);

    RatingResponse toRatingResponse(Rating rating);
}