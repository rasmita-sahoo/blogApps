package com.blog.service;


import com.blog.payload.PostDto;
import com.blog.payload.PostResponse;
import org.springframework.ui.Model;


public interface PostService {
    static void savePost(PostDto postDto, Model model) {
    }

    PostDto createPost(PostDto postDto);

   //List<PostDto> getAllPosts();

    PostDto getPostById(long id);

    PostDto updatePost(PostDto postDto, long id);

    void deletePost(long id);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
}


//RULE: Service layer never will never return Entity.
// Will Convert Entity into DTO.