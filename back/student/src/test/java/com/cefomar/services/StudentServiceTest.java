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

import javax.swing.text.html.Option;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    // ADD STUDENT TEST
    @Test
    void add_Student_ValidationsFails() {
        StudentRequest studentRequest= new StudentRequest();

        when(studentValidator.validate(any(StudentRequest.class))).thenReturn(List.of("some field required!"));

        Response studentResponse = studentService.add(studentRequest);

        verify(studentValidator, times(1)).validate(any(StudentRequest.class));
        assertThat(HttpStatus.BAD_REQUEST).isEqualTo(studentResponse.getStatus());
        assertThat("some fields are required!").isEqualTo(studentResponse.getMessage());
    }

    @Test
    void add_Student_AlreadyExists() {
        StudentRequest studentRequest= new StudentRequest();

        when(studentValidator.validate(any(StudentRequest.class))).thenReturn(List.of());
        when(studentRepository.findByCode(any())).thenReturn(Optional.of(new Student()));

        Response studentResponse = studentService.add(studentRequest);

        verify(studentRepository, times(1)).findByCode(any());
        assertThat(HttpStatus.BAD_REQUEST).isEqualTo(studentResponse.getStatus());
        assertThat(studentResponse.getMessage()).contains("student with the code:");
    }
    @Test
    void add_NewStudent_Successfully() {
        StudentRequest request = new StudentRequest();

        when(studentValidator.validate(any(StudentRequest.class))).thenReturn(List.of());
        when(studentRepository.findByCode(any())).thenReturn(Optional.empty());
        when(studentMapper.mapToStudent(any(StudentRequest.class))).thenReturn(new Student());
        when(studentRepository.save(any(Student.class))).thenReturn(new Student());
        when(studentMapper.mapToStudentResponse(any(Student.class))).thenReturn(new StudentResponse());

        Response addStudent = studentService.add(request);

        verify(studentValidator, times(1)).validate(any(StudentRequest.class));
        verify(studentMapper, times(1)).mapToStudent(any(StudentRequest.class));
        verify(studentMapper, times(1)).mapToStudentResponse(any(Student.class));
        verify(studentRepository, times(1)).save(any(Student.class));
        assertThat(HttpStatus.CREATED).isEqualTo(addStudent.getStatus());
        assertThat("new student added successfully!").isEqualTo(addStudent.getMessage());
    }

    //UPDATE STUDENT TEST
    @Test
    void update_NotExistStudent() {
        StudentRequest request = new StudentRequest();

        when(studentRepository.findByCode(any())).thenReturn(Optional.empty());

        Response studentResponse = studentService.update(request);

        verify(studentRepository, times(1)).findByCode(any());
        assertThat(HttpStatus.BAD_REQUEST).isEqualTo(studentResponse.getStatus());
        assertThat("Sorry, student doesn't exist into the database").isEqualTo(studentResponse.getMessage());
    }
    @Test
    void updateStudent_Successfully() {
        StudentRequest request= new StudentRequest();

        when(studentRepository.findByCode(any())).thenReturn(Optional.of(new Student()));
        when(studentMapper.mapToStudentResponse(any(Student.class))).thenReturn(new StudentResponse());

        Response studentUpdate = studentService.update(request);

        verify(studentRepository, times(1)).findByCode(any());
        verify(studentMapper, times(1)).mapToStudentResponse(any(Student.class));

        assertThat(HttpStatus.OK).isEqualTo(studentUpdate.getStatus());
        assertThat("student updated successfully!").isEqualTo(studentUpdate.getMessage());
    }

    //GET STUDENT TEST
    @Test
    void get_Student_IfNotExist() {

        when(studentRepository.findByCode(any())).thenReturn(Optional.empty());

        Response studentResponse = studentService.get(any());

        verify(studentRepository, times(1)).findByCode(any());
        assertThat(HttpStatus.BAD_REQUEST).isEqualTo(studentResponse.getStatus());
        assertThat("Sorry, student doesn't exist into the database").isEqualTo(studentResponse.getMessage());
    }
    @Test
    void get_StudentSuccessfully() {

        when(studentRepository.findByCode(any())).thenReturn(Optional.of(new Student()));
        when(studentMapper.mapToStudentResponse(any(Student.class))).thenReturn(new StudentResponse());

        Response studentResponse = studentService.get(any());

        verify(studentRepository, times(1)).findByCode(any());
        assertThat(HttpStatus.OK).isEqualTo(studentResponse.getStatus());
        assertThat("student retrieved successfully!").isEqualTo(studentResponse.getMessage());
    }

    //GET ALL STUDENT TEST
    @Test
    void get_AllStudent_Successfully() {
        when(studentRepository.findAll()).thenReturn(List.of(new Student()));
        when(studentMapper.mapToStudentResponse(any(Student.class))).thenReturn(new StudentResponse());

        Response allStudent = studentService.all();

        verify(studentRepository, times(1)).findAll();
        verify(studentMapper).mapToStudentResponse(any(Student.class));

        assertThat(HttpStatus.OK).isEqualTo(allStudent.getStatus());
        assertThat("all students retrieved successfully!").isEqualTo(allStudent.getMessage());
    }

    //DELETE STUDENT TEST
    @Test
    void delete_Student_IfNotExist() {
        when(studentRepository.findByCode(any())).thenReturn(Optional.empty());

        Response deletedStudent = studentService.delete(any());

        verify(studentRepository, times(1)).findByCode(any());
        assertThat(HttpStatus.BAD_REQUEST).isEqualTo(deletedStudent.getStatus());
        assertThat("Sorry, student doesn't exist into the database").isEqualTo(deletedStudent.getMessage());
    }
    @Test
    void delete_StudentExist_AndSuccessfully() {
        when(studentRepository.findByCode(any())).thenReturn(Optional.of(new Student()));

        Response deletedStudent = studentService.delete(any());

        verify(studentRepository, times(1)).findByCode(any());
        verify(studentRepository, times(1)).deleteByCode(any());

        assertThat(HttpStatus.OK).isEqualTo(deletedStudent.getStatus());
        assertThat("student deleted successfully").isEqualTo(deletedStudent.getMessage());
    }
}