package com.blog.mapper;

import com.blog.entity.Blog;
import com.blog.image.ImageData;
import com.blog.response.BlogResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogMapper {

    @Value("${application.images.link}")
    private String imageLink;

    public BlogResponse toBlogResponse(Blog savedBlog) {

        List<ImageData> images = savedBlog.getImages();
        List<String> imageUrls = images
                .stream()
                .map(image ->
                    imageLink + image.getName()
                ).toList();

        return BlogResponse
                .builder()
                .id(savedBlog.getId())
                .title(savedBlog.getTitle())
                .content(savedBlog.getContent())
                .authorName(savedBlog.getAuthor().getFullname())
                .createdAt(savedBlog.getCreatedAt())
                .imageUrls(imageUrls)
                .build();


    }
}
