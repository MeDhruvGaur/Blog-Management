package com.blogapi.service.impl;

import com.blogapi.dto.PostRequest;
import com.blogapi.dto.PostResponse;
import com.blogapi.exception.ResourceNotFoundException;
import com.blogapi.model.entity.Category;
import com.blogapi.model.entity.Post;
import com.blogapi.repository.CategoryRepository;
import com.blogapi.repository.PostRepository;
import com.blogapi.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostServiceImpl implements PostService {

    private static final Logger log = LoggerFactory.getLogger(PostServiceImpl.class);

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;

    public PostServiceImpl(PostRepository postRepository, CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PostResponse> getAllPosts(Pageable pageable) {
        log.info("Fetching posts with pageable: {}", pageable);
        return postRepository.findAll(pageable)
                .map(this::mapToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public PostResponse getPostById(Long id) {
        log.info("Fetching post with id: {}", id);
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + id));
        return mapToResponse(post);
    }

    @Override
    public PostResponse createPost(PostRequest postRequest) {
        log.info("Creating post titled: '{}' by author: '{}'", postRequest.getTitle(), postRequest.getAuthor());
        Category category = categoryRepository.findById(postRequest.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Category not found with id: " + postRequest.getCategoryId()));

        Post post = Post.builder()
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .author(postRequest.getAuthor())
                .category(category)
                .build();

        Post savedPost = postRepository.save(post);
        log.info("Created post successfully with id: {}", savedPost.getId());
        return mapToResponse(savedPost);
    }

    @Override
    public PostResponse updatePost(Long id, PostRequest postRequest) {
        log.info("Updating post with id: {}", id);
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + id));

        if (!post.getCategory().getId().equals(postRequest.getCategoryId())) {
            Category category = categoryRepository.findById(postRequest.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Category not found with id: " + postRequest.getCategoryId()));
            post.setCategory(category);
        }

        post.setTitle(postRequest.getTitle());
        post.setContent(postRequest.getContent());
        post.setAuthor(postRequest.getAuthor());

        Post updatedPost = postRepository.save(post);
        log.info("Updated post successfully with id: {}", updatedPost.getId());
        return mapToResponse(updatedPost);
    }

    @Override
    public void deletePost(Long id) {
        log.info("Deleting post with id: {}", id);
        if (!postRepository.existsById(id)) {
            throw new ResourceNotFoundException("Post not found with id: " + id);
        }
        postRepository.deleteById(id);
        log.info("Deleted post successfully with id: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByCategory(Long categoryId) {
        log.info("Fetching posts by categoryId: {}", categoryId);
        if (!categoryRepository.existsById(categoryId)) {
            throw new ResourceNotFoundException("Category not found with id: " + categoryId);
        }

        return postRepository.findByCategoryId(categoryId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PostResponse> getPostsByCategoryPaged(Long categoryId, Pageable pageable) {
        log.info("Fetching posts by categoryId: {} with pagination", categoryId);
        if (!categoryRepository.existsById(categoryId)) {
            throw new ResourceNotFoundException("Category not found with id: " + categoryId);
        }

        return postRepository.findByCategoryId(categoryId, pageable)
                .map(this::mapToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PostResponse> getPostsByAuthor(String author, Pageable pageable) {
        log.info("Fetching posts by author: {}", author);
        return postRepository.findByAuthorIgnoreCase(author, pageable)
                .map(this::mapToResponse);
    }

    @Override
    public PostResponse mapToResponse(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getAuthor())
                .categoryId(post.getCategory().getId())
                .categoryName(post.getCategory().getName())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }
}
