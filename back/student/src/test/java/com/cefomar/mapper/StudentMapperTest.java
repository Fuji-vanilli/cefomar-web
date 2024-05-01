package com.cefomar.mapper;

import com.cefomar.dto.StudentRequest;
import com.cefomar.dto.StudentResponse;
import com.cefomar.student.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class StudentMapperTest {

    private StudentMapper studentMapper;

    @BeforeEach
    void setUp() {
        studentMapper= new StudentMapperImpl();
    }

    @Test
    void should_student_isMap_toStudentResponse() {
        //GIVEN
        String idStudent= UUID.randomUUID().toString();
        Student student= Student.builder()
                .id(idStudent)
                .firstname("rakoto")
                .lastname("firinga")
                .dateOfBirth(new Date())
                .build();

        //WHEN
        StudentResponse studentResponse = studentMapper.mapToStudentResponse(student);

        //THEN
        assertThat(student.getFirstname()).isEqualTo(studentResponse.getFirstname());
        assertThat(student.getLastname()).isEqualTo(studentResponse.getLastname());
        assertThat(student.getDateOfBirth()).isEqualTo(studentResponse.getDateOfBirth());
    }

    @Test
    void should_studentRequest_isMap_toStudent() {
        StudentRequest studentRequest= StudentRequest.builder()
                .firstname("rakoto")
                .lastname("firinga")
                .dateOfBirth(new Date())
                .build();

        //WHEN
        Student student = studentMapper.mapToStudent(studentRequest);

        //THEN
        assertThat(studentRequest.getFirstname()).isEqualTo(student.getFirstname());
        assertThat(studentRequest.getLastname()).isEqualTo(student.getLastname());
        assertThat(studentRequest.getDateOfBirth()).isEqualTo(student.getDateOfBirth());
    }
}