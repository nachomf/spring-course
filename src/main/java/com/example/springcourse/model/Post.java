package com.example.springcourse.model;

import java.util.Date;

public class Post {
    private Date timestamp;
    private Long id;
    private String message;

    public Post(Date timestamp, Long id, String message) {
        this.id = id;
        this.timestamp = timestamp;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
