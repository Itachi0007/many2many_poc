package com.ahmer.manytomany.Service;


import com.ahmer.manytomany.Model.Course;
import com.ahmer.manytomany.Model.Student;
import com.ahmer.manytomany.Repository.CourseRepository;
import com.ahmer.manytomany.Repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public StudentService(StudentRepository studentRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    public Student saveStudentWithExistingCourses(Student student) {
        // Prepare a set to hold existing courses
        Set<Course> existingCourses = new HashSet<>();

        for (Course course : student.getCourses()) {
            // Check if the course exists in the database
            Optional<Course> existingCourse = courseRepository.findById(course.getCourseId());
            if (existingCourse.isPresent()) {
                // Add the existing course to the set
                existingCourses.add(existingCourse.get());
            } else {
                // Throw an exception if the course does not exist
                throw new IllegalArgumentException("Course with ID " + course.getCourseId() + " does not exist.");
            }
        }

        // Set the existing courses to the student
        student.setCourses(existingCourses);

        // Save the student with the associated courses
        return studentRepository.save(student);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public List<Course> getCoursesOfStudent(Long studentId){
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student with ID " + studentId + " does not exist."));
        return List.copyOf(student.getCourses());
    }

    public Student updateStudentCourses(Long studentId, List<Long> courseIds) {
        // Fetch the student
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student with ID " + studentId + " not found"));

        // Fetch the courses by IDs
        List<Course> courses = courseRepository.findAllById(courseIds);

        // Validate that all course IDs exist
        if (courses.size() != courseIds.size()) {
            throw new IllegalArgumentException("Some course IDs are invalid");
        }

        // Update the student's courses
        student.setCourses(new HashSet<>(courses));

        // Save and return the updated student
        return studentRepository.save(student);
    }
}
