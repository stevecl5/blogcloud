package com.stevencl.blogcloud.controller;

import com.stevencl.blogcloud.model.BlogRepository;
import com.stevencl.blogcloud.model.ImageRepository;
import com.stevencl.blogcloud.model.Post;
import com.stevencl.blogcloud.model.PostRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.ArrayList;

@Controller
@RequestMapping("/myblog")
public class AccountController {

    @Value("${tinymce-url}")
    private String tinyUrl;

    private final BlogRepository blogRepository;
    private final PostRepository postRepository;
    private final ImageRepository imageRepository;

    public AccountController(BlogRepository blogRepository, PostRepository postRepository,
                          ImageRepository imageRepository) {
        this.blogRepository = blogRepository;
        this.postRepository = postRepository;
        this.imageRepository = imageRepository;
    }

    @GetMapping("")
    public String myBlog(Model model, OAuth2AuthenticationToken token) {
        MainController.initializeModel(model, token);
        // lookup blog by user oid
        var user = token.getPrincipal();
        var oid = user.getName();
        var foundBlog = blogRepository.findById(oid);
        var blog = foundBlog.orElse(null);
        model.addAttribute("blog", blog);
        // lookup posts by blogId
        var foundPosts = postRepository.findByBlogId(oid);
        ArrayList<Post> posts = new ArrayList<>();
        foundPosts.iterator().forEachRemaining(posts::add);
        model.addAttribute("posts", posts);
        return "myblog";
    }

    @GetMapping("/join")
    public String join() {
        return "redirect:/myblog";
    }

    @GetMapping("/create")
    public String createPost(Model model, OAuth2AuthenticationToken token) {
        var user = token.getPrincipal();
        var oid = user.getName();
        Post blogPost = new Post();
        blogPost.setBlogId(oid);
        System.out.println(oid);
        System.out.println(blogPost);
        model.addAttribute("blogPost", blogPost);
        model.addAttribute("tinyUrl", tinyUrl);
        model.addAttribute("submitPath", "/myblog/create");
        return "edit";
    }

    @GetMapping("/edit/{postId}")
    public String editPost(@PathVariable String postId, OAuth2AuthenticationToken token,
                           Model model) throws Exception {
        var user = token.getPrincipal();
        var oid = user.getName();
        // lookup post by postId
        var result = postRepository.findById(postId);
        // 404 if not found
        if (result.isEmpty()) {
            throw new NoHandlerFoundException("GET", "/myblog/edit/" + postId, new HttpHeaders());
        }
        var blogPost = result.get();
        // 403 if post doesn't belong to this user
        if (!oid.equals(blogPost.getBlogId())) {
            throw new AccessDeniedException("403 Access Denied");
        }
        model.addAttribute("blogPost", blogPost);
        model.addAttribute("tinyUrl", tinyUrl);
        model.addAttribute("submitPath", "/myblog/edit");
        return "edit";
    }

    @PostMapping(value = {"/create", "/edit"})
    public String addEditPostSubmit(@ModelAttribute Post blogPost) {
        System.out.println(blogPost);
        postRepository.save(blogPost);
        return "redirect:/myblog";
    }

    @GetMapping("/delete/{postId}") 
    public String deletePost(@PathVariable String postId, OAuth2AuthenticationToken token,
                            Model model) throws Exception{

        var user = token.getPrincipal();
        var oid = user.getName();
        
        var result = postRepository.findById(postId);
        // 404 if not found
        if (result.isEmpty()) {
            throw new NoHandlerFoundException("GET", "/myblog/edit/" + postId, new HttpHeaders());
        }
        var blogPost = result.get();
        // 403 if post doesn't belong to this user
        if (!oid.equals(blogPost.getBlogId())) {
            throw new AccessDeniedException("403 Access Denied");
        }
        model.addAttribute("blogPost", blogPost);
        
        return "delete";
    }

    @PostMapping("/delete/{postId}")
    public String deletePostSubmit(@ModelAttribute Post blogPost) {
        System.out.println(blogPost);
        postRepository.delete(blogPost);
        return "redirect:/myblog";
    }

}
