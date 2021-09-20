package com.stevencl.blogcloud;

import com.azure.spring.data.cosmos.repository.config.EnableCosmosRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableCosmosRepositories
public class BlogCloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogCloudApplication.class, args);
    }

}
