package com.ahmer.manytomany.ControllerTest;

import com.ahmer.manytomany.Controller.StudentController;
import com.ahmer.manytomany.Model.ApiResponse;
import com.ahmer.manytomany.Model.Course;
import com.ahmer.manytomany.Model.Student;
import com.ahmer.manytomany.Service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class StudentControllerTest {

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;
    // @Mock is used for dependencies, and @InjectMocks is used for the class under test

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test cases go here
    @Test
    void testGetAllStudents() {
        // Arrange
        List<Student> students = List.of(
                new Student(1L, "Ahmer Mehmood"),
                new Student(2L, "Steve Smith")
        );
        when(studentService.getAllStudents()).thenReturn(students);

        // Act
        ResponseEntity<ApiResponse<List<Student>>> response = studentController.getAllStudents();

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().getData().size());
        assertEquals("Students fetched successfully", response.getBody().getMessage());

        verify(studentService, times(1)).getAllStudents();
    }

    @Test
    void testGetCoursesByStudent() {
        // Arrange
        Long studentId = 1L;
        List<Course> courses = List.of(
                new Course(1L, "Math"),
                new Course(2L, "Science"),
                new Course(3L, "English")
        );

        when(studentService.getCoursesOfStudent(studentId)).thenReturn(courses);

        // Act
        ResponseEntity<ApiResponse<List<Course>>> response = studentController.getCoursesbyStudent(studentId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(3, response.getBody().getData().size());
        assertEquals("Courses fetched for StudentId - " + studentId, response.getBody().getMessage());

        verify(studentService, times(1)).getCoursesOfStudent(studentId);

    }
}