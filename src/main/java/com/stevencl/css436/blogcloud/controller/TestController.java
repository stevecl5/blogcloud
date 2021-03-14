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

import java.util.Optional;
import java.util.HashMap;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/test")
public class TestController {

    @Value("${test-secret}")
    private String vaultSecret;

    @Value("${tiny.secret-key}")
    private String tinySecret;
    
    private HashMap<Integer, Post> testMap = new HashMap<Integer, Post>();

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

    @GetMapping("/create")
	public String addEditPost(Model model) {		

        setDefaultBlogPost(model);
		return "create";
	}
	

	@PostMapping("/create")
	public String addEditPostSubmit(Model model, Post blogPost) {
        blogPost.setTestId(blogPost.getTestId() - 1);
        blogPost.decrementCount();

        int id = blogPost.getTestId();
        testMap.put(id, blogPost);

        model.addAttribute("testMap", testMap);
		return "result";
	}

    @GetMapping("/editPage")
    public String editPage(Model model) {
        model.addAttribute("testMap", testMap);
        return "editPage";
    }

    @GetMapping("/edit/{postId}") 
    public String editPost(Model model, @PathVariable("postId") int postId) {
        Post blogPost = testMap.get(postId);

        model.addAttribute("blogPost", blogPost);
        model.addAttribute("postId", postId);
        return "edit";
    }

    @PostMapping("/edit/{postId}")
    public String editPostSubmit(Model model, Post blogPost, @PathVariable("postId") int postId) {
        
        blogPost.setTestId(postId);
        blogPost.decrementCount();
        testMap.replace(postId, blogPost);
        model.addAttribute("testMap", testMap);
       
        return "result";
    }
	
	private void setDefaultBlogPost(Model model) {
		Post blogPost = new Post();	
		model.addAttribute("blogPost", blogPost);

	}
}
