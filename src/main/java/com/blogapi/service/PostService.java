package com.blogapi.service;

import com.blogapi.dto.PostRequest;
import com.blogapi.dto.PostResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {
    Page<PostResponse> getAllPosts(Pageable pageable);
    PostResponse getPostById(Long id);
    PostResponse createPost(PostRequest postRequest);
    PostResponse updatePost(Long id, PostRequest postRequest);
    void deletePost(Long id);
    List<PostResponse> getPostsByCategory(Long categoryId);
    Page<PostResponse> getPostsByCategoryPaged(Long categoryId, Pageable pageable);
    Page<PostResponse> getPostsByAuthor(String author, Pageable pageable);
    PostResponse mapToResponse(com.blogapi.model.entity.Post post);
}
