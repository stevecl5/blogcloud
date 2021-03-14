package com.stevencl.css436.blogcloud.model;

import com.azure.spring.data.cosmos.core.mapping.Container;
import com.azure.spring.data.cosmos.core.mapping.GeneratedValue;
import com.azure.spring.data.cosmos.core.mapping.PartitionKey;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.OffsetDateTime;

@Container(containerName = "posts")
public class Post {

    @Id
    @GeneratedValue
    private String id;
    @PartitionKey
    private String blogId;
    private String title;
    private String bodyHtml;
    @CreatedDate
    private OffsetDateTime createdDate;
    @LastModifiedDate
    private OffsetDateTime lastModifiedDate;

    public String getId() {
        return id;
    }

    public String getBlogId() {
        return blogId;
    }

    public String getTitle() {
        return title;
    }

    public String getBodyHtml() {
        return bodyHtml;
    }

    public OffsetDateTime getCreatedDate() {
        return createdDate;
    }

    public OffsetDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBlogId(String userId) {
        this.blogId = userId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBodyHtml(String bodyHtml) {
        this.bodyHtml = bodyHtml;
    }

    public void setCreatedDate(OffsetDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public void setLastModifiedDate(OffsetDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

}
