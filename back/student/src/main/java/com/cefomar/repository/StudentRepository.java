package com.cefomar.repository;

import com.cefomar.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, String> {
    boolean existsByMatricule(String matricule);
    Optional<Student> findByMatricule(String matricule);
    void deleteByMatricule(String matricule);
}
