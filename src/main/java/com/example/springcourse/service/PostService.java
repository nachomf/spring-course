package com.example.springcourse.service;

import com.example.springcourse.exception.NotFoundException;
import com.example.springcourse.model.Post;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
public class PostService {
    private static final HashMap<Long, ArrayList<Post>> posts = new HashMap<>();

    public List<Post> findAll(Long userId) {
        List<Post> userPosts = posts.get(userId);

        return Objects.requireNonNullElseGet(userPosts, ArrayList::new);
    }

    public Post findOne(Long userId, Long postId) {
        return this.findAll(userId)
                .stream()
                .filter(post -> Objects.equals(post.getId(), postId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Post " + postId.toString() + " for user " + userId.toString() + " not found"));
    }

    public Post savePost(Long userId, Post post) {
        ArrayList<Post> userPosts = posts.get(userId);
        if (userPosts == null) {
            posts.put(userId, new ArrayList<>());
            userPosts = posts.get(userId);
        }

        if (post.getId() == null) {
            post.setId((long) userPosts.size() + 1);
        }

        userPosts.add(post);
        return post;
    }

    public void deletePost(Long userId, Long postId) {
        Post post = this.findOne(userId, postId);
        if (post == null) {
            throw new NotFoundException("Post " + postId.toString() + " for user " + userId.toString() + " not found");
        }

        ArrayList<Post> userPosts = posts.get(userId);
        userPosts.remove(post);
        posts.put(userId, userPosts);
    }
}
