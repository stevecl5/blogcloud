package com.stevencl.css436.blogcloud.controller;

import com.stevencl.css436.blogcloud.model.ImageRepository;
import com.stevencl.css436.blogcloud.model.UserRepository;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    private final UserRepository userRepository;
    private final ImageRepository imageRepository;

    public MainController(UserRepository userRepository, ImageRepository imageRepository) {
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
    }

    @GetMapping("/")
    public String index(Model model, OAuth2AuthenticationToken token) {
        initializeModel(model, token);
        return "index";
    }

    @GetMapping("/account")
    public String account(Model model, OAuth2AuthenticationToken token) {
        initializeModel(model, token);
        var x = token.getPrincipal().getAttributes();
        var z = token.getPrincipal().getName();
        System.out.println("name: " + z);
        for (var y : x.entrySet()) {
            System.out.println(y);
        }
        return "account";
    }

    private void initializeModel(Model model, OAuth2AuthenticationToken token) {
        if (token != null) {
            final OAuth2User user = token.getPrincipal();

            model.addAttribute("grant_type", user.getAuthorities());
            model.addAllAttributes(user.getAttributes());
        }
    }

}
