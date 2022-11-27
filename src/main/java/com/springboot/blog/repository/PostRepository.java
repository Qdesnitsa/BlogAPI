package com.springboot.blog.repository;

import com.springboot.blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    //JPQL query
    @Query("SELECT p FROM Post p WHERE p.title LIKE CONCAT('%', :query, '%') OR " +
            "p.description LIKE CONCAT('%', :query, '%')")
    List<Post> searchPosts(String query);

    //Native SQL query
//    @Query(value = "SELECT * FROM posts p WHERE p.title LIKE CONCAT('%', :query, '%') OR " +
//            "p.description LIKE CONCAT('%', :query, '%')", nativeQuery = true)
//    List<PostDto> searchPosts2(String query);
}
