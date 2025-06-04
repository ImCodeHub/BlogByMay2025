package com.example.BlogByMay.Service;

import com.example.BlogByMay.Entity.Comment;
import com.example.BlogByMay.Entity.Post;
import com.example.BlogByMay.Entity.User;
import com.example.BlogByMay.Model.CommentDto;
import com.example.BlogByMay.Repository.CommnetRepository;
import com.example.BlogByMay.Repository.PostRepository;
import com.example.BlogByMay.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentInterface{

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final PostRepository postRepository;

    @Autowired
    private final CommnetRepository commnetRepository;

    @Override
    public String saveComment(Long postId, Long userId, Comment comment) {
        User foundUser = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Post existPost = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        comment.setUser(foundUser);
        comment.setPost(existPost);

        commnetRepository.save(comment);
        return "your comment has been post";
    }

    @Override
    public List<CommentDto> findAllComments(Long postId) {
        Post existPost = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));

        List<CommentDto> commentDtosList = new ArrayList<>();

        List<Comment> comments = existPost.getComments();

        for(Comment comment : comments){
            CommentDto commentDto = CommentDto.builder()
                    .commentId(comment.getCommentId())
                    .comment(comment.getComment())
                    .fullName(comment.getUser().getFirstName()+" "+comment.getUser().getLastName())
                    .build();
            commentDtosList.add(commentDto);
        }

        return commentDtosList;
    }
}
