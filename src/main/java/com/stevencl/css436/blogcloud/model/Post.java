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
    private Date publishDate;
    private Date lastEditDate;
    private ArrayList<String> tags;
    private String bodyText;
    private String bodyHtml;

    public String getBody() {
      return bodyHtml;
    }
    public String getTitle() {
      return title;
    }
	
      public String toString() {
      return ReflectionToStringBuilder.toString(this);
    }	


}
