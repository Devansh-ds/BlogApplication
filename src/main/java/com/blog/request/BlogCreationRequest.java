package com.blog.request;

public record BlogCreationRequest(
    String title,
    String content
) {
}
