package com.library.comment_service.services;

import com.library.comment_service.dtos.requests.CommentRequest;
import com.library.comment_service.dtos.responses.CommentResponse;
import com.library.comment_service.entities.Comment;

import java.util.List;

public interface CommentService {

    CommentResponse createComment(CommentRequest request);

    CommentResponse updateComment(Long id, CommentRequest request);

    void deleteComment(Long id);

    List<CommentResponse> getCommentsByBookId(Long bookId);

    List<CommentResponse> getCommentsByUserId(Long userId);

    CommentResponse toCommentResponse(Comment comment);
}