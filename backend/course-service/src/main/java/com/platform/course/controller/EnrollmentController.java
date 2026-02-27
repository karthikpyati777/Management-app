package com.platform.course.controller;

import com.platform.course.model.Enrollment;
import com.platform.course.service.EnrollmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/enrollments")
@CrossOrigin(origins = "*")
@Tag(name = "Enrollment Management", description = "APIs for managing course enrollments")
public class EnrollmentController {
    
    @Autowired
    private EnrollmentService enrollmentService;
    
    @GetMapping
    @Operation(summary = "Get all enrollments", description = "Admin: Retrieves all enrollments with user and course details")
    public ResponseEntity<List<Enrollment>> getAllEnrollments() {
        return ResponseEntity.ok(enrollmentService.getAllEnrollments());
    }
    
    @PostMapping
    @Operation(summary = "Enroll user in course", description = "Enrolls a user in a specific course with validation")
    public ResponseEntity<Enrollment> enrollUser(@RequestBody Map<String, Long> request) {
        Long userId = request.get("userId");
        Long courseId = request.get("courseId");
        return ResponseEntity.status(201).body(enrollmentService.enrollUser(userId, courseId));
    }
    
    @GetMapping("/user/{userId}")
    @Operation(summary = "Get user enrollments", description = "Retrieves all enrollments for a specific user")
    public ResponseEntity<List<Enrollment>> getUserEnrollments(@PathVariable Long userId) {
        return ResponseEntity.ok(enrollmentService.getUserEnrollments(userId));
    }
    
    @GetMapping("/course/{courseId}")
    @Operation(summary = "Get course enrollments", description = "Retrieves all enrollments for a specific course")
    public ResponseEntity<List<Enrollment>> getCourseEnrollments(@PathVariable Long courseId) {
        return ResponseEntity.ok(enrollmentService.getCourseEnrollments(courseId));
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Unenroll user", description = "Removes a user's enrollment from a course")
    public ResponseEntity<Void> unenrollUser(@PathVariable Long id) {
        enrollmentService.unenrollUser(id);
        return ResponseEntity.noContent().build();
    }
}
