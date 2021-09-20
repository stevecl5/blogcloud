package com.stevencl.blogcloud.controller;

import com.stevencl.blogcloud.model.BlogRepository;
import com.stevencl.blogcloud.model.Post;
import com.stevencl.blogcloud.model.Blog;
import com.stevencl.blogcloud.model.PostRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping("/find")
public class SearchController {

    private final BlogRepository blogRepository;
    private final PostRepository postRepository;

    public SearchController(BlogRepository blogRepository, PostRepository postRepository) {
        this.blogRepository = blogRepository;
        this.postRepository = postRepository;
    }

    @GetMapping("/blog")
    public String findBlog(Model model) {
        return "findblog";
    }

    @PostMapping("/blog")
    public String findBlogResult(Model model, String author) {
        var foundBlogs = blogRepository.findByAuthor(author);
        ArrayList<Blog> blogs = new ArrayList<>();
        foundBlogs.iterator().forEachRemaining(blogs::add);
        model.addAttribute("blogs", blogs);
        return "findblog";
    }

    @GetMapping("/post")
    public String findPost(Model model) {
        return "findpost";
    }

    @PostMapping("/post")
    public String findPostResult(Model model, String title) {
        var foundPosts = postRepository.findByTitle(title);
        ArrayList<Post> posts = new ArrayList<>();
        foundPosts.iterator().forEachRemaining(posts::add);
        model.addAttribute("posts", posts);
        return "findpost";
    }

}
