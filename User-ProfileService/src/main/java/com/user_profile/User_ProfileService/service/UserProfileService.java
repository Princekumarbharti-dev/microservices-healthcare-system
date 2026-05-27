package com.user_profile.User_ProfileService.service;

import com.user_profile.User_ProfileService.DTO.UserProfileRequestDTO;
import com.user_profile.User_ProfileService.DTO.UserProfileResponseDTO;
import com.user_profile.User_ProfileService.DTO.UserProfileUpdateDTO;
import com.user_profile.User_ProfileService.entity.UserProfile;
import com.user_profile.User_ProfileService.exception.EmailAlreadyExists;
import com.user_profile.User_ProfileService.exception.EmailNotFound;
import com.user_profile.User_ProfileService.exception.PasswordNotMatch;
import com.user_profile.User_ProfileService.exception.UsersIDNotFound;
import com.user_profile.User_ProfileService.kafka.event.UserAuthCreateEvent;
import com.user_profile.User_ProfileService.kafka.producer.UserAuthProducer;
import com.user_profile.User_ProfileService.repository.UserProfileRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@Service
public class UserProfileService {
    @Autowired
    private  UserProfileRepository userProfileRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserAuthProducer userAuthProducer;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserProfileResponseDTO registerUserProfile(UserProfileRequestDTO userProfileRequestDTO) {
        //save User Profile and Pass Username And Password to Auth
        if(!userProfileRequestDTO.getPassword().equals(userProfileRequestDTO.getConfirmPassword())){
            throw new PasswordNotMatch("Passwords do not match Enter Same Password");
        }

        boolean isEmailPresent=userProfileRepository.existsByEmail(userProfileRequestDTO.getEmail());

        if(isEmailPresent) throw new EmailAlreadyExists("Email: "+userProfileRequestDTO.getEmail() +" Already Exists Try To Login!!");

        ////save User Profile and Pass Username And Password to Auth using kafka
        //AuthRegister--.gerEmail() .getPassword()
        //Publish a message

        UserProfile userProfile=modelMapper.map(userProfileRequestDTO, UserProfile.class);
        userProfileRepository.save(userProfile);

        UserAuthCreateEvent e = new UserAuthCreateEvent(
                userProfile.getId(),
                userProfile.getEmail(),
                passwordEncoder.encode(userProfileRequestDTO.getPassword()),
                "REGISTERED_USER"
        );

        userAuthProducer.send(e);

        return modelMapper.map(userProfile, UserProfileResponseDTO.class);
    }

    public UserProfileResponseDTO findUserProfileByID(Integer id) {
        UserProfile userProfile= userProfileRepository.findById(id).orElseThrow(()->new UsersIDNotFound("NO Users Found With ID: "+id));

        return modelMapper.map(userProfile, UserProfileResponseDTO.class);

    }

    public void deleteUserProfile(Integer id) {
        UserProfile userProfile= userProfileRepository.findById(id).orElseThrow(()->new UsersIDNotFound("NO Users Found With ID: "+id));
        userProfileRepository.deleteById(id);
    }


    public UserProfileResponseDTO findUserProfileByEmail(String email) {

        UserProfile userProfile=userProfileRepository.findByEmail(email).orElseThrow(()->new EmailNotFound("No Users Found With Email: "+email));

        return modelMapper.map(userProfile, UserProfileResponseDTO.class);

    }

    @Transactional
    public UserProfileUpdateDTO updateUsersProfile(Integer id,UserProfileUpdateDTO userProfileUpdateDTO) {
        UserProfile userProfile= userProfileRepository.findById(id).orElseThrow(()->new UsersIDNotFound("NO Users Found With ID: "+id));

        userProfile.setAge(userProfileUpdateDTO.getAge());
        userProfile.setName(userProfileUpdateDTO.getName());
        userProfile.setPhone(userProfileUpdateDTO.getPhone());
        userProfile.setGender(userProfileUpdateDTO.getGender());

        UserProfile saved = userProfileRepository.save(userProfile);
        return modelMapper.map(saved,UserProfileUpdateDTO.class);
    }
}
