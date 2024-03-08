package com.springboot.blog.springboocrudrestapi.controller;


import com.springboot.blog.springboocrudrestapi.entity.Post;
import com.springboot.blog.springboocrudrestapi.payload.PostDto;
import com.springboot.blog.springboocrudrestapi.payload.PostResponse;
import com.springboot.blog.springboocrudrestapi.service.CategoryService;
import com.springboot.blog.springboocrudrestapi.service.PostService;
import com.springboot.blog.springboocrudrestapi.utils.AppConstants;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {


    private PostService postService;


    public PostController(PostService postService) {
        this.postService = postService;
    }

    // create blog post

    @SecurityRequirement(
            name = "Bear Authentication"

    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto) {

        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);


    }


    // get all post rest api
    @GetMapping
    public PostResponse getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ){
        return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
    }

    // get post by id


    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") long id) {



        return ResponseEntity.ok(postService.getPostById(id));
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<List<PostDto>> getPostByCategoryId(@PathVariable("id") long categoryId) {


        return ResponseEntity.ok(postService.getPostByCategories(categoryId));
    }

    // update post by id rest api

    @SecurityRequirement(
            name = "Bear Authentication"

    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{id}")
    public ResponseEntity<PostDto> updatePostById(@Valid @RequestBody PostDto postDto, @PathVariable("id") long id){


        PostDto postResponse = postService.updatePosts(postDto,id);

        return new ResponseEntity<>(postResponse, HttpStatus.OK);

    }

    // delete post by id

    @SecurityRequirement(
            name = "Bear Authentication"

    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") long id){

        postService.deletePosts(id);


        return new ResponseEntity<>("Post entity delete successfully", HttpStatus.OK);

    }


}
