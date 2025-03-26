package com.blog.mapper;

import com.blog.entity.Comment;
import com.blog.response.CommentResponse;
import org.springframework.stereotype.Service;

@Service
public class CommentMapper {

    public CommentResponse toCommentResponse(Comment savedComment) {
        System.out.println("in comment mapper toCommentResponse");
        return CommentResponse
                .builder()
                .id(savedComment.getId())
                .name(savedComment.getCommenter().getFullname())
                .text(savedComment.getText())
                .commentDate(savedComment.getCommentDate())
                .build();
    }
}
