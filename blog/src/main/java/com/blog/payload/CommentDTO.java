package com.blog.payload;

import lombok.Data;
@Data
public class CommentDTO {

    private Long id;
    private String name;
    private String email;
    private String body;
    //private Long postId;      //for a comment post id is not required in DTO

}