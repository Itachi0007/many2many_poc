package com.ahmer.manytomany.Service;

import com.ahmer.manytomany.Model.Course;
import com.ahmer.manytomany.Model.Student;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import com.ahmer.manytomany.Repository.CourseRepository;


@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    // Fetch all courses
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    // Fetch a course by ID
    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    // Update an existing course by ID
    public Course updateCourse(Long id, Course updatedCourse) {
        return courseRepository.findById(id)
                .map(existingCourse -> {
                    existingCourse.setTitle(updatedCourse.getTitle());
                    return courseRepository.save(existingCourse);
                })
                .orElseThrow(() -> new IllegalArgumentException("Course with ID " + id + " does not exist."));
    }

    // Delete a course by ID
    public boolean deleteCourseById(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new IllegalArgumentException("Course with ID " + id + " does not exist.");
        }
        courseRepository.deleteById(id);
        return true;
    }

    public List<Student> getAllStudentsByCourse(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course with ID " + courseId + " does not exist."));
        return List.copyOf(course.getStudents()); // Convert Set to List
    }

    public List<Course> saveCourses(List<Course> courses) {
        if (courses == null || courses.isEmpty()) {
            throw new IllegalArgumentException("Course list cannot be null or empty.");
        }
        return courseRepository.saveAll(courses);
    }
}