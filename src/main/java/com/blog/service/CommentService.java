package com.blog.service;

import com.blog.response.CommentResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {

    CommentResponse addComment(String commentText, Integer blogId, String token);
    CommentResponse editComment(String commentText, Integer commentId, String token);
    List<CommentResponse> getCommentsByBlog(Integer blogId);
    CommentResponse getCommentById(Integer commentId);
    void deleteCommentById(Integer commentId, String token);


}
