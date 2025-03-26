package com.blog.repo;

import com.blog.entity.Blog;
import com.blog.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BlogRepository extends JpaRepository<Blog, Integer> {

    Optional<List<Blog>> findAllByAuthor(User user);

    Page<Blog> findAll(Pageable pageable);
}
