package com.stevencl.css436.blogcloud;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    private final DatabaseRepository databaseRepository;
    private final BlobRepository blobRepository;

    public MainController(DatabaseRepository databaseRepository, BlobRepository blobRepository) {
        this.databaseRepository = databaseRepository;
        this.blobRepository = blobRepository;
    }

    @GetMapping("/")
    public String index(Model model, OAuth2AuthenticationToken token) {
        initializeModel(model, token);
        return "index";
    }

    private void initializeModel(Model model, OAuth2AuthenticationToken token) {
        if (token != null) {
            final OAuth2User user = token.getPrincipal();

            model.addAttribute("grant_type", user.getAuthorities());
            model.addAllAttributes(user.getAttributes());
        }
    }

}
