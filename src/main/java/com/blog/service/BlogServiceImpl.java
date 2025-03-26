package com.blog.service;

import com.blog.entity.Blog;
import com.blog.entity.User;
import com.blog.image.ImageData;
import com.blog.image.ImageService;
import com.blog.mapper.BlogMapper;
import com.blog.repo.BlogRepository;
import com.blog.request.BlogCreationRequest;
import com.blog.request.BlogUpdateRequest;
import com.blog.response.BlogResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;
    private final UserService userService;
    private final BlogMapper blogMapper;
    private final HuggingFaceSummarizationService aiService;
    private final ImageService imageService;


    @Override
    public BlogResponse createBlog(BlogCreationRequest request, String token, List<MultipartFile> images) throws IOException {

        User user = userService.findByJwtToken(token);
        Blog blog = Blog
                .builder()
                .title(request.title())
                .content(request.content())
                .author(user)
                .createdAt(LocalDateTime.now())
                .build();

//        get all the images and attach it to the blog
        List<ImageData> blogImages = imageService.uploadImage(images);
        blog.setImages(blogImages);

        Blog savedBlog = blogRepository.save(blog);

        return blogMapper.toBlogResponse(savedBlog);
    }

    public Blog getBlogByIdAndUser(Integer blogId, User user) {

        List<Blog> allUserBlogs = blogRepository
                .findAllByAuthor(user)
                .orElseThrow(() -> new EntityNotFoundException("No blog found by user with email: " + user.getEmail()));

        return allUserBlogs.stream()
                .filter(blog -> blogId.equals(blog.getId()))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("No blog found by user with email: " + user.getEmail()));
    }

    @Override
    public BlogResponse updateBlog(BlogUpdateRequest request, Integer blogId, String token, List<MultipartFile> images) throws IOException {

        User user = userService.findByJwtToken(token);
        Blog oldBlog = getBlogByIdAndUser(blogId, user);

        if (request.title() != null) {
            oldBlog.setTitle(request.title());
        }
        if (request.content() != null) {
            oldBlog.setContent(request.content());
        }

        List<ImageData> newImages = imageService.updateImage(oldBlog.getImages(), images);
        oldBlog.setImages(newImages);

        Blog savedBlog = blogRepository.save(oldBlog);

        return blogMapper.toBlogResponse(savedBlog);
    }

    @Override
    public void deleteBlog(Integer blogId, String token) {
        User user = userService.findByJwtToken(token);
        Blog blog = getBlogByIdAndUser(blogId, user);

        if (blog != null) {
            blogRepository.delete(blog);
        }
    }

    @Override
    public BlogResponse getBlogById(Integer blogId) {
        Blog blog = blogRepository
                .findById(blogId)
                .orElseThrow(() -> new EntityNotFoundException("No blog found by user with id: " + blogId));
        return blogMapper.toBlogResponse(blog);
    }

    @Override
    public Blog findUnsafeBlogById(Integer blogId) {
        return blogRepository
                .findById(blogId)
                .orElseThrow(() -> new EntityNotFoundException("No blog found with id: " + blogId));
    }

    @Override
    public String summarizeBlog(Integer blogId) {
        Blog blog = findUnsafeBlogById(blogId);
        return aiService.summarizeText(blog.getContent());
    }

    @Override
    public Page<BlogResponse> getAllBlogs(Integer page, Integer size, String sortBy, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Blog> blogPage = blogRepository.findAll(pageable);

        List<BlogResponse> blogResponses = blogPage
                .getContent()
                .stream()
                .map(blogMapper::toBlogResponse)
                .toList();

        return  new PageImpl<>(blogResponses, pageable, blogPage.getTotalElements());

    }


}
