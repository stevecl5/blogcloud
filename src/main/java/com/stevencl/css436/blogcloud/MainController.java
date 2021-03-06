package com.stevencl.css436.blogcloud;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @Value("${test-secret}")
    private String vaultSecret;

    private final DatabaseRepository databaseRepository;
    private final BlobRepository blobRepository;

    public MainController(DatabaseRepository databaseRepository, BlobRepository blobRepository) {
        this.databaseRepository = databaseRepository;
        this.blobRepository = blobRepository;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

}
