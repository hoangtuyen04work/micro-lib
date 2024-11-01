package com.library.rate_service.repositories;

import com.library.rate_service.dtos.responses.RatingResponse;
import com.library.rate_service.entities.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepo extends JpaRepository<Rating, Long> {

    List<Rating> findByBookId(Long bookId);

    Optional<Rating> findByUserIdAndBookId(Long userId, Long bookId);

    Page<Rating> getRatingByBookId(Long bookId, Pageable pageable);
}
