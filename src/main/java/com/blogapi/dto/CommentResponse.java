package com.blogapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Response body representing a comment")
public class CommentResponse {

    @Schema(description = "Unique comment ID", example = "1")
    private Long id;

    @Schema(description = "Comment content", example = "Great article! Very helpful introduction to Spring Boot.")
    private String content;

    @Schema(description = "Author name", example = "Jane Doe")
    private String author;

    @Schema(description = "ID of the associated post", example = "1")
    private Long postId;

    @Schema(description = "Moderation approval status", example = "true")
    private Boolean approved;

    @Schema(description = "Creation timestamp", example = "2024-01-25T11:00:00")
    private LocalDateTime createdAt;

    @Schema(description = "Last update timestamp", example = "2024-01-25T11:00:00")
    private LocalDateTime updatedAt;

    public CommentResponse() {
    }

    public CommentResponse(Long id, String content, String author, Long postId, Boolean approved, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.content = content;
        this.author = author;
        this.postId = postId;
        this.approved = approved;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static CommentResponseBuilder builder() {
        return new CommentResponseBuilder();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public static class CommentResponseBuilder {
        private Long id;
        private String content;
        private String author;
        private Long postId;
        private Boolean approved;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public CommentResponseBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public CommentResponseBuilder content(String content) {
            this.content = content;
            return this;
        }

        public CommentResponseBuilder author(String author) {
            this.author = author;
            return this;
        }

        public CommentResponseBuilder postId(Long postId) {
            this.postId = postId;
            return this;
        }

        public CommentResponseBuilder approved(Boolean approved) {
            this.approved = approved;
            return this;
        }

        public CommentResponseBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public CommentResponseBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public CommentResponse build() {
            return new CommentResponse(id, content, author, postId, approved, createdAt, updatedAt);
        }
    }
}
