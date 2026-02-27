package com.platform.course.service;

import com.platform.course.exception.InvalidRequestException;
import com.platform.course.exception.ResourceNotFoundException;
import com.platform.course.exception.ServiceCommunicationException;
import com.platform.course.model.Enrollment;
import com.platform.course.repository.CourseRepository;
import com.platform.course.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;
import java.util.Map;

@Service
public class EnrollmentService {
    
    @Autowired
    private EnrollmentRepository enrollmentRepository;
    
    @Autowired
    private CourseRepository courseRepository;
    
    @Autowired
    private WebClient.Builder webClientBuilder;
    
    @Value("${user.service.url}")
    private String userServiceUrl;
    
    public Enrollment enrollUser(Long userId, Long courseId) {
        if (!courseRepository.existsById(courseId)) {
            throw new ResourceNotFoundException("Course not found with id: " + courseId);
        }
        
        if (!validateUser(userId)) {
            throw new ServiceCommunicationException("User not found with id: " + userId);
        }
        
        if (enrollmentRepository.findByUserIdAndCourseId(userId, courseId).isPresent()) {
            throw new InvalidRequestException("User already enrolled in this course");
        }
        
        Enrollment enrollment = new Enrollment();
        enrollment.setUserId(userId);
        enrollment.setCourseId(courseId);
        return enrollmentRepository.save(enrollment);
    }
    
    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }
    
    public List<Enrollment> getUserEnrollments(Long userId) {
        return enrollmentRepository.findByUserId(userId);
    }
    
    public List<Enrollment> getCourseEnrollments(Long courseId) {
        if (!courseRepository.existsById(courseId)) {
            throw new ResourceNotFoundException("Course not found with id: " + courseId);
        }
        return enrollmentRepository.findByCourseId(courseId);
    }
    
    public void unenrollUser(Long enrollmentId) {
        if (!enrollmentRepository.existsById(enrollmentId)) {
            throw new ResourceNotFoundException("Enrollment not found with id: " + enrollmentId);
        }
        enrollmentRepository.deleteById(enrollmentId);
    }
    
    private Boolean validateUser(Long userId) {
        try {
            Map<String, Object> response = webClientBuilder.build()
                .get()
                .uri(userServiceUrl + "/api/users/" + userId)
                .retrieve()
                .bodyToMono(new org.springframework.core.ParameterizedTypeReference<Map<String, Object>>() {})
                .block();
            return response != null;
        } catch (Exception e) {
            throw new ServiceCommunicationException("Failed to validate user with User Service");
        }
    }
}
