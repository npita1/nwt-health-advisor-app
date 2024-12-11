package com.example.accessingdatamysql.service;


import com.example.accessingdatamysql.auth.ChangePasswordRequest;
import com.example.accessingdatamysql.entity.UserEntity;
import com.example.accessingdatamysql.exceptions.UserNotFoundException;
import com.example.accessingdatamysql.grpc.GrpcClient;
import com.example.accessingdatamysql.repository.TokenRepository;
import com.example.accessingdatamysql.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private GrpcClient grpcClient;
    public String getUsers() {
        grpcClient = GrpcClient.get();
        List<UserEntity> users = userRepository.findAll();
        String json = null;
        try {
            StringBuilder sb = new StringBuilder("[");
            for (UserEntity user : users) {
                sb.append(user.toString()).append(",");
            }
            if (users.size() > 0) {
                sb.deleteCharAt(sb.length() - 1);
            }
            sb.append("]");
            json = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error serializing elections to JSON: " + e.getMessage());
        }
        System.out.println("Serialized JSON: " + json);
        return json;
    }

    public Long getUserIdFromAuthentication() {
        grpcClient = GrpcClient.get();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userDetails = (UserEntity) authentication.getPrincipal();
        return userDetails.getId();
    }
    public  Map<String, String> getUserDetailsFromAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Map<String, String> userDetailsMap = new HashMap<>();
        if (authentication != null  ) {
            UserEntity userDetails = (UserEntity) authentication.getPrincipal();
            userDetailsMap.put("firstName", userDetails.getFirstName());
            userDetailsMap.put("lastName", userDetails.getLastName());
            userDetailsMap.put("email", userDetails.getEmail());
            userDetailsMap.put("password", userDetails.getPassword());
            userDetailsMap.put("role", userDetails.getRole().toString());
        }
        return userDetailsMap;
    }

    @Transactional
    public void changePassword(ChangePasswordRequest request, Principal connectedUser, String authHeader) {

        // Dohvaćanje korisnika iz sigurnosnog konteksta
        var user = (UserEntity) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        // Provjera trenutne lozinke
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }

        // Provjera podudaranja nove lozinke i potvrđene lozinke
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Passwords are not the same");
        }

        // Ažuriranje lozinke korisnika
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        // Automatski logout nakon promjene lozinke
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);
            var storedToken = tokenRepository.findByToken(jwt).orElseThrow(() -> new UserNotFoundException("Token not found"));

            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            tokenRepository.save(storedToken);
        }

        // Čišćenje sigurnosnog konteksta
        SecurityContextHolder.clearContext();
    }

}
