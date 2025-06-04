package com.example.BlogByMay.Model;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostDto {
    private Long postId;

    private String fullName;

    private String title;

    private String description;

    private String image;
}
