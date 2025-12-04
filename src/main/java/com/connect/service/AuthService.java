package com.connect.service;

import com.connect.dto.AuthRequest;
import com.connect.dto.AuthResponse;
import com.connect.dto.SignupRequest;
import com.connect.dto.UserDTO;
import com.connect.model.User;
import com.connect.repository.UserRepository;
import com.connect.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }
    
    public AuthResponse signup(SignupRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }
        
        User user = new User(
            request.getEmail(),
            passwordEncoder.encode(request.getPassword()),
            request.getFirstName(),
            request.getLastName()
        );
        
        user = userRepository.save(user);
        String token = jwtService.generateToken(user.getEmail());
        
        return new AuthResponse(token, new UserDTO(user));
    }
    
    public AuthResponse login(AuthRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new RuntimeException("Invalid email or password"));
        
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }
        
        String token = jwtService.generateToken(user.getEmail());
        return new AuthResponse(token, new UserDTO(user));
    }
    
    public UserDTO getCurrentUser(User user) {
        return new UserDTO(user);
    }
}
