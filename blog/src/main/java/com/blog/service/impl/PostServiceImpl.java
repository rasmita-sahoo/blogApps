package com.blog.service.impl;
import com.blog.entities.Post;
import com.blog.exception.ResourceNotFoundException;
import com.blog.payload.PostDto;
import com.blog.payload.PostResponse;
import com.blog.repositories.PostRepository;
import com.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
     private ModelMapper mapper;

    public PostServiceImpl(PostRepository postRepository, ModelMapper mapper) {
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    @Override        //To Create the Post ----------------------------------------------------------------------
    public PostDto createPost(PostDto postDto) {

        //If u want to convert Entity to Dto,no need to write the code, just call the method.
        Post post = mapToEntity(postDto);
        Post savedPost = postRepository.save(post);

        PostDto dto = mapToDto(post);
        return dto;
    }
//    @Override       //For Showing All DATA in LIST format ----------------------------------------------------
//    public List<PostDto> getAllPosts() {
//        List<Post> posts = postRepository.findAll();
//       return posts.stream().map(post->mapToDto(post)).collect(Collectors.toList());
//                                    // OR
//        //List<PostDto> postDtos = posts.stream().map(post->mapToDto(post)).collect(Collectors.toList());
//        //return postDtos;
//    }

    @Override         //For GET Records/data BY ID ------------------------------------------------------------
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post Not Found id: " + id));    //Used Lambda Expression
        return mapToDto(post);
    }

    @Override        //For UPDATE ----------------------------------------------------------------------------
    public PostDto updatePost(PostDto postDto, long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post not found with id:" + id));    //Used Lambda Expression

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());
        Post updatedPost = postRepository.save(post);       // To save Post or DATA
        return mapToDto(updatedPost);                      //To convert Entity to Dto

    }

    @Override      //For DELETE by Id ------------------------------------------------------------------------
    public void deletePost(long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post not found with id:" + id));
        postRepository.deleteById(id);
    }

    @Override     //For PAGINATION --------------------------------------------------------------------------
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        PageRequest pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Post> content = postRepository.findAll(pageable);
        List<Post> posts = content.getContent();                       //convert back return type --page to LIST
        List<PostDto> dtos = posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(dtos);
        postResponse.setPageNo( content.getNumber());
        postResponse.setPageSize( content.getSize());
        postResponse.setTotalPages( content.getTotalPages());
        postResponse.setTotalElements( content.getTotalElements());
        postResponse.setLast(content.isLast());
        return postResponse;
    }

         // Mapping DTO to ENTITY---------------------------------------------------------------------------
    Post mapToEntity(PostDto postDto) {
        Post post = mapper.map(postDto, Post.class);

//        Post post = new Post();
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
       return post;
    }
         // Mapping ENTITY to DTO---------------------------------------------------------------------------
    PostDto mapToDto(Post post){
       PostDto dto = mapper.map(post, PostDto.class);

//        PostDto dto = new PostDto();       //give Entity data to DTO.
//        dto.setId(post.getId());
//        dto.setTitle(post.getTitle());
//        dto.setDescription(post.getDescription());
//        dto.setContent(post.getContent());
        return dto;

    }
}
