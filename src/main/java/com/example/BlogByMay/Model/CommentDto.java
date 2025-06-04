package com.example.BlogByMay.Model;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class CommentDto {
    private Long commentId;
    private String fullName;
    private String comment;
}
