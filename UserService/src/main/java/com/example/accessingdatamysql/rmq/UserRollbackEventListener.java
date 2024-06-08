package com.example.accessingdatamysql.rmq;

import com.example.accessingdatamysql.repository.TokenRepository;
import com.example.accessingdatamysql.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserRollbackEventListener {
    private final RabbitTemplate rabbitTemplate;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    public UserRollbackEventListener(UserRepository userRepository, TokenRepository tokenRepository, RabbitTemplate rabbitTemplate) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.rabbitTemplate=rabbitTemplate;
    }
    @Transactional
    @RabbitListener(queues = RabbitConfig.ROLLBACK_QUEUE)
    public void handleUserRollback(UserCreationRollbackEvent event) {
        Long userId = event.getId();
        Optional<Long> userIdOptional = Optional.ofNullable(userId);
        userIdOptional.ifPresent(id -> {
            tokenRepository.deleteByUserId(id);
            userRepository.deleteById(id);

        });


    }

}
