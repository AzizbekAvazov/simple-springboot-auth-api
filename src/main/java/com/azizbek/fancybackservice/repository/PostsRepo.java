package com.azizbek.fancybackservice.repository;

import com.azizbek.fancybackservice.entity.Posts;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Creator: Azizbek Avazov
 * Date: 20.08.2022
 * Time: 17:38
 */
@Repository
public interface PostsRepo extends CrudRepository<Posts, Long> {
    @Query(value = "SELECT * FROM posts WHERE owner_id=:owner_id", nativeQuery = true)
    List<Posts> getUserPosts(@Param(value = "owner_id") long user_id);

    @Query(value = "SELECT * FROM posts", nativeQuery = true)
    List<Posts> getAllPosts();

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM posts WHERE post_id=:post_id", nativeQuery = true)
    void deletePostById(@Param(value = "post_id") long post_id);
}
