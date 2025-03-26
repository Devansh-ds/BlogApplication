package com.blog.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.blog.request.BlogCreationRequest;
import com.blog.request.BlogUpdateRequest;
import com.blog.response.BlogResponse;
import com.blog.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/blog")
public class BlogController {

    private final BlogService blogService;

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<BlogResponse> createBlog(
            @RequestPart("blog") String blogJson,
            @RequestPart(value = "images", required = false) List<MultipartFile> images,
            @RequestHeader("Authorization") String token
            ) throws IOException {

        // Convert JSON String to Java Object
        ObjectMapper objectMapper = new ObjectMapper();
        BlogCreationRequest request = objectMapper.readValue(blogJson, BlogCreationRequest.class);

        return ResponseEntity.ok(blogService.createBlog(request, token, images));
    }

    @PutMapping(path = "/{blogId}", consumes = "multipart/form-data")
    public ResponseEntity<BlogResponse> updateBlog(
            @PathVariable Integer blogId,
            @RequestPart("blog") String blogJson,
            @RequestPart(value = "images", required = false) List<MultipartFile> images,
            @RequestHeader("Authorization") String token
            ) throws IOException {

        // Convert JSON String to Java Object
        ObjectMapper objectMapper = new ObjectMapper();
        BlogUpdateRequest request = objectMapper.readValue(blogJson, BlogUpdateRequest.class);

        return ResponseEntity.ok(blogService.updateBlog(request, blogId, token, images));
    }

    @DeleteMapping("/{blogId}")
    public ResponseEntity<String> deleteBlog(
            @PathVariable Integer blogId,
            @RequestHeader("Authorization")  String token
    ) {
        blogService.deleteBlog(blogId, token);
        return ResponseEntity.ok("Blog with id " + blogId + " deleted.");
    }

    @GetMapping("/{blogId}")
    public ResponseEntity<BlogResponse> getBlogById(@PathVariable Integer blogId) {
        return ResponseEntity.ok(blogService.getBlogById(blogId));
    }

    @GetMapping("/{blogId}/summarize")
    public ResponseEntity<String> summarizeBlog(@PathVariable Integer blogId) {
        return ResponseEntity.ok(blogService.summarizeBlog(blogId));
    }

    @GetMapping
    public  ResponseEntity<Page<BlogResponse>> getAllBlogs(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        return ResponseEntity.ok(blogService.getAllBlogs(page, size, sortBy, sortDirection));
    }


}

















