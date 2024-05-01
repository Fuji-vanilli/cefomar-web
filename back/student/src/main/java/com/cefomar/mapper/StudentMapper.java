package com.cefomar.mapper;

import com.cefomar.dto.StudentRequest;
import com.cefomar.dto.StudentResponse;
import com.cefomar.student.Student;

public interface StudentMapper {
    Student mapToStudent(StudentRequest request);
    StudentResponse mapToStudentResponse(Student student);
}
