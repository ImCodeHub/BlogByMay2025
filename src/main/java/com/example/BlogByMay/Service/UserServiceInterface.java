package com.example.BlogByMay.Service;

import com.example.BlogByMay.Entity.Profile;
import com.example.BlogByMay.Entity.User;
import com.example.BlogByMay.Model.ProfileDto;
import com.example.BlogByMay.Model.WholeProfileDto;
import jakarta.mail.MessagingException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserServiceInterface {
    public String saveUser(User user) throws MessagingException;
    public String saveProfile(Long userId,Profile profile, MultipartFile imageFile) throws IOException;
    public WholeProfileDto findProfile(Long profileId) throws IOException;
    public ProfileDto findProfileDetails(Long profileId);
    public String updateProfile(Long userId,  ProfileDto profileDto);
    public String enrollInCourse(Long userId, Long courseId);
}
