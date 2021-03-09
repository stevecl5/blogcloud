package com.stevencl.css436.blogcloud.model;

import com.azure.spring.data.cosmos.repository.CosmosRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends CosmosRepository<Post, String> {
    Iterable<Post> findByTitle(String title);
}
