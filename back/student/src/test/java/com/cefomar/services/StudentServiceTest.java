package com.cefomar.services;

import com.cefomar.dto.StudentRequest;
import com.cefomar.dto.StudentResponse;
import com.cefomar.mapper.StudentMapper;
import com.cefomar.repository.StudentRepository;
import com.cefomar.student.Student;
import com.cefomar.utils.Response;
import com.cefomar.validators.StudentValidator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


//@ExtendWith(MockitoExtension.class)

public class StudentServiceTest {
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private StudentMapper studentMapper;
    @Mock
    private StudentValidator studentValidator;
    @InjectMocks
    private StudentServiceImpl studentService;
    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
    }
    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }
    @Test
    void add_Student_ValidationsFails() {
        StudentRequest studentRequest= new StudentRequest();

        when(studentValidator.validate(any(StudentRequest.class))).thenReturn(List.of("some field required!"));

        Response studentResponse = studentService.add(studentRequest);

        verify(studentValidator, times(1)).validate(any(StudentRequest.class));
        assertThat(HttpStatus.BAD_REQUEST).isEqualTo(studentResponse.getStatus());
        assertThat("some field is required!").isEqualTo(studentResponse.getMessage());
    }

    @Test
    void add_Student_AlreadyExists() {
        StudentRequest studentRequest= new StudentRequest();

        when(studentValidator.validate(any(StudentRequest.class))).thenReturn(List.of());
        when(studentRepository.existsByMatricule(any())).thenReturn(true);

        Response studentResponse = studentService.add(studentRequest);

        verify(studentRepository, times(1)).existsByMatricule(any());
        assertThat(HttpStatus.BAD_REQUEST).isEqualTo(studentResponse.getStatus());
        assertThat(studentResponse.getMessage()).contains("student with the matricule:");
    }
    @Test
    void add_NewStudent_Successfully() {
        StudentRequest request = new StudentRequest();

        when(studentValidator.validate(any(StudentRequest.class))).thenReturn(List.of());
        when(studentRepository.existsByMatricule(any())).thenReturn(false);
        when(studentMapper.mapToStudent(any(StudentRequest.class))).thenReturn(new Student());
        when(studentRepository.save(any(Student.class))).thenReturn(new Student());
        when(studentMapper.mapToStudentResponse(any(Student.class))).thenReturn(new StudentResponse());

        Response addStudent = studentService.add(request);

        verify(studentValidator, times(1)).validate(any(StudentRequest.class));
        verify(studentMapper, times(1)).mapToStudent(any(StudentRequest.class));
        verify(studentMapper, times(1)).mapToStudentResponse(any(Student.class));
        verify(studentRepository, times(1)).save(any(Student.class));
        assertThat(HttpStatus.OK).isEqualTo(addStudent.getStatus());
    }
}