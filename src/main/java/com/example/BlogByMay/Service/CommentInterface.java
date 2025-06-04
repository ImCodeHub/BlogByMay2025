package com.example.BlogByMay.Service;

import com.example.BlogByMay.Entity.Comment;
import com.example.BlogByMay.Model.CommentDto;

import java.util.List;

public interface CommentInterface {
    public String saveComment(Long postId, Long userId, Comment comment);
    public List<CommentDto> findAllComments(Long postId);
}
