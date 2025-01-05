package com.ahmer.manytomany.Controller;

import com.ahmer.manytomany.Model.ApiResponse;
import com.ahmer.manytomany.Model.Course;
import com.ahmer.manytomany.Model.Student;
import com.ahmer.manytomany.Service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // Get all students
    @GetMapping
    public ResponseEntity<ApiResponse<List<Student>>> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        return ResponseEntity.ok(new ApiResponse<>(true, "Students fetched successfully", students));
    }

    @GetMapping("/courses/{studentId}")
    public ResponseEntity<ApiResponse<List<Course>>> getCoursesbyStudent(@PathVariable Long studentId) {
        try {
            List<Course> courses = studentService.getCoursesOfStudent(studentId);
            return ResponseEntity.ok
                    (new ApiResponse<>(true, "Courses fetched for StudentId - " + studentId, courses));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    // Create a new student with existing courses
    @PostMapping
    public ResponseEntity<ApiResponse<Student>> saveStudent(@RequestBody Student student) {
        try {
            Student savedStudent = studentService.saveStudentWithExistingCourses(student);
            return ResponseEntity.ok(new ApiResponse<>(true, "Student created successfully", savedStudent));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }
}