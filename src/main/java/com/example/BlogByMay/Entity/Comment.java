package com.example.BlogByMay.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comments")
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    private String comment;
    private LocalDateTime createTime;
    private LocalDateTime editTime;

    @PrePersist
    protected void onCreate(){
        this.createTime = LocalDateTime.now();
        this.editTime = LocalDateTime.now();
    }

    @PreUpdate
    protected void onEdit(){
        this.editTime = LocalDateTime.now();
    }

    //Many comments can have One post
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    //Many Comments can post by One user
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

}
