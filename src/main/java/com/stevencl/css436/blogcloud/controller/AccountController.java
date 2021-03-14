package com.stevencl.css436.blogcloud.controller;

import com.stevencl.css436.blogcloud.model.BlogRepository;
import com.stevencl.css436.blogcloud.model.ImageRepository;
import com.stevencl.css436.blogcloud.model.Post;
import com.stevencl.css436.blogcloud.model.PostRepository;

import org.springframework.http.HttpHeaders;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.ArrayList;

@Controller
@RequestMapping("/myblog")
public class AccountController {

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
        model.addAttribute("blog", blogRepository);
        // lookup posts by blogId
        var foundPosts = postRepository.findByBlogId(oid);
        ArrayList<Post> posts = new ArrayList<>();
        foundPosts.iterator().forEachRemaining(posts::add);
        model.addAttribute("posts", posts);
        return "account";
    }

    @GetMapping("/create")
    public String createBlog() {
        return "redirect:/myblog";
    }

    @GetMapping("/edit")
    public String editPost(@RequestParam String postId, OAuth2AuthenticationToken token,
                           Model model) throws NoHandlerFoundException{
        var user = token.getPrincipal();
        var oid = user.getName();

        // lookup post by postId
        var result = postRepository.findById(postId);
        // 404 if not found
        if (result.isEmpty()) {
            throw new NoHandlerFoundException("GET", "/myblog/edit?postId=" + postId, new HttpHeaders());
        }
        var post = result.get();
        // 403 if post doesn't belong to this user
        if (!oid.equals(post.getBlogId())) {
            throw new AccessDeniedException("403 Access Denied");
        }
        model.addAttribute("post", post);
        System.out.println(postId);
        return "redirect:/myblog";
    }


}
