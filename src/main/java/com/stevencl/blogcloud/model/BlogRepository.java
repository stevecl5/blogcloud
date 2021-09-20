package com.stevencl.blogcloud.model;

import com.azure.spring.data.cosmos.repository.CosmosRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepository extends CosmosRepository<Blog, String> {
    Iterable<Blog> findByAuthor(String author);
}
