package com.stevencl.blogcloud.model;

import com.azure.spring.data.cosmos.core.mapping.Container;
import com.azure.spring.data.cosmos.core.mapping.PartitionKey;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.data.annotation.Id;

import java.time.OffsetDateTime;

@Container(containerName = "blogs")
public class Blog {

    @Id
    private String id;
    @PartitionKey
    private String author;
    private final OffsetDateTime createdDate;

    public Blog() {
        this(null, null);
    }

    public Blog(String id, String author) {
        this.id = id;
        this.author = author;
        this.createdDate = OffsetDateTime.now();
    }

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public OffsetDateTime getCreatedDate() {
        return createdDate;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

}
