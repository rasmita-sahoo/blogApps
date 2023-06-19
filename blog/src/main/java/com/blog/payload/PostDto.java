package com.blog.payload;

import lombok.Data;
import javax.validation.constraints.NotEmpty;


@Data
public class PostDto {
    private Long id;

    @NotEmpty       //is used to ensure that a field is not empty.-----------------------------------------------------
    //@Size(min = 5, message = "Post title should have at least 5 characters")
    private String title;

    @NotEmpty
    //@Size(min = 200, message = "Post description should have at least 500 characters")  //It'll show this msg in Postman.
    private String description;

    @NotEmpty
    private String content;


}
