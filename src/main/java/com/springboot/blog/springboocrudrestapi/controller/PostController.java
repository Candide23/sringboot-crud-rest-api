package com.springboot.blog.springboocrudrestapi.controller;


import com.springboot.blog.springboocrudrestapi.payload.PostDto;
import com.springboot.blog.springboocrudrestapi.payload.PostResponse;
import com.springboot.blog.springboocrudrestapi.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/posts")
public class PostController {


    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // create blog post

    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto) {

        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);


    }


    // get all post rest api
    @GetMapping()
    public PostResponse getAllPost(@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo, @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize){

        return postService.getAllPosts(pageNo, pageSize);


    }

    // get post by id

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") long id) {



        return ResponseEntity.ok(postService.getPostById(id));
    }

    // update post by id rest api

    @PutMapping("{id}")
    public ResponseEntity<PostDto> updatePostById(@RequestBody PostDto postDto, @PathVariable("id") long id){


        PostDto postResponse = postService.updatePosts(postDto,id);

        return new ResponseEntity<>(postResponse, HttpStatus.OK);

    }

    // delete post by id

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") long id){

        postService.deletePosts(id);


        return new ResponseEntity<>("Post entity delete successfully", HttpStatus.OK);




    }


}
