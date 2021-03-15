package com.stevencl.css436.blogcloud.controller;

import com.stevencl.css436.blogcloud.model.BlogRepository;
import com.stevencl.css436.blogcloud.model.ImageRepository;
import com.stevencl.css436.blogcloud.model.Post;
import com.stevencl.css436.blogcloud.model.PostRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;

@Controller
@RequestMapping("/test")
public class TestController {

    @Value("${test-secret}")
    private String vaultSecret;

    @Value("${tiny.secret-key}")
    private String tinySecret;
    
    private HashMap<Integer, Post> testMap = new HashMap<>();

    private final BlogRepository blogRepository;
    private final PostRepository postRepository;
    private final ImageRepository imageRepository;

    public TestController(BlogRepository blogRepository, PostRepository postRepository,
                          ImageRepository imageRepository) {
        this.blogRepository = blogRepository;
        this.postRepository = postRepository;
        this.imageRepository = imageRepository;
    }

    @GetMapping("/blob")
    public String blobTest() {
        System.out.println("blob test");
        imageRepository.getContainerName();
        return "forward:/";
    }

    @GetMapping("/db")
    public String dbTest() {
        System.out.println("db test");
//        var x = blogRepository.findByAuthor("bobby83");
//        var iter = x.iterator();
//        if (iter.hasNext()) {
//            System.out.println(iter.next());
//        }
        var post = new Post();
        post.setBlogId("1234");
        post.setTitle("title");
        post.setBodyHtml("<p>blah blah</p>");
        postRepository.save(post);
        return "forward:/";
    }

    @GetMapping("/secret")
    public String secretTest() {
        System.out.println("secret test");
        System.out.println(vaultSecret);
        return "forward:/";
    }

}
