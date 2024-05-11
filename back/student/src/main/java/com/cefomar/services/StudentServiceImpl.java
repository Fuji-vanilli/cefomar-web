package com.cefomar.services;

import com.cefomar.dto.StudentRequest;
import com.cefomar.dto.StudentResponse;
import com.cefomar.mapper.StudentMapper;
import com.cefomar.repository.StudentRepository;
import com.cefomar.student.Student;
import com.cefomar.utils.Response;
import com.cefomar.validators.StudentValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService{
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final StudentValidator studentValidator;
    @Override
    public Response add(StudentRequest request) {
        final String codeStudent= request.getCode();
        List<String> errors= studentValidator.validate(request);

        if (!errors.isEmpty()) {
            log.error("validations error: {}", errors);
            return generateResponse(
                    HttpStatus.BAD_REQUEST,
                    null,
                    Map.of(
                            "errors", errors
                    ),
                    "some fields are required!"
            );
        }

        Optional<Student> existingStudent = studentRepository.findByCode(codeStudent);
        if (existingStudent.isPresent()) {
            log.error("student with the code: {} already exist", codeStudent);
            return generateResponse(
                    HttpStatus.BAD_REQUEST,
                    null,
                    null,
                    "student with the code: "+ codeStudent +" already exist into the database"
            );
        }

        Student student= studentMapper.mapToStudent(request);
        student.setId(UUID.randomUUID().toString());
        student.setCreatedDate(new Date());
        student.setLastUpdateDate(new Date());

        studentRepository.save(student);
        log.info("new student added successfully!");

        URI location;
        try {
            location= new URI("api/student/get/"+ codeStudent);
        } catch (URISyntaxException e) {
            log.error("failed to create URI for a new student ", e);
            throw new RuntimeException("failed to create URI for a new student ", e);
        }

        return generateResponse(
                HttpStatus.CREATED,
                null,
                Map.of(
                        "student", studentMapper.mapToStudentResponse(student)
                ),
                "new student added successfully!"
        );
    }

    @Override
    public Response update(StudentRequest request) {
        final String codeStudent= request.getCode();
        Optional<Student> existingStudent = studentRepository.findByCode(codeStudent);

        if (existingStudent.isEmpty()) {
            log.error("Sorry, student doesn't exist into the database");
            return generateResponse(
                    HttpStatus.BAD_REQUEST,
                    null,
                    null,
                    "Sorry, student doesn't exist into the database"

            );
        }

        Student student = existingStudent.get();

        student.setFirstname(request.getFirstname());
        student.setLastname(request.getLastname());
        student.setDateOfBirth(request.getDateOfBirth());
        student.setLastUpdateDate(new Date());

        studentRepository.save(student);
        log.info("student updated successfully!");

        return generateResponse(
                HttpStatus.OK,
                null,
                Map.of(
                        "student", studentMapper.mapToStudentResponse(student)
                ),
                "student updated successfully!"
        );
    }

    @Override
    public Response get(String code) {
        Optional<Student> existingStudent= studentRepository.findByCode(code);
        if (existingStudent.isEmpty()) {
            log.error("Sorry, student doesn't exist into the database");
            return generateResponse(
                    HttpStatus.BAD_REQUEST,
                    null,
                    null,
                    "Sorry, student doesn't exist into the database"

            );
        }

        Student student= existingStudent.get();
        log.info("student retrieved successfully!");

        return generateResponse(
                HttpStatus.OK,
                null,
                Map.of(
                        "student", studentMapper.mapToStudentResponse(student)
                ),
                "student retrieved successfully!"
        );
    }

    @Override
    public Response all() {
        try {
            List<StudentResponse> studentResponses = studentRepository.findAll().stream()
                    .map(studentMapper::mapToStudentResponse)
                    .toList();

            log.info("all students retrieved successfully!");
            return generateResponse(
                    HttpStatus.OK,
                    null,
                    Map.of(
                            "students", studentResponses
                    ),
                    "all students retrieved successfully!"
            );
        } catch (Exception e) {
            log.error("error to retrieved all student ", e);
            return generateResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null,
                    null,
                    "failed to retrieved all student"
            );
        }

    }

    @Override
    public Response delete(String code) {
        Optional<Student> existingStudent= studentRepository.findByCode(code);
        if (existingStudent.isEmpty()) {
            log.error("Sorry, student doesn't exist into the database");
            return generateResponse(
                    HttpStatus.BAD_REQUEST,
                    null,
                    null,
                    "Sorry, student doesn't exist into the database"
            );
        }

        try {
            studentRepository.deleteByCode(code);
            log.info("student deleted successfully");

            return generateResponse(
                    HttpStatus.OK,
                    null,
                    null,
                    "student deleted successfully"
            );
        } catch (Exception e) {
            log.error("error occurred while deleting student!");
            return generateResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null,
                    null,
                    "error occurred while deleting student"
            );
        }
    }
    private Response generateResponse(HttpStatus status, URI location, Map<?, ?> data, String message) {
        return Response.builder()
                .timeStamp(LocalDateTime.now())
                .location(location)
                .status(status)
                .statusCode(status.value())
                .data(data)
                .message(message)
                .build();
    }
}
