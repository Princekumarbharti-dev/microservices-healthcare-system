package com.user_profile.User_ProfileService.service;

import com.user_profile.User_ProfileService.DTO.UserProfileRequestDTO;
import com.user_profile.User_ProfileService.DTO.UserProfileResponseDTO;
import com.user_profile.User_ProfileService.DTO.UserProfileUpdateDTO;
import com.user_profile.User_ProfileService.entity.Gender;
import com.user_profile.User_ProfileService.entity.UserProfile;
import com.user_profile.User_ProfileService.exception.EmailAlreadyExists;
import com.user_profile.User_ProfileService.exception.PasswordNotMatch;
import com.user_profile.User_ProfileService.exception.UsersIDNotFound;
import com.user_profile.User_ProfileService.kafka.producer.UserAuthProducer;
import com.user_profile.User_ProfileService.repository.UserProfileRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserProfileServiceTest {

    @Mock
    private UserProfileRepository userProfileRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private UserAuthProducer userAuthProducer;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserProfileService userProfileService;

    @Test
    void registerUserProfileSuccess() {

        UserProfileRequestDTO req = new UserProfileRequestDTO();
        req.setName("Admin");
        req.setEmail("admin@gmail.com");
        req.setPassword("123");
        req.setConfirmPassword("123");

        UserProfile user = new UserProfile();
        user.setId(1);
        user.setName("Admin");
        user.setEmail("admin@gmail.com");

        UserProfileResponseDTO res = new UserProfileResponseDTO();
        res.setName("Admin");
        res.setEmail("admin@gmail.com");

        when(userProfileRepository.existsByEmail("admin@gmail.com")).thenReturn(false);
        when(modelMapper.map(req, UserProfile.class)).thenReturn(user);
        when(passwordEncoder.encode("123")).thenReturn("encoded123");
        when(modelMapper.map(user, UserProfileResponseDTO.class)).thenReturn(res);

        UserProfileResponseDTO result = userProfileService.registerUserProfile(req);

        assertNotNull(result);
        assertEquals("Admin", result.getName());
        assertEquals("admin@gmail.com", result.getEmail());

        verify(userProfileRepository, times(1)).save(user);
        verify(userAuthProducer, times(1)).send(any());
    }

    @Test
    void registerUserProfilePasswordMismatch() {

        UserProfileRequestDTO req = new UserProfileRequestDTO();
        req.setPassword("123");
        req.setConfirmPassword("456");

        assertThrows(PasswordNotMatch.class, () -> userProfileService.registerUserProfile(req));

        verify(userProfileRepository, never()).save(any());
    }

    @Test
    void registerUserProfileEmailAlreadyExists() {

        UserProfileRequestDTO req = new UserProfileRequestDTO();
        req.setEmail("admin@gmail.com");
        req.setPassword("123");
        req.setConfirmPassword("123");

        when(userProfileRepository.existsByEmail("admin@gmail.com")).thenReturn(true);

        assertThrows(EmailAlreadyExists.class, () -> userProfileService.registerUserProfile(req));
    }

    @Test
    void findUserProfileByIDSuccess() {

        UserProfile user = new UserProfile();
        user.setId(1);
        user.setName("Admin");
        user.setEmail("admin@gmail.com");

        UserProfileResponseDTO res = new UserProfileResponseDTO();
        res.setName("Admin");
        res.setEmail("admin@gmail.com");

        when(userProfileRepository.findById(1)).thenReturn(Optional.of(user));
        when(modelMapper.map(user, UserProfileResponseDTO.class)).thenReturn(res);

        UserProfileResponseDTO result = userProfileService.findUserProfileByID(1);

        assertEquals("Admin", result.getName());
    }

    @Test
    void findUserProfileByIDNotFound() {

        when(userProfileRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(UsersIDNotFound.class, () -> userProfileService.findUserProfileByID(1));
    }

    @Test
    void deleteUserProfileSuccess() {

        UserProfile user = new UserProfile();
        user.setId(1);

        when(userProfileRepository.findById(1)).thenReturn(Optional.of(user));

        userProfileService.deleteUserProfile(1);

        verify(userProfileRepository).deleteById(1);
    }

    @Test
    void updateUsersProfileSuccess() {

        UserProfile user = new UserProfile();
        user.setId(1);
        user.setName("Admin");

        UserProfileUpdateDTO update = new UserProfileUpdateDTO();
        update.setName("Admin Updated");
        update.setAge(25);
        update.setPhone("9999999999");
        update.setGender(Gender.MALE);

        when(userProfileRepository.findById(1)).thenReturn(Optional.of(user));
        when(userProfileRepository.save(user)).thenReturn(user);
        when(modelMapper.map(user, UserProfileUpdateDTO.class)).thenReturn(update);

        UserProfileUpdateDTO result = userProfileService.updateUsersProfile(1, update);

        assertEquals("Admin Updated", result.getName());
    }
}