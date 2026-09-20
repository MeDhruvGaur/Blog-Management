package com.blogapi.repository;

import com.blogapi.model.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByCategoryId(Long categoryId);

    Page<Post> findByCategoryId(Long categoryId, Pageable pageable);

    List<Post> findByAuthorIgnoreCase(String author);

    Page<Post> findByAuthorIgnoreCase(String author, Pageable pageable);

    List<Post> findByTitleContainingIgnoreCase(String title);
}
