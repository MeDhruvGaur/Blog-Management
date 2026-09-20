package com.blogapi.service;

import com.blogapi.dto.PostRequest;
import com.blogapi.dto.PostResponse;
import com.blogapi.exception.ResourceNotFoundException;
import com.blogapi.model.entity.Category;
import com.blogapi.model.entity.Post;
import com.blogapi.repository.CategoryRepository;
import com.blogapi.repository.PostRepository;
import com.blogapi.service.impl.PostServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private PostServiceImpl postService;

    private Category category;
    private Post post;
    private PostRequest postRequest;

    @BeforeEach
    void setUp() {
        category = Category.builder()
                .id(1L)
                .name("Technology")
                .description("Tech topics")
                .build();

        post = Post.builder()
                .id(1L)
                .title("Spring Boot Tutorial")
                .content("Comprehensive guide to Spring Boot 3")
                .author("John Doe")
                .category(category)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        postRequest = PostRequest.builder()
                .title("Spring Boot Tutorial")
                .content("Comprehensive guide to Spring Boot 3")
                .author("John Doe")
                .categoryId(1L)
                .build();
    }

    @Test
    @DisplayName("Should return all posts paged")
    void testGetAllPosts() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Post> postPage = new PageImpl<>(List.of(post));
        given(postRepository.findAll(pageable)).willReturn(postPage);

        Page<PostResponse> result = postService.getAllPosts(pageable);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getTitle()).isEqualTo("Spring Boot Tutorial");
    }

    @Test
    @DisplayName("Should return post by ID successfully")
    void testGetPostByIdSuccess() {
        given(postRepository.findById(1L)).willReturn(Optional.of(post));

        PostResponse result = postService.getPostById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getTitle()).isEqualTo("Spring Boot Tutorial");
        assertThat(result.getCategoryName()).isEqualTo("Technology");
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when post not found")
    void testGetPostByIdNotFound() {
        given(postRepository.findById(99L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> postService.getPostById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Post not found with id: 99");
    }

    @Test
    @DisplayName("Should create post successfully")
    void testCreatePostSuccess() {
        given(categoryRepository.findById(1L)).willReturn(Optional.of(category));
        given(postRepository.save(any(Post.class))).willReturn(post);

        PostResponse result = postService.createPost(postRequest);

        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("Spring Boot Tutorial");
        assertThat(result.getCategoryId()).isEqualTo(1L);
        verify(postRepository).save(any(Post.class));
    }

    @Test
    @DisplayName("Should throw exception when creating post with invalid category ID")
    void testCreatePostCategoryNotFound() {
        given(categoryRepository.findById(99L)).willReturn(Optional.empty());
        postRequest.setCategoryId(99L);

        assertThatThrownBy(() -> postService.createPost(postRequest))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Category not found with id: 99");
    }

    @Test
    @DisplayName("Should delete post successfully")
    void testDeletePostSuccess() {
        given(postRepository.existsById(1L)).willReturn(true);

        postService.deletePost(1L);

        verify(postRepository).deleteById(1L);
    }
}
