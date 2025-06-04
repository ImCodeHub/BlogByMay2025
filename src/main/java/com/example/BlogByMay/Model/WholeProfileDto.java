package com.example.BlogByMay.Model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class WholeProfileDto {
    private Long profile_id;
    private String image;
    private String fullName;
    private String mobile;
    private String email;
    private int age;
    private String city;
    private String state;
    private String address;
    private String pincode;
    private LocalDateTime profileCreated;
    private LocalDateTime profileUpdatedLast;
    private LocalDateTime registerDate;
}
