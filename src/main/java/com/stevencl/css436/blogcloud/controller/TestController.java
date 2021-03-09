package com.stevencl.css436.blogcloud.controller;

import com.stevencl.css436.blogcloud.model.ImageRepository;
import com.stevencl.css436.blogcloud.model.UserRepository;
import com.stevencl.css436.blogcloud.model.Post;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/test")
public class TestController {

    @Value("${test-secret}")
    private String vaultSecret;

    @Value("${tiny.secret-key}")
    private String tinySecret;

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

    @GetMapping("/tiny")
	public String addEditPost(Model model, @RequestParam("blogPostId") Optional<String> blogPostId) {		

        setDefaultBlogPost(model);					
		return "tiny";
	}
	

	@PostMapping("/tiny")
	public String addEditPostSubmit(Model model, Post blogPost) {
        System.out.println("Title:");
        System.out.println(blogPost.getTitle());
        System.out.println();
	    System.out.println("Body:");
        System.out.println(blogPost.getBodyHtml());
        System.out.println();
	    System.out.println("Secret Key is " + tinySecret);

	    model.addAttribute("postTitle", blogPost.getTitle());
	    model.addAttribute("postBody", blogPost.getBodyHtml());
		return "result";
	}

	
	private void setDefaultBlogPost(Model model) {
		Post blogPost = new Post();		
		model.addAttribute("blogPost", blogPost);
	}
}
