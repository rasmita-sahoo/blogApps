package com.blog.service.impl;

import com.blog.entities.Comment;
import com.blog.entities.Post;
import com.blog.exception.BlogAPIException;
import com.blog.exception.ResourceNotFoundException;
import com.blog.payload.CommentDTO;
import com.blog.repositories.CommentRepository;
import com.blog.repositories.PostRepository;
import com.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private PostRepository postRepository;
    private CommentRepository commentRepository;
    private ModelMapper mapper;         // Using ModelMapper

    public CommentServiceImpl(PostRepository postRepository, CommentRepository commentRepository,ModelMapper mapper) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.mapper = mapper;
    }

    // or u can use @Autowired
    @Override
    public CommentDTO saveComment(Long postId, CommentDTO commentDTO) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post not found with id: " + postId));

        Comment comment = mapToEntity(commentDTO);
        comment.setPost(post);
        Comment newComment = commentRepository.save(comment);
        return mapToDTO(newComment);
    }

    @Override
    public List<CommentDTO> getCommentByPostId(long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream().map(comment -> mapToDTO(comment)).collect(Collectors.toList());

    }

    @Override
    public CommentDTO getCommentById(long postId, long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post not found with id: " + postId));

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("comment not found with id: " + commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException("Comment doesn't belong to Post");
        }
        return mapToDTO(comment);
    }

    @Override
    public CommentDTO updateComment(long postId, long id, CommentDTO commentDTO) {
        //postId = 2 (e.g)
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post not found with id: " + postId));

        //comment.getPost().getPostId() 2 (e.g)
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("comment not found with id: " + id));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException("Comment doesn't belong to Post");
        }
        comment.setName(commentDTO.getName());
        comment.setEmail(commentDTO.getEmail());
        comment.setBoy(commentDTO.getBody());
        Comment updatedComment = commentRepository.save(comment);
        return mapToDTO(updatedComment);
    }

    // For DELETING Comments
    @Override
    public void deleteComment(long postId, long commentId) {
            // retrieve post entity by id
            Post post = postRepository.findById(postId).orElseThrow(
                    () -> new ResourceNotFoundException(("comment not found with id: " + postId)));

            // retrieve comment by id
            Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                    new ResourceNotFoundException("comment not found with id: " + commentId));

            if(!comment.getPost().getId().equals(post.getId())){
                throw new BlogAPIException("Comment does not belongs to post");
            }
            commentRepository.deleteById(commentId);
    }

    Comment mapToEntity(CommentDTO commentDTO){
        Comment comment = mapper.map(commentDTO, Comment.class);

//        Comment comment = new Comment();
//        comment.setBody(commentDTO.getBody());
//        comment.setEmail(commentDTO.getEmail());
//        comment.setName(commentDTO.getName());
        return comment;

    }
    CommentDTO mapToDTO(Comment comment) {
        CommentDTO DTO = mapper.map(comment, CommentDTO.class);

//        CommentDTO DTO = new CommentDTO();
//        DTO.setId(comment.getId());
//        DTO.setBody(comment.getBody());
//        DTO.setEmail(comment.getEmail());
//        DTO.setName(comment.getName());
        return DTO;
    }

}
