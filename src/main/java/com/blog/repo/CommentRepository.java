package com.blog.repo;

import com.blog.entity.Comment;
import com.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    Optional<List<Comment>> findAllByCommenter(User user);

    Optional<List<Comment>> findAllByBlogId(Integer blogId);

}
