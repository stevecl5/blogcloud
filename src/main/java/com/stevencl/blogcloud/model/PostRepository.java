package com.stevencl.blogcloud.model;

import com.azure.spring.data.cosmos.repository.CosmosRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends CosmosRepository<Post, String> {
    Iterable<Post> findByBlogId(String title);
    Iterable<Post> findByTitle(String title);
}
