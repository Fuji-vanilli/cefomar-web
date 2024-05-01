package com.cefomar.mapper;

import com.cefomar.dto.StudentResponse;
import com.cefomar.student.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
class StudentMapperTest {

    private StudentMapper studentMapper;

    @BeforeEach
    void setUp() {
        studentMapper= new StudentMapperImpl();
    }

    @Test
    void sure_ThatStudent_IsMap_ToStudentResponse() {
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
}