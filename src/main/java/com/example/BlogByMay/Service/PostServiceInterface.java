package com.example.BlogByMay.Service;

import com.example.BlogByMay.Entity.Post;
import com.example.BlogByMay.Model.PostDto;

import java.util.List;

public interface PostServiceInterface {
    public String savePost(Long userId, Post post);
    public List<PostDto> findPosts(Long userId);
}
