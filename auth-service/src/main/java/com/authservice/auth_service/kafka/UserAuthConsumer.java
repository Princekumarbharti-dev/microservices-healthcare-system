package com.authservice.auth_service.kafka;

import com.authservice.auth_service.entity.AuthUser;
import com.authservice.auth_service.kafka.event.UserAuthCreateEvent;
import com.authservice.auth_service.repository.AuthUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
public class UserAuthConsumer {

    private final ObjectMapper objectMapper=new ObjectMapper();

    @Autowired
    private AuthUsersRepository authUsersRepository;

    @KafkaListener(topics = "auth.user.create", groupId = "auth-service-group-4")
    public void consume(String message) {
        System.out.println("Received Kafka Event: " + message);


        UserAuthCreateEvent userAuthCreateEvent=objectMapper.readValue(message, UserAuthCreateEvent.class);
        AuthUser authUser=authUsersRepository.findByEmail(userAuthCreateEvent.getEmail()).orElse(null);

        if(authUser==null){
            authUsersRepository.save(new AuthUser(userAuthCreateEvent.getUserId(), userAuthCreateEvent.getEmail(), userAuthCreateEvent.getPassword(),userAuthCreateEvent.getRole()));

        }else{

            authUser.setUserId(userAuthCreateEvent.getUserId());
            authUser.setEmail(userAuthCreateEvent.getEmail());
            authUser.setPassword(userAuthCreateEvent.getPassword());
            authUser.setRole(userAuthCreateEvent.getRole());
            authUsersRepository.save(authUser);
        }

        System.out.println("Saved to DB: " + authUser.getEmail());

    }
}

