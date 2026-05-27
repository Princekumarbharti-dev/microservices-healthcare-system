package com.user_profile.User_ProfileService.exception;

public class PasswordNotMatch extends RuntimeException{
    public PasswordNotMatch(String message){
        super(message);
    }
}
