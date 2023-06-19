package com.blog.service;

import com.blog.payload.CommentDTO;

import java.util.List;
// or u can use @Autowired

public interface CommentService {
    CommentDTO saveComment(Long postId, CommentDTO commentDTO);
    List<CommentDTO> getCommentByPostId(long postId);
    CommentDTO getCommentById(long postId, long commentId);

    CommentDTO updateComment(long postId, long id, CommentDTO commentDTO);

    void deleteComment(long postId, long commentId);


}
