package com.blogapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Response body representing a blog post")
public class PostResponse {

    @Schema(description = "Unique post ID", example = "1")
    private Long id;

    @Schema(description = "Post title", example = "Getting Started with Spring Boot 3")
    private String title;

    @Schema(description = "Post content", example = "Spring Boot makes it easy to create stand-alone production-ready Spring applications...")
    private String content;

    @Schema(description = "Author name", example = "John Doe")
    private String author;

    @Schema(description = "Category ID", example = "1")
    private Long categoryId;

    @Schema(description = "Category Name", example = "Technology")
    private String categoryName;

    @Schema(description = "Creation timestamp", example = "2024-01-25T10:30:00")
    private LocalDateTime createdAt;

    @Schema(description = "Last update timestamp", example = "2024-01-25T10:30:00")
    private LocalDateTime updatedAt;

    public PostResponse() {
    }

    public PostResponse(Long id, String title, String content, String author, Long categoryId, String categoryName, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static PostResponseBuilder builder() {
        return new PostResponseBuilder();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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

    public static class PostResponseBuilder {
        private Long id;
        private String title;
        private String content;
        private String author;
        private Long categoryId;
        private String categoryName;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public PostResponseBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public PostResponseBuilder title(String title) {
            this.title = title;
            return this;
        }

        public PostResponseBuilder content(String content) {
            this.content = content;
            return this;
        }

        public PostResponseBuilder author(String author) {
            this.author = author;
            return this;
        }

        public PostResponseBuilder categoryId(Long categoryId) {
            this.categoryId = categoryId;
            return this;
        }

        public PostResponseBuilder categoryName(String categoryName) {
            this.categoryName = categoryName;
            return this;
        }

        public PostResponseBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public PostResponseBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public PostResponse build() {
            return new PostResponse(id, title, content, author, categoryId, categoryName, createdAt, updatedAt);
        }
    }
}
