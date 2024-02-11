package com.springboot.blog.springboocrudrestapi.payload;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Set;

@Data
public class PostDto {

    private Long id;
    private String title;
    private String description;
    private String content;
    private Set<CommentDto> comments;

}
