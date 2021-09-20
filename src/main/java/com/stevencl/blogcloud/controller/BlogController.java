package com.stevencl.blogcloud.controller;

import com.stevencl.blogcloud.model.BlogRepository;
import com.stevencl.blogcloud.model.ImageRepository;
import com.stevencl.blogcloud.model.Post;
import com.stevencl.blogcloud.model.PostRepository;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.ArrayList;

@Controller
@RequestMapping("/blog")
public class BlogController {

    private final BlogRepository blogRepository;
    private final PostRepository postRepository;
    private final ImageRepository imageRepository;

    public BlogController(BlogRepository blogRepository, PostRepository postRepository,
                          ImageRepository imageRepository) {
        this.blogRepository = blogRepository;
        this.postRepository = postRepository;
        this.imageRepository = imageRepository;
    }

    @GetMapping("/{blogId}")
    public String blogHome(@PathVariable String blogId, Model model) throws NoHandlerFoundException {
        // query database for matching blog
        var result = blogRepository.findById(blogId);
        // 404 if not found
        if (result.isEmpty()) {
            throw new NoHandlerFoundException("GET", "/blog/" + blogId, new HttpHeaders());
        }
        var blog = result.get();
        model.addAttribute("blog", blog);
        // lookup posts by blogId
        var foundPosts = postRepository.findByBlogId(blogId);
        ArrayList<Post> posts = new ArrayList<>();
        foundPosts.iterator().forEachRemaining(posts::add);
        model.addAttribute("posts", posts);
        return "blog";
    }

    @GetMapping("/{blogId}/{postId}")
    public String blogPost(@PathVariable String postId, @PathVariable String blogId,
                           Model model) throws NoHandlerFoundException {
        // query database for post
        var result = postRepository.findById(postId);
        // 404 if not found
        if (result.isEmpty() || !blogId.equals(result.get().getBlogId())) {
            throw new NoHandlerFoundException("GET", "/blog/" + blogId + "/" + "postId", new HttpHeaders());
        }
        var post = result.get();
        model.addAttribute("post", post);
        System.out.println("blogId: " + blogId);
        System.out.println("postId: " + postId);
        return "post";
    }

}
