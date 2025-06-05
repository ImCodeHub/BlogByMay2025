package com.example.BlogByMay.Service;

import com.example.BlogByMay.Entity.Profile;
import com.example.BlogByMay.Entity.User;
import com.example.BlogByMay.Model.ProfileDto;
import com.example.BlogByMay.Repository.ProfileRepository;
import com.example.BlogByMay.Repository.UserRepository;
import com.example.BlogByMay.Service.Utility.EmailService;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Executable;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // it is good practice to use otherwise test unit will not work.
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private EmailService emailService;

    @Mock
    private ProfileRepository profileRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    private Profile profile;

    private Long profileId = 1L;

    @BeforeEach
    public void setUp() {
//        MockitoAnnotations.openMocks(this);  //either you can use this in setUp() or you can use this extension on class level.
        user = User.builder()
                .userId(1L)
                .firstName("Aaryan")
                .lastName("Prashar")
                .email("aaryan@gmail.com")
                .password("1112223334455")
                .build();

        profile= Profile.builder()
                .profile_id(profileId)
                .age(20)
                .city("mumbai")
                .state("MH")
                .pincode("123456")
                .address("anywhere in mumbai")
                .user(user)
                .mobile("1122334455")
                .imageName("image.jpeg")
                .build();
    }

    @AfterEach
    public void tearDown() {
        user = null;
    }

    @Test
    public void TestSaveUser() throws MessagingException {
        //mock userRepository and pretend that "when" you save "any" User.class then return user object;
        when(userRepository.save(any(User.class))).thenReturn(user);

        //mock emailService.registerEmail(anyString()) when call with any String, return dummy email body.
        when(emailService.registerEmail(anyString())).thenReturn("dummy email text body");

        //mock emailService.sendStanderEmail: when call with any String do nothing;
        doNothing().when(emailService).sendStanderEmail(anyString(), anyString(), anyString());

        //call the service and get the exact response(result) from the service layer.
        String response = userService.saveUser(user);

        // verify the response
        //match the result(response) is matching with expected Result or not.
        assertThat(response).isEqualTo("User has been saved now!");

        //verify that userRepository.save(): was called exactly once with user object.
        verify(userRepository).save(user);
    }

    @Test
    public void TestFindProfileDetails_successful(){
        //mock
        when(profileRepository.findById(profileId)).thenReturn(Optional.of(profile));

        ProfileDto profileDetails = userService.findProfileDetails(profileId);

        assertThat(profileDetails).isNotNull();

        assertThat(profileDetails.getFullName()).isEqualTo("Aaryan Prashar");
        assertThat(profileDetails.getCity()).isEqualTo("mumbai");
        assertThat(profileDetails.getState()).isEqualTo("MH");
        assertThat(profileDetails.getPincode()).isEqualTo("123456");
        assertThat(profileDetails.getEmail()).isEqualTo("aaryan@gmail.com");

    }

    @Test
    public void TestFindProfileDetails_ProfileNotFound(){
        //mock
        when(profileRepository.findById(profileId)).thenReturn(Optional.empty());

        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
            userService.findProfileDetails(profileId);
        });

        //verfication
        assertThat(runtimeException.getMessage()).isEqualTo("Profile not found");
    }


}
