package com.cefomar.mapper;

import com.cefomar.dto.StudentRequest;
import com.cefomar.dto.StudentResponse;
import com.cefomar.student.Student;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StudentMapperImpl implements StudentMapper{
    @Override
    public Student mapToStudent(StudentRequest request) {
        return Student.builder()
                .matricule(request.getMatricule())
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .dateOfBirth(request.getDateOfBirth())
                .photo(request.getPhoto())
                .build();
    }

    @Override
    public StudentResponse mapToStudentResponse(Student student) {
        return StudentResponse.builder()
                .id(student.getId())
                .createdDate(student.getCreatedDate())
                .lastUpdateDate(student.getLastUpdateDate())
                .firstname(student.getFirstname())
                .lastname(student.getLastname())
                .dateOfBirth(student.getDateOfBirth())
                .photo(student.getPhoto())
                .build();
    }
}
