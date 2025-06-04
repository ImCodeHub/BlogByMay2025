package com.example.BlogByMay.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Table(name = "users")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column(nullable = false)
    @NotBlank(message = "first name can not be empty/null/blank")
    @Pattern(regexp = "^[\\S]+$", message = "space not allow")
    private String firstName;

    @Column(nullable = false)
    @NotBlank(message = "last name can not be empty/null/blank")
    private String lastName;

    @Column(nullable = false)
    @Email(message = "this is not Right E-mail formate")
    @Pattern(regexp = "^[A-Za-z0-9+._-]+@gmail.com$", message = "you can register with G-mail Account only.")
    private String email;

    @Column(nullable = false)
    @NotBlank(message = "Password can not be blank")
    @Size(min = 8, max = 15, message = "password must at least 8 character")
    private String password;

    @Column(nullable = false, updatable = false)
    private LocalDateTime registerDate;

    @PrePersist
    protected void onCreate(){
        this.registerDate = LocalDateTime.now();
    }

    //One user can have Many Posts
    @OneToMany(mappedBy = "user", orphanRemoval = true,cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "student_courses",
                joinColumns = @JoinColumn( name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    private Set<Course> courses = new HashSet<>(); // for avoid duplicate courses with same user.

}
