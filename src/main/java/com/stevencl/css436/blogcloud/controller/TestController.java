package com.stevencl.css436.blogcloud.controller;

import com.stevencl.css436.blogcloud.model.ImageRepository;
import com.stevencl.css436.blogcloud.model.UserRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class TestController {

    @Value("${test-secret}")
    private String vaultSecret;

    private final UserRepository userRepository;
    private final ImageRepository imageRepository;

    public TestController(UserRepository userRepository, ImageRepository imageRepository) {
        this.userRepository = userRepository;
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
        var x = userRepository.findByDisplayName("bobby83");
        var iter = x.iterator();
        if (iter.hasNext()) {
            System.out.println(iter.next().getDisplayName());
        }
        return "forward:/";
    }

    @GetMapping("/secret")
    public String secretTest() {
        System.out.println("secret test");
        System.out.println(vaultSecret);
        return "forward:/";
    }

    @GetMapping("/form")
    public String formEntry() {
        System.out.println("form entry");
        return "entry";
    }

    @PostMapping("/form")
    public String formResult(String postTitle, String postBody, Model model) {
        System.out.println("form results");
        System.out.println(postBody);
        model.addAttribute("postTitle", postTitle);
        model.addAttribute("postBody", postBody);
        return "result";
    }
}
