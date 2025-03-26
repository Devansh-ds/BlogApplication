package com.blog.response;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record BlogResponse(
        Integer id,
        String title,
        String content,
        String authorName,
        LocalDateTime createdAt,
        List<CommentResponse> commentResponses,
        List<String> imageUrls,
        String blogSummary
) {
}
