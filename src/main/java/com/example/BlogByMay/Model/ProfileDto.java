package com.example.BlogByMay.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**DTO - Data Transfer Object : which helps to transfer the object between 2 layers,<p> it reduces the number of calls*/
@Data
@Builder
public class ProfileDto {
    private Long profileId; //profile
    private String fullName; // User
    private String email; // User
    private String city; // Profile
    private String state; //Profile
    private String pincode; //Profile
}
