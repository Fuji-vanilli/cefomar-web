package com.cefomar.repository;

import com.cefomar.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, String> {
    boolean existsByMatricule(String matricule);
}
