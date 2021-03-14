package com.stevencl.css436.blogcloud.model;

import com.azure.spring.data.cosmos.core.mapping.Container;
import com.azure.spring.data.cosmos.core.mapping.GeneratedValue;
import com.azure.spring.data.cosmos.core.mapping.PartitionKey;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.data.annotation.Id;

import java.time.OffsetDateTime;

@Container(containerName = "posts")
public class Post {

    private static int count = 0;
    private int testId;
    @Id
    @GeneratedValue
    private String id;
    @PartitionKey
    private String blogId;
    private String title;
    private String bodyHtml;
    private final OffsetDateTime createdDate;
    private OffsetDateTime lastModifiedDate;

    public Post() {
        this(null, null, null);
        this.testId = count;
        count++;
    }

    public Post(String blogId, String title, String bodyHtml) {
        this.blogId = blogId;
        this.title = title;
        this.bodyHtml = bodyHtml;
        this.createdDate = OffsetDateTime.now();
    }

    public int getTestId() {
        return testId;
    }
    public void setTestId(int testId) {
        this.testId = testId;
    }

    public void decrementCount() {
        count--;
    }

    public int getCount() {
        return count;
    }
    
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

    public void setLastModifiedDate() {
        this.lastModifiedDate = OffsetDateTime.now();
    }

}
