package com.example.accessingdatamysql.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.accessingdatamysql.Repository.UserRepository;
import com.example.accessingdatamysql.Entity.User;

@Service
public class UserDataService {

    @Autowired
    private UserRepository userRepository;

    public void addUser(String firstName, String lastName, String email, Integer type, String passwordHash) {
        User user = new User(firstName, lastName, email, type, passwordHash);
        userRepository.save(user);
    }
}
