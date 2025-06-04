package com.example.BlogByMay.Service;

import com.example.BlogByMay.Entity.Post;
import com.example.BlogByMay.Entity.User;
import com.example.BlogByMay.Model.PostDto;
import com.example.BlogByMay.Repository.PostRepository;
import com.example.BlogByMay.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostServiceInterface {

    @Autowired
    private final PostRepository postRepository;

    @Autowired
    private final UserRepository userRepository;


    @Override
    public String savePost(Long userId, Post post) {
        User foundUser = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        post.setUser(foundUser);
        postRepository.save(post);
        return "your post has been saved";
    }

    //this is 1 way to get the all the posts from DB for a particular user.
    @Override
    public List<PostDto> findPosts(Long userId) {
        List<PostDto> listDto = new ArrayList<>();
        User foundUser = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user not found"));
        List<Post> posts = foundUser.getPosts();

        for (Post post : posts) {
            PostDto postDto = PostDto.builder()
                    .fullName(post.getUser().getFirstName() + " " + post.getUser().getLastName())
                    .title(post.getTitle())
                    .description(post.getDescription())
                    .image(post.getImage())
                    .postId(post.getPostId())
                    .build();

            listDto.add(postDto);
        }
        return listDto;
    }
    // 2nd way
    public List<PostDto> findPostList(Long userId){
        User foundUser = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user not found"));
        List<PostDto> listDto = new ArrayList<>();
        List<Post> posts = postRepository.findByUserUserId(foundUser.getUserId());
        for (Post post : posts) {
            PostDto postDto = PostDto.builder()
                    .fullName(post.getUser().getFirstName() + " " + post.getUser().getLastName())
                    .title(post.getTitle())
                    .description(post.getDescription())
                    .image(post.getImage())
                    .postId(post.getPostId())
                    .build();

            listDto.add(postDto);
        }
        return listDto;
    }
}
