package com.platform.user.service;

import com.platform.user.dto.CourseDTO;
import com.platform.user.dto.LoginRequest;
import com.platform.user.dto.LoginResponse;
import com.platform.user.exception.DuplicateResourceException;
import com.platform.user.exception.ResourceNotFoundException;
import com.platform.user.exception.ServiceCommunicationException;
import com.platform.user.model.User;
import com.platform.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;

@Service
public class UserService {
    
    private final UserRepository userRepository;
    private final WebClient.Builder webClientBuilder;
    
    public UserService(UserRepository userRepository, WebClient.Builder webClientBuilder) {
        this.userRepository = userRepository;
        this.webClientBuilder = webClientBuilder;
    }
    
    @Value("${course.service.url:http://course-service:8081}")
    private String courseServiceUrl;
    
    public User createUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new DuplicateResourceException("Username already exists");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }
        return userRepository.save(user);
    }
    
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new ResourceNotFoundException("Invalid email or password"));
        
        if (!user.getPassword().equals(request.getPassword())) {
            throw new ResourceNotFoundException("Invalid email or password");
        }
        
        String token = "token_" + user.getId() + "_" + System.currentTimeMillis();
        return new LoginResponse(token, user.getId(), user.getUsername(), user.getEmail(), user.getRole());
    }
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    public User getUserById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }
    
    public User updateUser(Long id, User userDetails) {
        User user = getUserById(id);
        user.setUsername(userDetails.getUsername());
        user.setEmail(userDetails.getEmail());
        user.setRole(userDetails.getRole());
        return userRepository.save(user);
    }
    
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
    
    public List<CourseDTO> getUserCourses(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }
        try {
            return webClientBuilder.build()
                .get()
                .uri(courseServiceUrl + "/api/courses/instructor/" + userId)
                .retrieve()
                .bodyToFlux(CourseDTO.class)
                .collectList()
                .block();
        } catch (Exception e) {
            throw new ServiceCommunicationException("Failed to fetch courses from course service");
        }
    }
}
