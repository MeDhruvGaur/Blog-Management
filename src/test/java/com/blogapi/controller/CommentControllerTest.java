package com.blogapi.controller;

import com.blogapi.dto.CommentRequest;
import com.blogapi.dto.CommentResponse;
import com.blogapi.service.CommentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

@WebMvcTest(CommentController.class)
class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("GET /api/posts/{postId}/comments - Should return post comments")
    void testGetCommentsByPost() throws Exception {
        CommentResponse comment = CommentResponse.builder()
                .id(1L)
                .content("Informative article!")
                .author("Reader")
                .postId(1L)
                .approved(true)
                .createdAt(LocalDateTime.now())
                .build();

        given(commentService.getCommentsByPostId(1L)).willReturn(List.of(comment));

        mockMvc.perform(get("/api/posts/1/comments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value("Informative article!"))
                .andExpect(jsonPath("$[0].author").value("Reader"));
    }

    @Test
    @DisplayName("POST /api/posts/{postId}/comments - Should add comment to post")
    void testAddCommentToPost() throws Exception {
        CommentRequest request = CommentRequest.builder()
                .content("Informative article!")
                .author("Reader")
                .build();

        CommentResponse response = CommentResponse.builder()
                .id(1L)
                .content("Informative article!")
                .author("Reader")
                .postId(1L)
                .approved(true)
                .build();

        given(commentService.addCommentToPost(eq(1L), any(CommentRequest.class))).willReturn(response);

        mockMvc.perform(post("/api/posts/1/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.postId").value(1L));
    }

    @Test
    @DisplayName("PATCH /api/comments/{id}/moderation - Should moderate comment")
    void testModerateComment() throws Exception {
        CommentResponse response = CommentResponse.builder()
                .id(1L)
                .content("Comment text")
                .author("User")
                .postId(1L)
                .approved(false)
                .build();

        given(commentService.moderateComment(1L, false)).willReturn(response);

        mockMvc.perform(patch("/api/comments/1/moderation?approved=false"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.approved").value(false));
    }
}
