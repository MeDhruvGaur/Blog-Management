package com.blogapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Standard API response wrapper for errors and status messages")
public class ApiResponse<T> {

    @Schema(description = "Success indicator", example = "true")
    private boolean success;

    @Schema(description = "Response or error message", example = "Resource processed successfully")
    private String message;

    @Schema(description = "HTTP Status Code", example = "200")
    private int status;

    @Schema(description = "Timestamp of the response", example = "2024-01-25T10:30:00")
    private LocalDateTime timestamp = LocalDateTime.now();

    @Schema(description = "Payload data if applicable")
    private T data;

    @Schema(description = "Field validation errors map if applicable")
    private Map<String, String> errors;

    public ApiResponse() {
    }

    public ApiResponse(boolean success, String message, int status, LocalDateTime timestamp, T data, Map<String, String> errors) {
        this.success = success;
        this.message = message;
        this.status = status;
        this.timestamp = timestamp != null ? timestamp : LocalDateTime.now();
        this.data = data;
        this.errors = errors;
    }

    public static <T> ApiResponseBuilder<T> builder() {
        return new ApiResponseBuilder<T>();
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    public static class ApiResponseBuilder<T> {
        private boolean success;
        private String message;
        private int status;
        private LocalDateTime timestamp = LocalDateTime.now();
        private T data;
        private Map<String, String> errors;

        public ApiResponseBuilder<T> success(boolean success) {
            this.success = success;
            return this;
        }

        public ApiResponseBuilder<T> message(String message) {
            this.message = message;
            return this;
        }

        public ApiResponseBuilder<T> status(int status) {
            this.status = status;
            return this;
        }

        public ApiResponseBuilder<T> timestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public ApiResponseBuilder<T> data(T data) {
            this.data = data;
            return this;
        }

        public ApiResponseBuilder<T> errors(Map<String, String> errors) {
            this.errors = errors;
            return this;
        }

        public ApiResponse<T> build() {
            return new ApiResponse<T>(success, message, status, timestamp, data, errors);
        }
    }
}
