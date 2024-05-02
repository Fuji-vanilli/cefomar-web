package com.cefomar.services;

import com.cefomar.dto.StudentRequest;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
        String matricule= request.getMatricule();
        List<String> errors= studentValidator.validate(request);
       if (!errors.isEmpty()) {
            log.error("Sorry, some field is required");
            return generateResponse(
                    HttpStatus.BAD_REQUEST,
                    null,
                    Map.of(
                            "errors", errors
                    ),
                    "some field is required!"

            );
        }

        if (studentRepository.existsByMatricule(matricule)) {
            log.error("student with the matricule: {} already exist", matricule);
            return generateResponse(
                    HttpStatus.BAD_REQUEST,
                    null,
                    null,
                    "student with the matricule: "+matricule+" already exist into the database"
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
            location= new URI("api/student/get/"+matricule);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        return generateResponse(
                HttpStatus.OK,
                null,
                Map.of(
                        "student", studentMapper.mapToStudentResponse(student)
                ),
                "new student added successfully!"
        );
    }

    @Override
    public Response update(StudentRequest request) {
        final String matricule= request.getMatricule();
        if (!studentRepository.existsByMatricule(matricule)) {
            log.error("Sorry, student doesn't exist into the database");
            return generateResponse(
                    HttpStatus.BAD_REQUEST,
                    null,
                    null,
                    "Sorry, student doesn't exist into the database"

            );
        }

        Student student = studentRepository.findByMatricule(matricule)
                .orElseThrow(
                        () -> new IllegalArgumentException("error to fetch student into the database!")
                );

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
    public Response get(String matricule) {
        Optional<Student> studentOptional= studentRepository.findByMatricule(matricule);
        if (studentOptional.isEmpty()) {
            log.error("Sorry, student doesn't exist into the database");
            return generateResponse(

                    HttpStatus.BAD_REQUEST,
                    null,
                    null,
                    "Sorry, student doesn't exist into the database"

            );
        }

        Student student= studentOptional.get();
        log.info("student getted successfully!");

        return generateResponse(
                HttpStatus.OK,
                null,
                Map.of(
                        "student", studentMapper.mapToStudentResponse(student)
                ),
                "student getted successfully!"
        );
    }

    @Override
    public Response all() {
        log.info("all students getted successfully!");
        return generateResponse(
                HttpStatus.OK,
                null,
                Map.of(
                        "students", studentRepository.findAll().stream()
                                .map(studentMapper::mapToStudentResponse)
                                .toList()
                ),
                "all students getted successfully!"
        );
    }

    @Override
    public Response delete(String id) {
        return null;
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
