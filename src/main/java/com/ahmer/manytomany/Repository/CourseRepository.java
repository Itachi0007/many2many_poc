package com.ahmer.manytomany.Repository;

import com.ahmer.manytomany.Model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {}