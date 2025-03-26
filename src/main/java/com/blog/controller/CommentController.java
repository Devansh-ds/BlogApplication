package com.blog.controller;

import com.blog.response.CommentResponse;
import com.blog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/blog/{blogId}")
    public ResponseEntity<CommentResponse> addComment(
                                              @RequestBody String commentText,
                                              @PathVariable Integer blogId,
                                              @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(commentService.addComment(commentText, blogId, token));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentResponse> editComment(
            @RequestBody String commentText,
            @PathVariable Integer commentId,
            @RequestHeader("Authorization") String token
    ) {
        return ResponseEntity.ok(commentService.editComment(commentText, commentId, token));
    }

    @GetMapping("/blog/{blogId}")
    public ResponseEntity<List<CommentResponse>> getCommentsByBlogId(
            @PathVariable Integer blogId
    ) {
        return ResponseEntity.ok(commentService.getCommentsByBlog(blogId));
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentResponse> getCommentById(
            @PathVariable Integer commentId
    ) {
        return ResponseEntity.ok(commentService.getCommentById(commentId));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteCommentById(
            @PathVariable Integer commentId,
            @RequestHeader("Authorization") String token
    ) {
        commentService.deleteCommentById(commentId, token);
        return ResponseEntity.ok("Comment deleted successfully with id: " + commentId);
    }


}
















