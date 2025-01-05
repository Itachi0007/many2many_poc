package com.ahmer.manytomany.Controller;

import com.ahmer.manytomany.Model.ApiResponse;
import com.ahmer.manytomany.Model.Course;
import com.ahmer.manytomany.Model.Student;
import com.ahmer.manytomany.Service.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    // Get all courses
    @GetMapping
    public ResponseEntity<ApiResponse<List<Course>>> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        return ResponseEntity.ok(new ApiResponse<>(true, "Courses fetched successfully", courses));
    }

    // Get a course by ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Course>> getCourseById(@PathVariable Long id) {
        return courseService.getCourseById(id)
                .map(course -> ResponseEntity.ok(new ApiResponse<>(true, "Course fetched successfully", course)))
                .orElse(ResponseEntity.status(404)
                        .body(new ApiResponse<>(false, "Course with ID " + id + " not found", null)));
    }

    // Create a new course
    @PostMapping
    public ResponseEntity<ApiResponse<Course>> saveCourse(@RequestBody Course course) {
        try {
            Course savedCourse = courseService.saveCourse(course);
            return ResponseEntity.ok(new ApiResponse<>(true, "Course created successfully", savedCourse));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    // Update an existing course
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Course>> updateCourse(@PathVariable Long id, @RequestBody Course updatedCourse) {
        try {
            Course updated = courseService.updateCourse(id, updatedCourse);
            return ResponseEntity.ok(new ApiResponse<>(true, "Course updated successfully", updated));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    // Delete a course by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCourseById(@PathVariable Long id) {
        try {
            courseService.deleteCourseById(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Course deleted successfully", null));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    // Get all students of a course
    @GetMapping("/{id}/students")
    public ResponseEntity<ApiResponse<List<Student>>> getAllStudentsByCourse(@PathVariable Long id) {
        try {
            List<Student> students = courseService.getAllStudentsByCourse(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Students fetched successfully", students));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }
}