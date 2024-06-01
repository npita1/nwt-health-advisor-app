package com.example.accessingdatamysql.rmq;

import com.example.accessingdatamysql.entity.UserEntity;
import com.example.accessingdatamysql.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class UserEventListener {

    @Autowired
    private UserRepository userRepository;

    @RabbitListener(queues = "user-queue" )
    public void handleUserCreated(UserCreatedEvent event) {
        try {
            UserEntity user = new UserEntity();
            user.setId(event.getId());
            user.setEmail(event.getEmail());
            user.setFirstName(event.getFirstName());
            user.setLastName(event.getLastName());
            user.setPassword(event.getPassword());

            userRepository.save(user);

            // Oznaka uspjeha
            // Slanje potvrde o uspjehu npr. na RabbitMQ ili neki drugi mehanizam
        } catch (Exception e) {
            // Rukovanje greškama
            // Slanje rollback događaja npr. na RabbitMQ ili neki drugi mehanizam
            throw e;
        }
    }
}
