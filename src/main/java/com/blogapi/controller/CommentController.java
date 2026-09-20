package com.blogapi.controller;

import com.blogapi.dto.CommentRequest;
import com.blogapi.dto.CommentResponse;
import com.blogapi.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Comments", description = "Blog Comment & Moderation Management APIs")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/api/posts/{postId}/comments")
    @Operation(summary = "Get comments for post", description = "Retrieve all comments associated with a specific blog post")
    public ResponseEntity<List<CommentResponse>> getCommentsByPost(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.getCommentsByPostId(postId));
    }

    @PostMapping("/api/posts/{postId}/comments")
    @Operation(summary = "Add comment to post", description = "Add a new comment to an existing blog post")
    public ResponseEntity<CommentResponse> addCommentToPost(
            @PathVariable Long postId,
            @Valid @RequestBody CommentRequest commentRequest) {
        CommentResponse response = commentService.addCommentToPost(postId, commentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/api/comments/{id}")
    @Operation(summary = "Get comment by ID", description = "Retrieve a specific comment by its ID")
    public ResponseEntity<CommentResponse> getCommentById(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.getCommentById(id));
    }

    @PutMapping("/api/comments/{id}")
    @Operation(summary = "Update comment", description = "Update the content or author of a comment")
    public ResponseEntity<CommentResponse> updateComment(
            @PathVariable Long id,
            @Valid @RequestBody CommentRequest commentRequest) {
        return ResponseEntity.ok(commentService.updateComment(id, commentRequest));
    }

    @PatchMapping("/api/comments/{id}/moderation")
    @Operation(summary = "Moderate comment", description = "Approve or reject a comment")
    public ResponseEntity<CommentResponse> moderateComment(
            @PathVariable Long id,
            @RequestParam boolean approved) {
        return ResponseEntity.ok(commentService.moderateComment(id, approved));
    }

    @DeleteMapping("/api/comments/{id}")
    @Operation(summary = "Delete comment", description = "Delete a comment by ID")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}
