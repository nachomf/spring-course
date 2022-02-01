package com.example.springcourse.controller;

import com.example.springcourse.model.Post;
import com.example.springcourse.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class PostController {

    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    @GetMapping("/users/{userId}/posts")
    public List<Post> getAllPosts(@PathVariable Integer userId) {
        return service.findAll(userId);
    }

    @GetMapping("/users/{userId}/posts/{postId}")
    public Post getPost(
            @PathVariable Integer userId,
            @PathVariable Integer postId
    ) {
        return service.findOne(userId, postId);
    }

    @PostMapping("/users/{userId}/posts")
    public ResponseEntity<URI> createPost(
            @PathVariable Integer userId,
            @RequestBody Post post
    ) {
        Post savedPost = service.savePost(userId,post);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPost.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}