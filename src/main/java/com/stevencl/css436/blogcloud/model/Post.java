package com.stevencl.css436.blogcloud.model;

import com.azure.spring.data.cosmos.core.mapping.Container;
import com.azure.spring.data.cosmos.core.mapping.PartitionKey;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.Date;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@Container(containerName = "posts")
public class Post {

    @Id
    private String id;
    @PartitionKey
    private String title;
    private String author;
    private String bodyText;
    private String bodyHtml;
//    private Date publishDate;
//    private Date lastEditDate;
//    private ArrayList<String> tags;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getBodyText() {
        return bodyText;
    }

    public String getBodyHtml() {
        return bodyHtml;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setBodyText(String bodyText) {
        this.bodyText = bodyText;
    }

    public void setBodyHtml(String bodyHtml) {
        this.bodyHtml = bodyHtml;
    }

}
