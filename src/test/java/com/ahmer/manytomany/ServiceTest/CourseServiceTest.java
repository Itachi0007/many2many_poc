package com.ahmer.manytomany.ServiceTest;

import com.ahmer.manytomany.Model.Course;
import com.ahmer.manytomany.Model.Student;
import com.ahmer.manytomany.Service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.ahmer.manytomany.Repository.CourseRepository;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseService courseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test cases go here
    @Test
    void testGetAllStudentsByCourse_ValidCourse() {
        // Arrange
        Long courseId = 1L;
        Course course = new Course(courseId, "Math");

        Student student1 = new Student(1L, "Ahmer Mehmood");
        Student student2 = new Student(2L, "Karthick");

        course.setStudents(Set.of(student1, student2));

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        // Act
        var result = courseService.getAllStudentsByCourse(courseId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(courseRepository, times(1)).findById(courseId);
    }

    @Test
    void testGetAllStudentsByCourse_NonExistentCourse() {
        // Arrange
        Long courseId = 99L;
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            courseService.getAllStudentsByCourse(courseId);
        });

        assertEquals("Course with ID 99 does not exist.", exception.getMessage());

        // Ensure that the method on mocked object was called (or not called) as expected no of times
        verify(courseRepository, times(1)).findById(courseId);
    }

}
