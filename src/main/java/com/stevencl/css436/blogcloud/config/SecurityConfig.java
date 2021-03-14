package com.stevencl.css436.blogcloud.config;

import com.stevencl.css436.blogcloud.model.Blog;
import com.stevencl.css436.blogcloud.model.BlogRepository;

import com.azure.spring.autoconfigure.b2c.AADB2COidcLoginConfigurer;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.core.user.OAuth2User;

/**
 * https://docs.microsoft.com/en-us/azure/developer/java/spring-framework/configure-spring-boot-starter-java-app-with-azure-active-directory-b2c-oidc
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AADB2COidcLoginConfigurer configurer;
    private final BlogRepository blogRepository;

    public SecurityConfig(AADB2COidcLoginConfigurer configurer, BlogRepository blogRepository) {
        this.configurer = configurer;
        this.blogRepository = blogRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/myblog/**").authenticated()
                .antMatchers("/test/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .apply(configurer);
    }

    @EventListener
    public void authSuccessEventListener(AuthenticationSuccessEvent authorizedEvent) {
        System.out.println("auth success");
        var user = (OAuth2User) authorizedEvent.getAuthentication().getPrincipal();
        var oid = user.getName();
        var displayName = (String) user.getAttribute("name");
        // create blog for new user
        if (user.getAttributes().containsKey("newUser")) {
            var blog = new Blog(oid, displayName);
            blogRepository.save(blog);
            System.out.println("created blog: " + oid);
        } else {
            // add blog if user doesn't have one
            // TODO: this feature should be removed in production
            var result = blogRepository.findById(oid);
            if (result.isEmpty()) {
                var blog = new Blog(oid, displayName);
                blogRepository.save(blog);
                System.out.println("created blog: " + oid);
            }
            // update blog author if display name is different
            var blog = result.orElse(null);
            if (blog != null && displayName != null && !displayName.equals(blog.getAuthor())) {
                blog.setAuthor(displayName);
                blogRepository.save(blog);
            }
        }
    }

}
