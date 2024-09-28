package com.library.comment_service.services.impl;


import com.library.comment_service.dtos.requests.CommentRequest;
import com.library.comment_service.dtos.responses.CommentResponse;
import com.library.comment_service.entities.Comment;
import com.library.comment_service.repositories.CommentRepo;
import com.library.comment_service.services.CommentService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentServiceImpl implements CommentService {

    CommentRepo commentRepo;

    @Override
    public CommentResponse createComment(CommentRequest request) {
        Comment comment = Comment.builder()
                .userId(request.getUserId())
                .bookId(request.getBookId())
                .content(request.getContent())
                .build();
        return toCommentResponse(commentRepo.save(comment));
    }

    @Override
    @Transactional
    public CommentResponse updateComment(Long id, CommentRequest request) {
        Comment comment = commentRepo.findById(id).orElseThrow(() -> new RuntimeException("Comment not found"));
        comment.setContent(request.getContent());
        return toCommentResponse(commentRepo.save(comment));
    }

    @Override
    public void deleteComment(Long id) {
        commentRepo.deleteById(id);
    }

    @Override
    public List<CommentResponse> getCommentsByBookId(Long bookId) {
        return commentRepo.findByBookId(bookId).stream()
                .map(this::toCommentResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentResponse> getCommentsByUserId(Long userId) {
        return commentRepo.findByUserId(userId).stream()
                .map(this::toCommentResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CommentResponse toCommentResponse(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .userId(comment.getUserId())
                .bookId(comment.getBookId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .likes(comment.getLikes())
                .build();
    }
}
