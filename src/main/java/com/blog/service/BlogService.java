package com.blog.service;

import com.blog.entity.Blog;
import com.blog.request.BlogCreationRequest;
import com.blog.request.BlogUpdateRequest;
import com.blog.response.BlogResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BlogService {

    BlogResponse createBlog(BlogCreationRequest request, String token, List<MultipartFile> images) throws IOException;
    BlogResponse updateBlog(BlogUpdateRequest request, Integer blogId, String token, List<MultipartFile> images) throws IOException;
    void deleteBlog(Integer blogId, String token);
    BlogResponse getBlogById(Integer blogId);
    Blog findUnsafeBlogById(Integer blogId);
    String summarizeBlog(Integer blogId);
    Page<BlogResponse> getAllBlogs(Integer page, Integer size, String sortBy, String sortDirection);

    // todo = add pagination

}
