package com.example.BlogByMay.Controller;

import com.example.BlogByMay.Entity.Profile;
import com.example.BlogByMay.Entity.User;
import com.example.BlogByMay.Model.ProfileDto;
import com.example.BlogByMay.Model.StudentCourseDto;
import com.example.BlogByMay.Model.WholeProfileDto;
import com.example.BlogByMay.Service.Course.CourseService;
import com.example.BlogByMay.Service.UserServiceImpl;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api/user/v1")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private final UserServiceImpl userService;

    @Autowired
    private final CourseService courseService;

    @PostMapping("register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody User user) throws MessagingException {
        String response = userService.saveUser(user);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("profile/{userId}")
    public ResponseEntity<String> userProfile(@PathVariable Long userId, @Valid @RequestPart("profile") Profile profile, @RequestParam("file") MultipartFile imageFile) throws IOException {
        String response = userService.saveProfile(userId,profile, imageFile);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("get-profile-byId/{id}")
    public ResponseEntity<WholeProfileDto> getProfileByProfileId(@PathVariable(name = "id") Long profileId) throws IOException {
        WholeProfileDto profile = userService.findProfile(profileId);
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }

    @GetMapping("get-profileDto-byId/{id}")
    public ResponseEntity<ProfileDto> getProfileByProfileDetails(@PathVariable(name = "id") Long profileId){
        ProfileDto profileDetails = userService.findProfileDetails(profileId);
        return new ResponseEntity<>(profileDetails, HttpStatus.OK);
    }

    @PutMapping("update-profile/{uId}")
    public ResponseEntity<String> updateProfileDetails(@PathVariable(name = "uId") Long userId, @RequestBody ProfileDto profileDto){
        String response = userService.updateProfile(userId, profileDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("enroll-course/{uId}/{cId}")
    public ResponseEntity<String> enrollCourse(@PathVariable(name = "uId") Long userId, @PathVariable(name = "cId") Long courseId){
        String response = userService.enrollInCourse(userId, courseId );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("get-enrolledStudent/{uId}")
    public ResponseEntity<StudentCourseDto> getStudentCourse(@PathVariable(name = "uId") Long userId){
        StudentCourseDto studentCourseDetails = courseService.findStudentCourseDetails(userId);
        return new ResponseEntity<>(studentCourseDetails, HttpStatus.OK);
    }

}
