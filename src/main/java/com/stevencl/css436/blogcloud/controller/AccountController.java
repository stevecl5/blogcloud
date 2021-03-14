package com.stevencl.css436.blogcloud.controller;

import com.stevencl.css436.blogcloud.model.BlogRepository;
import com.stevencl.css436.blogcloud.model.ImageRepository;
import com.stevencl.css436.blogcloud.model.PostRepository;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        var userId = user.getName();
        var blog = blogRepository.findById(userId);
//        System.out.println("authorities: " + user.getAuthorities());
//        System.out.println("name: " + user.getAttribute("name"));
//        System.out.println("oid: " + user.getAttribute("oid"));
//        var x = token.getPrincipal().getAttributes();
//        for (var y : x.entrySet()) {
//            System.out.println(y);
//        }
        return "account";
    }

    @GetMapping("/create")
    public String createBlog() {
        return "redirect:/myblog";
    }

}
