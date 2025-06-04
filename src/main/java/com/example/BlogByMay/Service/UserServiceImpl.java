package com.example.BlogByMay.Service;

import com.example.BlogByMay.Entity.Course;
import com.example.BlogByMay.Entity.Profile;
import com.example.BlogByMay.Entity.User;
import com.example.BlogByMay.Model.ProfileDto;
import com.example.BlogByMay.Model.WholeProfileDto;
import com.example.BlogByMay.Repository.CourseRepository;
import com.example.BlogByMay.Repository.ProfileRepository;
import com.example.BlogByMay.Repository.UserRepository;
import com.example.BlogByMay.Service.Utility.EmailService;
import com.example.BlogByMay.Service.Utility.ImageService;
import jakarta.mail.MessagingException;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserServiceInterface {
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final ProfileRepository profileRepository;

    @Autowired
    private final CourseRepository courseRepository;

    @Autowired
    private final ImageService imageService;

    @Autowired
    private final EmailService emailService;

    @Override
    public String saveUser(User user) throws MessagingException {
        String to = user.getEmail();
        String subject = "Greeting from Blog plate form";
        String textBody = emailService.registerEmail(user.getFirstName());
        userRepository.save(user);
        emailService.sendStanderEmail(to,subject,textBody);
        return "User has been saved now!";
    }

    @Override
    public String saveProfile(Long userId, Profile profile, MultipartFile imageFile) throws IOException {
        User foundUser = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user not found"));// later update

        // save the image file in to directory. And return the unique file name.
        String fileName = imageService.saveImage(imageFile);

        profile.setImageName(fileName);
        profile.setUser(foundUser);
        profileRepository.save(profile);
        return "your profile has been register";
    }

    @Override
    public WholeProfileDto findProfile(Long profileId) throws IOException {
        printValue();
        Profile profile = profileRepository.findById(profileId).orElseThrow(() -> new RuntimeException("Profile not found"));
        WholeProfileDto wholeProfileDto = WholeProfileDto.builder()
                .image(imageService.getEncodedImage(profile.getImageName()))
                .profile_id(profile.getProfile_id())
                .fullName(profile.getUser().getFirstName()+" "+profile.getUser().getLastName())
                .mobile(profile.getMobile())
                .email(profile.getUser().getEmail())
                .address(profile.getAddress())
                .state(profile.getState())
                .city(profile.getCity())
                .pincode(profile.getPincode())
                .age(profile.getAge())
                .registerDate(profile.getUser().getRegisterDate())
                .profileCreated(profile.getProfileCreated())
                .profileUpdatedLast(profile.getProfileUpdatedLast())
                .build();
        return wholeProfileDto;
    }

    @Override
    public ProfileDto findProfileDetails(Long profileId) {
        Profile profile = profileRepository.findById(profileId).orElseThrow(() -> new RuntimeException("Profile not found"));

//        ProfileDto profileDto = new ProfileDto();
//        profileDto.setFullName(profile.getUser().getFirstName()+" "+profile.getUser().getLastName());
//        profileDto.setEmail(profile.getUser().getEmail());
//        profileDto.setCity(profile.getCity());
//        profileDto.setState(profile.getState());
//        profileDto.setPincode(profile.getPincode());

        ProfileDto profileDto = ProfileDto.builder()
                .fullName(profile.getUser().getFirstName() + " " + profile.getUser().getLastName())
                .email(profile.getUser().getEmail())
                .city(profile.getCity())
                .state(profile.getState())
                .pincode(profile.getPincode())
                .build();

        return profileDto;
    }

    @Override
    public String updateProfile(Long userId, ProfileDto profileDto) {
        Profile profile = profileRepository.findByUserUserId(userId).orElseThrow(() -> new RuntimeException("Profile not found"));
        String[] str = profileDto.getFullName().split(" "); //str["first_name","last_name"];

        profile.getUser().setFirstName(str[0]);
        profile.getUser().setLastName(str[1]);
        profile.getUser().setEmail(profileDto.getEmail());
        profile.setCity(profileDto.getCity());
        profile.setState(profileDto.getState());
        profile.setPincode(profileDto.getPincode());


        profileRepository.save(profile);

        return "your profile has been updated!";
    }

    @Override
    public String enrollInCourse(Long userId, Long courseId) {
        User foundUser = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user not found"));
        Course foundCourse = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("course not found"));
        foundUser.getCourses().add(foundCourse);
        userRepository.save(foundUser);
        return "You have successfully enrolled in "+foundCourse.getCourseName()+" Course";
    }

    @Value("${app.name}")
    private String name;

    @Value("${app.version}")
    private String version;

    public void printValue(){
        System.out.println("Name and value: "+name+" "+version);
    }

}
