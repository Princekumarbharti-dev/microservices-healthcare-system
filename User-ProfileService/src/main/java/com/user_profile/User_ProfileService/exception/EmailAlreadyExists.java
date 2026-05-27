package com.user_profile.User_ProfileService.exception;

public class EmailAlreadyExists extends RuntimeException{
    public EmailAlreadyExists(String message){
        super(message);
    }
}
