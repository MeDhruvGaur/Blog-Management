package com.blogapi.service;

import com.blogapi.dto.CommentRequest;
import com.blogapi.dto.CommentResponse;
import com.blogapi.exception.ResourceNotFoundException;
import com.blogapi.model.entity.Comment;
import com.blogapi.model.entity.Post;
import com.blogapi.repository.CommentRepository;
import com.blogapi.repository.PostRepository;
import com.blogapi.service.impl.CommentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private CommentServiceImpl commentService;

    private Post post;
    private Comment comment;

    @BeforeEach
    void setUp() {
        post = Post.builder()
                .id(1L)
                .title("Post 1")
                .content("Content 1")
                .author("Author 1")
                .build();

        comment = Comment.builder()
                .id(1L)
                .content("Nice post!")
                .author("Reviewer")
                .post(post)
                .approved(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Test
    @DisplayName("Should return comments for post")
    void testGetCommentsByPostId() {
        given(postRepository.existsById(1L)).willReturn(true);
        given(commentRepository.findByPostId(1L)).willReturn(List.of(comment));

        List<CommentResponse> result = commentService.getCommentsByPostId(1L);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getContent()).isEqualTo("Nice post!");
    }

    @Test
    @DisplayName("Should add comment to post")
    void testAddCommentToPost() {
        CommentRequest request = CommentRequest.builder()
                .content("Nice post!")
                .author("Reviewer")
                .build();

        given(postRepository.findById(1L)).willReturn(Optional.of(post));
        given(commentRepository.save(any(Comment.class))).willReturn(comment);

        CommentResponse response = commentService.addCommentToPost(1L, request);

        assertThat(response).isNotNull();
        assertThat(response.getContent()).isEqualTo("Nice post!");
        assertThat(response.getPostId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("Should throw exception when adding comment to non-existent post")
    void testAddCommentPostNotFound() {
        CommentRequest request = CommentRequest.builder()
                .content("Nice post!")
                .author("Reviewer")
                .build();

        given(postRepository.findById(99L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> commentService.addCommentToPost(99L, request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Post not found with id: 99");
    }

    @Test
    @DisplayName("Should moderate comment approval")
    void testModerateComment() {
        given(commentRepository.findById(1L)).willReturn(Optional.of(comment));
        given(commentRepository.save(any(Comment.class))).willReturn(comment);

        CommentResponse response = commentService.moderateComment(1L, false);

        assertThat(response).isNotNull();
        assertThat(response.getApproved()).isFalse();
    }
}
