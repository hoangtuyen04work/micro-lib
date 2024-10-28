package com.library.rate_service.services;

import com.library.rate_service.dtos.requests.RatingRequest;
import com.library.rate_service.dtos.responses.PageResponse;
import com.library.rate_service.dtos.responses.RatingResponse;
import com.library.rate_service.entities.Rating;
import com.library.rate_service.exceptions.AppException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RatingService {

    RatingResponse rateBook(RatingRequest request);

    RatingResponse updateRating(Long id, RatingRequest request);

    void deleteRating(Long id);

    PageResponse<RatingResponse> getRatingsByBookId(Long bookId, Long page, Long size);

    RatingResponse getRatingByUserAndBook(Long userId, Long bookId) throws AppException;

    Double getAverageRatingForBook(Long bookId);

    PageResponse<RatingResponse> toRatingResponse(Page<Rating> pages);

    List<RatingResponse> toPageResponses(List<Rating> ratings);

    RatingResponse toRatingResponse(Rating rating);
}