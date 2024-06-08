package com.example.accessingdatamysql.rmq;

import com.example.accessingdatamysql.entity.UserEntity;
import com.example.accessingdatamysql.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class UserEventListener {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private final RabbitTemplate rabbitTemplate;
    @RabbitListener(queues = RabbitConfig.QUEUE )
    public void handleUserCreated(UserCreatedEvent event) {
        try {
            UserEntity user = new UserEntity();
            user.setId(event.getId());
            user.setEmail(event.getEmail());
            user.setFirstName(event.getFirstName());
            user.setLastName(event.getLastName());
            user.setPassword(event.getPassword());

            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            // Jedinstveno ograničenje prekršeno, pokrenite rollback
            sendRollbackEvent(event);
        }catch (Exception e) {
            // Rukovanje greškama
            // Slanje rollback događaja npr. na RabbitMQ ili neki drugi mehanizam
            throw e;
        }
    }
    private void sendRollbackEvent(UserCreatedEvent event) {
        UserCreationRollbackEvent rollbackEvent = new UserCreationRollbackEvent();
        rollbackEvent.setId(event.getId());
        rollbackEvent.setEmail(event.getEmail());
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE, RabbitConfig.ROLLBACK_ROUTING_KEY, rollbackEvent);
    }

}
