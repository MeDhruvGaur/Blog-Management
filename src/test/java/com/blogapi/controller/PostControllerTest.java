package com.blogapi.controller;

import com.blogapi.dto.PostRequest;
import com.blogapi.dto.PostResponse;
import com.blogapi.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("GET /api/posts - Should return paged posts")
    void testGetAllPosts() throws Exception {
        PostResponse postResponse = PostResponse.builder()
                .id(1L)
                .title("Sample Post")
                .content("Sample Content")
                .author("John")
                .categoryId(1L)
                .categoryName("Technology")
                .createdAt(LocalDateTime.now())
                .build();

        Page<PostResponse> page = new PageImpl<>(List.of(postResponse));
        given(postService.getAllPosts(any(Pageable.class))).willReturn(page);

        mockMvc.perform(get("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1L))
                .andExpect(jsonPath("$.content[0].title").value("Sample Post"));
    }

    @Test
    @DisplayName("GET /api/posts/{id} - Should return post by id")
    void testGetPostById() throws Exception {
        PostResponse postResponse = PostResponse.builder()
                .id(1L)
                .title("Sample Post")
                .content("Sample Content")
                .author("John")
                .categoryId(1L)
                .categoryName("Technology")
                .build();

        given(postService.getPostById(1L)).willReturn(postResponse);

        mockMvc.perform(get("/api/posts/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Sample Post"));
    }

    @Test
    @DisplayName("POST /api/posts - Should create new post and return 201 Created")
    void testCreatePost() throws Exception {
        PostRequest request = PostRequest.builder()
                .title("New Post Title")
                .content("Detailed content of the post...")
                .author("John Doe")
                .categoryId(1L)
                .build();

        PostResponse postResponse = PostResponse.builder()
                .id(1L)
                .title("New Post Title")
                .content("Detailed content of the post...")
                .author("John Doe")
                .categoryId(1L)
                .categoryName("Technology")
                .build();

        given(postService.createPost(any(PostRequest.class))).willReturn(postResponse);

        mockMvc.perform(post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("New Post Title"));
    }

    @Test
    @DisplayName("POST /api/posts - Should fail validation with 400 Bad Request on empty fields")
    void testCreatePostValidationFailure() throws Exception {
        PostRequest invalidRequest = PostRequest.builder()
                .title("") // Blank title
                .content("")
                .author("")
                .categoryId(null)
                .build();

        mockMvc.perform(post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errors.title").exists());
    }

    @Test
    @DisplayName("DELETE /api/posts/{id} - Should return 204 No Content")
    void testDeletePost() throws Exception {
        mockMvc.perform(delete("/api/posts/1"))
                .andExpect(status().isNoContent());
    }
}
