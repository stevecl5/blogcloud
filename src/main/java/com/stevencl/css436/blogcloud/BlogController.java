package com.stevencl.css436.blogcloud;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/blog")
public class BlogController {

    @GetMapping("/{blogId}")
    public String blogHome(@PathVariable String blogId) {
        System.out.println("blogId: " + blogId);
        return "blog";
    }

    @GetMapping("/{blogId}/{postId}")
    public String blogPost(@PathVariable String postId, @PathVariable String blogId) {
        System.out.println("blogId: " + blogId);
        System.out.println("postId: " + postId);
        return "post";
    }

}
