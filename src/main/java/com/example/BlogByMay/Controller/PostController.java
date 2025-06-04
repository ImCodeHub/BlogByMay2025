package com.example.BlogByMay.Controller;

import com.example.BlogByMay.Entity.Comment;
import com.example.BlogByMay.Entity.Post;
import com.example.BlogByMay.Model.CommentDto;
import com.example.BlogByMay.Model.PostDto;
import com.example.BlogByMay.Service.CommentServiceImpl;
import com.example.BlogByMay.Service.PostServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/post/v1")
@RequiredArgsConstructor
public class PostController {

    @Autowired
    private final PostServiceImpl postService;

    @Autowired
    private final CommentServiceImpl commentService;

    @PostMapping("post/{userId}")
    public ResponseEntity<String> createPost(@PathVariable Long userId, @RequestBody Post post){
        String response = postService.savePost(userId, post);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("get-posts-byId/way-1/{userId}")
    public ResponseEntity<List<PostDto>> getAllPostsWay1(@PathVariable Long userId){
        List<PostDto> posts = postService.findPosts(userId);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("get-posts-byId/way-2/{userId}")
    public ResponseEntity<List<PostDto>> getAllPostsWay2(@PathVariable Long userId){
        List<PostDto> posts = postService.findPostList(userId);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @PostMapping("post-comment/{userId}/{postId}")
    public ResponseEntity<String> postComment(@PathVariable Long userId, @PathVariable Long postId, @RequestBody Comment comment){
        String response = commentService.saveComment(postId, userId, comment);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @GetMapping("get-post-comment/{postId}")
    public ResponseEntity<List<CommentDto>> getPostComments(@PathVariable Long postId){
        List<CommentDto> allComments = commentService.findAllComments(postId);
        return new ResponseEntity<>(allComments, HttpStatus.OK);
    }

}
