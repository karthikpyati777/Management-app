package com.platform.course.controller;

import com.platform.course.model.Course;
import com.platform.course.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
@CrossOrigin(origins = "*")
@Tag(name = "Course Management", description = "APIs for managing courses")
public class CourseController {
    
    @Autowired
    private CourseService courseService;
    
    @PostMapping
    @Operation(summary = "Create a new course", description = "Creates a new course with instructor validation")
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        Course created = courseService.createCourse(course);
        return ResponseEntity.status(201).body(created);
    }
    
    @GetMapping
    @Operation(summary = "Get all courses", description = "Retrieves a list of all available courses")
    public ResponseEntity<List<Course>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get course by ID", description = "Retrieves a specific course by its ID")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update course", description = "Updates an existing course's information")
    public ResponseEntity<Course> updateCourse(@PathVariable Long id, @RequestBody Course course) {
        return ResponseEntity.ok(courseService.updateCourse(id, course));
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete course", description = "Deletes a course from the system")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/instructor/{instructorId}")
    @Operation(summary = "Get courses by instructor", description = "Retrieves all courses taught by a specific instructor")
    public ResponseEntity<List<Course>> getCoursesByInstructor(@PathVariable Long instructorId) {
        return ResponseEntity.ok(courseService.getCoursesByInstructor(instructorId));
    }
}
