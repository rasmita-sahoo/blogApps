package com.blog.controller;

import com.blog.payload.CommentDTO;
import com.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {
    private CommentService commentService;     //Dependency Injection
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    //http://localhost:8080/api/post/1/comments
    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<CommentDTO> createComment(
            @PathVariable("postId") long postId,
            @RequestBody CommentDTO commentDTO) {

        CommentDTO DTO = commentService.saveComment(postId, commentDTO);
        return new ResponseEntity<>(DTO, HttpStatus.CREATED);
    }

    //Using Handler Methods
    //http://localhost:8080/api/post/1/comments
    @GetMapping("/post/{postId}/comments")
    public List<CommentDTO> getCommentsByPostId(@PathVariable("postId") long postId) {
        return commentService.getCommentByPostId(postId);
    }

    //To check SAME POST ID for same COMMENT ID
    //http://localhost:8080/api/post/1/comments/1
    @GetMapping("/post/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> getCommentsByPostId(
            @PathVariable("postId") long postId,
            @PathVariable("commentId") long commentId) {
        CommentDTO dto = commentService.getCommentById(postId, commentId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    // UPDATE Comment
    //http://localhost:8080/api/post/{postId}/comments/{commentId}
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/post/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable("postId") long postId,
                                                    @PathVariable("commentId") long commentId,
                                                    @RequestBody CommentDTO commentDTO) {
        // @RequestBody takes the content of JSON & put that into DTO.

        CommentDTO dto = commentService.updateComment(postId,commentId,commentDTO);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    // For DELETING Comments
    //http://localhost:8080/api/post/{postId}/comments/{commentId}
    @DeleteMapping("/post/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable("postId") long postId,
                                                @PathVariable("commentId") long commentId){
        commentService.deleteComment(postId, commentId);
        return new ResponseEntity<>("Comment deleted successfully", HttpStatus.OK);
    }

}

