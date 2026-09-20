package com.blogapi.service.impl;

import com.blogapi.dto.CommentRequest;
import com.blogapi.dto.CommentResponse;
import com.blogapi.exception.ResourceNotFoundException;
import com.blogapi.model.entity.Comment;
import com.blogapi.model.entity.Post;
import com.blogapi.repository.CommentRepository;
import com.blogapi.repository.PostRepository;
import com.blogapi.service.CommentService;
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
public class CommentServiceImpl implements CommentService {

    private static final Logger log = LoggerFactory.getLogger(CommentServiceImpl.class);

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentResponse> getCommentsByPostId(Long postId) {
        log.info("Fetching comments for post id: {}", postId);
        if (!postRepository.existsById(postId)) {
            throw new ResourceNotFoundException("Post not found with id: " + postId);
        }

        return commentRepository.findByPostId(postId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CommentResponse> getCommentsByPostIdPaged(Long postId, Pageable pageable) {
        log.info("Fetching paged comments for post id: {}", postId);
        if (!postRepository.existsById(postId)) {
            throw new ResourceNotFoundException("Post not found with id: " + postId);
        }

        return commentRepository.findByPostId(postId, pageable)
                .map(this::mapToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public CommentResponse getCommentById(Long id) {
        log.info("Fetching comment with id: {}", id);
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: " + id));
        return mapToResponse(comment);
    }

    @Override
    public CommentResponse addCommentToPost(Long postId, CommentRequest commentRequest) {
        log.info("Adding comment to post id: {} by author: {}", postId, commentRequest.getAuthor());
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));

        Comment comment = Comment.builder()
                .content(commentRequest.getContent())
                .author(commentRequest.getAuthor())
                .post(post)
                .approved(true)
                .build();

        Comment savedComment = commentRepository.save(comment);
        log.info("Created comment with id: {}", savedComment.getId());
        return mapToResponse(savedComment);
    }

    @Override
    public CommentResponse updateComment(Long id, CommentRequest commentRequest) {
        log.info("Updating comment id: {}", id);
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: " + id));

        comment.setContent(commentRequest.getContent());
        comment.setAuthor(commentRequest.getAuthor());

        Comment updatedComment = commentRepository.save(comment);
        log.info("Updated comment with id: {}", updatedComment.getId());
        return mapToResponse(updatedComment);
    }

    @Override
    public CommentResponse moderateComment(Long id, boolean approved) {
        log.info("Moderating comment id: {} to approved={}", id, approved);
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: " + id));

        comment.setApproved(approved);
        Comment updatedComment = commentRepository.save(comment);
        return mapToResponse(updatedComment);
    }

    @Override
    public void deleteComment(Long id) {
        log.info("Deleting comment id: {}", id);
        if (!commentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Comment not found with id: " + id);
        }
        commentRepository.deleteById(id);
        log.info("Deleted comment successfully with id: {}", id);
    }

    @Override
    public CommentResponse mapToResponse(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .author(comment.getAuthor())
                .postId(comment.getPost().getId())
                .approved(comment.getApproved())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .build();
    }
}
