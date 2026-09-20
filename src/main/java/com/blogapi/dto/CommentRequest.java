package com.blogapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Request body for submitting or updating a comment")
public class CommentRequest {

    @NotBlank(message = "Comment content is required")
    @Size(min = 2, max = 1000, message = "Comment content must be between 2 and 1000 characters")
    @Schema(description = "Content of the comment", example = "Great article! Very helpful introduction to Spring Boot.")
    private String content;

    @NotBlank(message = "Author name is required")
    @Size(min = 2, max = 100, message = "Author name must be between 2 and 100 characters")
    @Schema(description = "Name of the comment author", example = "Jane Doe")
    private String author;

    public CommentRequest() {
    }

    public CommentRequest(String content, String author) {
        this.content = content;
        this.author = author;
    }

    public static CommentRequestBuilder builder() {
        return new CommentRequestBuilder();
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

    public static class CommentRequestBuilder {
        private String content;
        private String author;

        public CommentRequestBuilder content(String content) {
            this.content = content;
            return this;
        }

        public CommentRequestBuilder author(String author) {
            this.author = author;
            return this;
        }

        public CommentRequest build() {
            return new CommentRequest(content, author);
        }
    }
}
