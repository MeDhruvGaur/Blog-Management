package com.blogapi.service;

import com.blogapi.dto.CommentRequest;
import com.blogapi.dto.CommentResponse;
import com.blogapi.model.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentService {
    List<CommentResponse> getCommentsByPostId(Long postId);
    Page<CommentResponse> getCommentsByPostIdPaged(Long postId, Pageable pageable);
    CommentResponse getCommentById(Long id);
    CommentResponse addCommentToPost(Long postId, CommentRequest commentRequest);
    CommentResponse updateComment(Long id, CommentRequest commentRequest);
    CommentResponse moderateComment(Long id, boolean approved);
    void deleteComment(Long id);
    CommentResponse mapToResponse(Comment comment);
}
