package com.library.comment_service.controllers;


import com.library.comment_service.dtos.ApiResponse;
import com.library.comment_service.dtos.requests.CommentRequest;
import com.library.comment_service.dtos.responses.CommentResponse;
import com.library.comment_service.services.CommentService;
import jakarta.persistence.Access;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentController {
    CommentService commentService;

    @PostMapping
    public ApiResponse<CommentResponse> createComment(@RequestBody CommentRequest request) {
        return ApiResponse.<CommentResponse>builder()
                .data(commentService.createComment(request))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<CommentResponse> updateComment(@PathVariable Long id, @RequestBody CommentRequest request) {
        return ApiResponse.<CommentResponse>builder()
                .data(commentService.updateComment(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ApiResponse.<Void>builder()
                .data(null)
                .build();
    }

    @GetMapping("/book/{bookId}")
    public ApiResponse<List<CommentResponse>> getCommentsByBookId(@PathVariable Long bookId) {
        return ApiResponse.<List<CommentResponse>>builder()
                .data(commentService.getCommentsByBookId(bookId))
                .build();
    }

    @GetMapping("/user/{userId}")
    public ApiResponse<List<CommentResponse>> getCommentsByUserId(@PathVariable Long userId) {
        return ApiResponse.<List<CommentResponse>>builder()
                .data(commentService.getCommentsByUserId(userId))
                .build();
    }
}
