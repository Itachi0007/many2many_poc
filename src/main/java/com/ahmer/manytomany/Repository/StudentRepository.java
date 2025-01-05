package com.ahmer.manytomany.Repository;

import com.ahmer.manytomany.Model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {}
