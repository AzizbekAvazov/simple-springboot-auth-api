package com.azizbek.fancybackservice.service;

import com.azizbek.fancybackservice.entity.Posts;
import com.azizbek.fancybackservice.repository.PostsRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Creator: Azizbek Avazov
 * Date: 21.08.2022
 * Time: 13:16
 */
@Service
public class PostsService {
    private final PostsRepo postsRepo;

    public PostsService(PostsRepo postsRepo) {
        this.postsRepo = postsRepo;
    }

    public Posts savePost(Posts post) {
        return postsRepo.save(post);
    }

    public List<Posts> getUserPosts(long user_id) {
        return postsRepo.getUserPosts(user_id);
    }

    public List<Posts> getAllPosts() {
        return postsRepo.getAllPosts();
    }

    public void deletePostById(long post_id) {
        postsRepo.deletePostById(post_id);
    }

    public Optional<Posts> getPostById(long post_id) {
        return postsRepo.findById(post_id);
    }
}
