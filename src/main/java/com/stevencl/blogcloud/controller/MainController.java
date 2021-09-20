package com.stevencl.blogcloud.controller;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String index(Model model) {
        String imageUrl = "https://blogcloud.blob.core.windows.net/images/blogcloud-small.jpg";
        model.addAttribute("imageUrl", imageUrl);
        return "index";
    }

    protected static void initializeModel(Model model, OAuth2AuthenticationToken token) {
        if (token != null) {
            final OAuth2User user = token.getPrincipal();

            model.addAttribute("grant_type", user.getAuthorities());
            model.addAllAttributes(user.getAttributes());
        }
    }

}
