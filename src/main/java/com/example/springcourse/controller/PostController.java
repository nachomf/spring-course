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
    public List<Post> getAllPosts(@PathVariable Long userId) {
        return service.findAll(userId);
    }

    @GetMapping("/users/{userId}/posts/{postId}")
    public Post getPost(
            @PathVariable Long userId,
            @PathVariable Long postId
    ) {
        return service.findOne(userId, postId);
    }

    @PostMapping("/users/{userId}/posts")
    public ResponseEntity<URI> createPost(
            @PathVariable Long userId,
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

    @DeleteMapping("/users/{userId}/posts/{postId}")
    public ResponseEntity<URI> deletePost(
            @PathVariable Long userId,
            @PathVariable Long postId
    ) {
        service.deletePost(userId, postId);
        return ResponseEntity.noContent().build();
    }
}