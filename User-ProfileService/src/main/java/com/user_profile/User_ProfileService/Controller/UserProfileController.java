package com.user_profile.User_ProfileService.Controller;

import com.user_profile.User_ProfileService.DTO.UserProfileRequestDTO;
import com.user_profile.User_ProfileService.DTO.UserProfileResponseDTO;
import com.user_profile.User_ProfileService.DTO.UserProfileUpdateDTO;
import com.user_profile.User_ProfileService.service.UserProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userprofile")
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;
    @PostMapping("/register")
    public ResponseEntity<UserProfileResponseDTO> registerUserProfile(@Valid @RequestBody UserProfileRequestDTO userProfileRequestDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(userProfileService.registerUserProfile(userProfileRequestDTO));
    }

    @GetMapping("/ViewProfile/id/{id}")
    public ResponseEntity<UserProfileResponseDTO> findUserProfileById(@PathVariable Integer id){
        return ResponseEntity.status(HttpStatus.OK).body(userProfileService.findUserProfileByID(id));
    }

    @GetMapping("/ViewProfile/email/{email}")
    public ResponseEntity<UserProfileResponseDTO> findUserProfileByEmail(@PathVariable String email){
        return ResponseEntity.status(HttpStatus.OK).body(userProfileService.findUserProfileByEmail(email));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserProfile(@PathVariable Integer id) {
        userProfileService.deleteUserProfile(id);
        return ResponseEntity.ok("User deleted successfully");
    }

    @PutMapping("/UpdateProfile/{id}")
    public ResponseEntity<UserProfileUpdateDTO> updateUserProfile(@PathVariable Integer id,@Valid @RequestBody UserProfileUpdateDTO userProfileUpdateDTO){
        return ResponseEntity.status(HttpStatus.OK).body(userProfileService.updateUsersProfile(id,userProfileUpdateDTO));
    }

}
