package com.stevencl.css436.blogcloud.model;

import com.azure.spring.data.cosmos.repository.CosmosRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CosmosRepository<User, String> {
    Iterable<User> findByDisplayName(String displayName);
}
