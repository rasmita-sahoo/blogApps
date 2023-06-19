package com.blog.payload;

import lombok.Data;

import java.util.List;
@Data
public class PostResponse {
    private List<PostDto> content;
    private int pageNo;
    private int pageSize;
    private int TotalPages;
    private long TotalElements;
    private boolean Last;
}
