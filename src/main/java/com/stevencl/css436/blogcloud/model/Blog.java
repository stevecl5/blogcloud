package com.stevencl.css436.blogcloud.model;

import com.azure.spring.data.cosmos.core.mapping.Container;
import com.azure.spring.data.cosmos.core.mapping.PartitionKey;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;

import java.time.OffsetDateTime;

@Container(containerName = "blogs")
public class Blog {

    @Id
    private String id;
    @PartitionKey
    private String author;
    @CreatedDate
    private OffsetDateTime createdDate;

    public Blog() {
        // default constructor
    }

    public Blog(String id, String author) {
        this.id = id;
        this.author = author;
    }

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

}
