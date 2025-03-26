package com.blog.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CommentResponse(
        Integer id,
        String name,
        String text,
        LocalDateTime commentDate
) {
}
