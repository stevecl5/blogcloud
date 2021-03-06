package com.stevencl.css436.blogcloud;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class TestController {

    @Value("${test-secret}")
    private String vaultSecret;

    private final DatabaseRepository databaseRepository;
    private final BlobRepository blobRepository;

    public TestController(DatabaseRepository databaseRepository, BlobRepository blobRepository) {
        this.databaseRepository = databaseRepository;
        this.blobRepository = blobRepository;
    }

    @GetMapping("/blob")
    public String blobTest() {
        System.out.println("blob test");
        blobRepository.getContainerName();
        return "redirect:/";
    }

    @GetMapping("/db")
    public String dbTest() {
        System.out.println("db test");
        databaseRepository.listContainers();
        return "redirect:/";
    }

    @GetMapping("/secret")
    public String secretTest() {
        System.out.println("secret test");
        System.out.println(vaultSecret);
        return "redirect:/";
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
