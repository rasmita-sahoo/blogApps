package com.blog.repositories;

import com.blog.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    //Find the comment by postId. No need to write whole code, just write in Repository it'll do the Work.

    List<Comment> findByPostId(long postId);

}
