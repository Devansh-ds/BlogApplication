package com.blog.request;

public record BlogUpdateRequest(
        String title,
        String content
) {
}
