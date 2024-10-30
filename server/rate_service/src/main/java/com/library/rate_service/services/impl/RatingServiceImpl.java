package com.library.rate_service.services.impl;

import com.library.rate_service.dtos.requests.RatingRequest;
import com.library.rate_service.dtos.responses.PageResponse;
import com.library.rate_service.dtos.responses.RatingResponse;
import com.library.rate_service.entities.Rating;
import com.library.rate_service.exceptions.AppException;
import com.library.rate_service.exceptions.ErrorCode;
import com.library.rate_service.repositories.RatingRepo;
import com.library.rate_service.services.RatingService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RatingServiceImpl implements RatingService {
    RatingRepo ratingRepo;

    @Override
    public RatingResponse rateBook(RatingRequest request) {
        Optional<Rating> rate = ratingRepo.findByUserIdAndBookId(request.getUserId(), request.getBookId());
        if(rate.isPresent()) {
            Rating rating = rate.get();
            rating.setRating(request.getRating());
            rating.setComment(request.getComment());
            return toRatingResponse(ratingRepo.save(rating));
        }
        Rating rating = Rating.builder()
                .userId(request.getUserId())
                .bookId(request.getBookId())
                .rating(request.getRating())
                .userName(request.getUserName())
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
    public PageResponse<RatingResponse> getRatingsByBookId(Long bookId, Long page, Long size) {
        Pageable pageRequest = PageRequest.of(Math.toIntExact(page), Math.toIntExact(size), Sort.by("createdAt").descending());
        Page<Rating> pages = ratingRepo.getRatingByBookId(bookId, pageRequest);
        return toRatingResponse(pages);
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
    public PageResponse<RatingResponse> toRatingResponse(Page<Rating> pages){
        return PageResponse.<RatingResponse>builder()
                .content(toPageResponses(pages.getContent()))
                .pageNumber(pages.getNumber())
                .pageSize(pages.getSize())
                .totalElements(pages.getTotalElements())
                .totalPages(pages.getTotalPages())
                .build();
    }

    @Override
    public List<RatingResponse> toPageResponses(List<Rating> ratings){
        return ratings.stream().map(this::toRatingResponse).toList();
    }

    @Override
    public RatingResponse toRatingResponse(Rating rating) {
        return RatingResponse.builder()
                .id(rating.getId())
                .userName(rating.getUserName())
                .userId(rating.getUserId())
                .bookId(rating.getBookId())
                .rating(rating.getRating())
                .comment(rating.getComment())
                .createdAt(rating.getCreatedAt())
                .updatedAt(rating.getUpdatedAt())
                .build();
    }
}