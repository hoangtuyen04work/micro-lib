package com.library.rate_service.services.impl;


import com.library.rate_service.dtos.requests.RatingRequest;
import com.library.rate_service.dtos.responses.RatingResponse;
import com.library.rate_service.entities.Rating;
import com.library.rate_service.exceptions.AppException;
import com.library.rate_service.exceptions.ErrorCode;
import com.library.rate_service.repositories.RatingRepo;
import com.library.rate_service.services.RatingService;
import com.nimbusds.oauth2.sdk.AbstractOptionallyAuthenticatedRequest;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RatingServiceImpl implements RatingService {
    RatingRepo ratingRepo;

    @Override
    public RatingResponse rateBook(RatingRequest request) {
        Optional<Rating> rate = ratingRepo.findByUserIdAndBookId(request.getUserId(), request.getBookId());
        if(rate.isPresent()) {
            Rating rating = rate.get(); rating.setRating(request.getRating());
            return toRatingResponse(ratingRepo.save(rating));
        }
        Rating rating = Rating.builder()
                .userId(request.getUserId())
                .bookId(request.getBookId())
                .rating(request.getRating())
                .comment(request.getComment())
                .build();
        return toRatingResponse(ratingRepo.save(rating));
    }

    @Override
    public RatingResponse updateRating(Long id, RatingRequest request) {
        Rating rating = ratingRepo.findById(id).orElseThrow(() -> new RuntimeException("Rating not found"));
        rating.setRating(request.getRating());
        rating.setComment(request.getComment());
        return toRatingResponse(ratingRepo.save(rating));
    }

    @Override
    public void deleteRating(Long id) {
        ratingRepo.deleteById(id);
    }

    @Override
    public List<RatingResponse> getRatingsByBookId(Long bookId) {
        return ratingRepo.findByBookId(bookId).stream()
                .map(this::toRatingResponse)
                .collect(Collectors.toList());
    }

    @Override
    public RatingResponse getRatingByUserAndBook(Long userId, Long bookId) throws AppException {
        Rating rating = ratingRepo.findByUserIdAndBookId(userId, bookId)
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_INPUT));
        return toRatingResponse(rating);
    }

    @Override
    public Double getAverageRatingForBook(Long bookId) {
        List<Rating> ratings = ratingRepo.findByBookId(bookId);
        return ratings.stream()
                .mapToInt(Rating::getRating)
                .average()
                .orElse(0.0);
    }

    @Override
    public RatingResponse toRatingResponse(Rating rating) {
        return RatingResponse.builder()
                .id(rating.getId())
                .userId(rating.getUserId())
                .bookId(rating.getBookId())
                .rating(rating.getRating())
                .comment(rating.getComment())
                .createdAt(rating.getCreatedAt())
                .updatedAt(rating.getUpdatedAt())
                .build();
    }
}