package com.example.BlogByMay.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users_profiles")
@Entity
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long profile_id;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    @NotBlank(message = "your address must not be blank")
    private String address;

    @Column(nullable = false)
    @Size(max = 6)
    private String pincode;

    @Column(nullable = false)
    @Min(value = 18, message = "Age must be at least 18 or above")
    private int age;

    @Column(nullable = false, length = 10)
    @Size(min = 10,max =12 , message = "your mobile number should 10 or 12 digit (91)" )
    private String mobile;

    private String imageName;

    @Column(nullable = false, updatable = false)
    private LocalDateTime profileCreated;

    @Column(nullable = false)
    private LocalDateTime profileUpdatedLast;

    @PrePersist
    protected void onCreate(){
        this.profileCreated = LocalDateTime.now();
        this.profileUpdatedLast = LocalDateTime.now();
    }

    // pre update
    @PreUpdate
    protected void onUpdate(){
        this.profileUpdatedLast = LocalDateTime.now();
    }

    //One user can have One Profile
    @OneToOne
    @JoinColumn(name = "user_id",unique = true)
//    @JsonIgnore
    private User user;

}
