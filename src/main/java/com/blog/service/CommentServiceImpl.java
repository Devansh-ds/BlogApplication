package com.blog.service;

import com.blog.entity.Blog;
import com.blog.entity.Comment;
import com.blog.entity.User;
import com.blog.mapper.CommentMapper;
import com.blog.repo.CommentRepository;
import com.blog.response.CommentResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final BlogService blogService;
    private final CommentMapper commentMapper;

    @Override
    public CommentResponse addComment(String commentText, Integer blogId, String token) {

        User user = userService.findByJwtToken(token);
        Blog blog = blogService.findUnsafeBlogById(blogId);

        System.out.println("here");

        Comment comment = Comment
                .builder()
                .commenter(user)
                .text(commentText)
                .commentDate(LocalDateTime.now())
                .blog(blog)
                .build();

        System.out.println("comment made");

        Comment savedComment = commentRepository.save(comment);

        System.out.println("comment saved");

        return commentMapper.toCommentResponse(savedComment);
    }

    private Comment getCommentByIdAndUser(Integer commentId, User user) {
        List<Comment> allCommnetsOfUser = commentRepository
                .findAllByCommenter(user)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found with user id: " + user.getId()));

        return allCommnetsOfUser.stream()
                .filter(comment -> commentId.equals(comment.getId()))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Comment not found with comment Id: " + commentId));
    }

    @Override
    public CommentResponse editComment(String commentText, Integer commentId, String token) {
        User user = userService.findByJwtToken(token);
        Comment oldComment = getCommentByIdAndUser(commentId, user);

        if (commentText != null && !commentText.equals(oldComment.getText())) {
            oldComment.setText(commentText);
        }

        Comment savedComment = commentRepository.save(oldComment);
        return commentMapper.toCommentResponse(savedComment);
    }

    @Override
    public List<CommentResponse> getCommentsByBlog(Integer blogId) {
        List<Comment> allComments = commentRepository
                .findAllByBlogId(blogId)
                .orElseThrow(() -> new EntityNotFoundException("No Comment found with blog id: " + blogId));

        return allComments
                .stream()
                .map(commentMapper::toCommentResponse)
                .toList();
    }

    @Override
    public CommentResponse getCommentById(Integer commentId) {

        Comment comment = commentRepository
                .findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("No Comment found with comment Id: " + commentId));

        return commentMapper.toCommentResponse(comment);
    }

    @Override
    public void deleteCommentById(Integer commentId, String token) {
        User user = userService.findByJwtToken(token);
        Comment comment = getCommentByIdAndUser(commentId, user);
        commentRepository.delete(comment);
    }
}
