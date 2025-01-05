package com.ahmer.manytomany.Service;

import com.ahmer.manytomany.Model.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.transaction.Transactional;

import java.util.List;

public class StudentService {
    private EntityManager entityManager;

    public StudentService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Student> getAllStudents() {
        return entityManager.createQuery("SELECT s FROM Student s", Student.class).getResultList();
    }

    @Transactional
    public Student saveStudent(Student student) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(student);
        transaction.commit();
        return student;
    }

    public Student getStudentById(Long id) {
        return entityManager.find(Student.class, id);
    }
}
