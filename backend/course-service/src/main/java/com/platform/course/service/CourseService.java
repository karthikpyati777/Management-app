package com.platform.course.service;

import com.platform.course.exception.InvalidRequestException;
import com.platform.course.exception.ResourceNotFoundException;
import com.platform.course.exception.ServiceCommunicationException;
import com.platform.course.model.Course;
import com.platform.course.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;
import java.util.Map;

@Service
public class CourseService {
    
    @Autowired
    private CourseRepository courseRepository;
    
    @Autowired
    private WebClient.Builder webClientBuilder;
    
    @Value("${user.service.url}")
    private String userServiceUrl;
    
    public Course createCourse(Course course) {
        if (course.getInstructorId() == null) {
            throw new InvalidRequestException("Instructor ID is required");
        }
        if (!validateUser(course.getInstructorId())) {
            throw new ServiceCommunicationException("Instructor not found with id: " + course.getInstructorId());
        }
        return courseRepository.save(course);
    }
    
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }
    
    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));
    }
    
    public Course updateCourse(Long id, Course courseDetails) {
        Course course = getCourseById(id);
        course.setTitle(courseDetails.getTitle());
        course.setDescription(courseDetails.getDescription());
        course.setCategory(courseDetails.getCategory());
        course.setPrice(courseDetails.getPrice());
        course.setDuration(courseDetails.getDuration());
        return courseRepository.save(course);
    }
    
    public void deleteCourse(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Course not found with id: " + id);
        }
        courseRepository.deleteById(id);
    }
    
    public List<Course> getCoursesByInstructor(Long instructorId) {
        return courseRepository.findByInstructorId(instructorId);
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
