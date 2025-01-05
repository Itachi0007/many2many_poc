package com.ahmer.manytomany.ServiceTest;

import com.ahmer.manytomany.Model.Course;
import com.ahmer.manytomany.Model.Student;
import com.ahmer.manytomany.Repository.StudentRepository;
import com.ahmer.manytomany.Service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test cases go here
    @Test
    void testGetCoursesOfStudent_ValidStudent() {
        // Arrange
        Long studentId = 1L;
        Student student = new Student(studentId, "Ahmer Mehmood");

        Course course1 = new Course(1L, "Math");
        Course course2 = new Course(2L, "Science");

        student.setCourses(Set.of(course1, course2));

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));

        // Act
        var result = studentService.getCoursesOfStudent(studentId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(studentRepository, times(1)).findById(studentId);
    }

    @Test
    void testGetCoursesOfStudent_NonExistentStudent() {
        // Arrange
        Long studentId = 99L;
        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            studentService.getCoursesOfStudent(studentId);
        });

        assertEquals("Student with ID 99 does not exist.", exception.getMessage());
        verify(studentRepository, times(1)).findById(studentId);
    }
}