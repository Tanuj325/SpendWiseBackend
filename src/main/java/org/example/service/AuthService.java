package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.LoginRequest;
import org.example.dto.SignupRequest;
import org.example.entity.User;
import org.example.repository.UserRepository;
import org.example.util.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder encoder;

    private final JwtUtil jwtUtil;

    // =========================
    // SIGNUP
    // =========================

    public Map<String, Object> signup(SignupRequest request) {

        // EMAIL ALREADY EXISTS
        if (userRepository.findByEmail(request.getEmail()) != null) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();

        user.setName(request.getName());

        user.setEmail(request.getEmail());

        // PASSWORD HASH
        user.setPassword(
                encoder.encode(request.getPassword())
        );

        // EMPTY EXPENSE LIST
        user.setExpenseIds(new ArrayList<>());

        // SAVE USER
        User savedUser = userRepository.save(user);

        // GENERATE JWT
        String token =
                jwtUtil.generateToken(savedUser.getEmail());

        Map<String, Object> response = new HashMap<>();

        response.put("token", token);

        response.put("user", savedUser);

        return response;
    }

    // =========================
    // LOGIN
    // =========================

    public Map<String, Object> login(LoginRequest request) {

        User user =
                userRepository.findByEmail(request.getEmail());

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // PASSWORD MATCH
        boolean match =
                encoder.matches(
                        request.getPassword(),
                        user.getPassword()
                );

        if (!match) {
            throw new RuntimeException("Invalid password");
        }

        // GENERATE JWT
        String token =
                jwtUtil.generateToken(user.getEmail());

        Map<String, Object> response = new HashMap<>();

        response.put("token", token);

        response.put("user", user);

        return response;
    }
}