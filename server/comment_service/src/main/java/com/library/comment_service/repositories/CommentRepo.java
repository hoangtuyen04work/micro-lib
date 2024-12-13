package com.library.comment_service.repositories;

import com.library.comment_service.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Long> {

    List<Comment> findByBookId(Long bookId);

    List<Comment> findByUserId(Long userId);
}

