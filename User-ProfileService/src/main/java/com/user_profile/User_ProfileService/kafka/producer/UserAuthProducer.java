package com.user_profile.User_ProfileService.kafka.producer;

import com.user_profile.User_ProfileService.kafka.event.UserAuthCreateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserAuthProducer {

    private final KafkaTemplate<String, UserAuthCreateEvent> kt;

    @Autowired
    public UserAuthProducer(KafkaTemplate<String, UserAuthCreateEvent> kt) {
        this.kt = kt;
    }

    public void send(UserAuthCreateEvent e) {
        kt.send("auth.user.create", e.getEmail(), e);
    }
}