package com.blogapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Request body for creating or updating a post")
public class PostRequest {

    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 255, message = "Title must be between 3 and 255 characters")
    @Schema(description = "Title of the blog post", example = "Getting Started with Spring Boot 3")
    private String title;

    @NotBlank(message = "Content is required")
    @Schema(description = "Detailed content of the blog post", example = "Spring Boot makes it easy to create stand-alone production-ready Spring applications...")
    private String content;

    @NotBlank(message = "Author name is required")
    @Size(min = 2, max = 100, message = "Author name must be between 2 and 100 characters")
    @Schema(description = "Name of the post author", example = "John Doe")
    private String author;

    @NotNull(message = "Category ID is required")
    @Schema(description = "ID of the category this post belongs to", example = "1")
    private Long categoryId;

    public PostRequest() {
    }

    public PostRequest(String title, String content, String author, Long categoryId) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.categoryId = categoryId;
    }

    public static PostRequestBuilder builder() {
        return new PostRequestBuilder();
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

    public static class PostRequestBuilder {
        private String title;
        private String content;
        private String author;
        private Long categoryId;

        public PostRequestBuilder title(String title) {
            this.title = title;
            return this;
        }

        public PostRequestBuilder content(String content) {
            this.content = content;
            return this;
        }

        public PostRequestBuilder author(String author) {
            this.author = author;
            return this;
        }

        public PostRequestBuilder categoryId(Long categoryId) {
            this.categoryId = categoryId;
            return this;
        }

        public PostRequest build() {
            return new PostRequest(title, content, author, categoryId);
        }
    }
}
