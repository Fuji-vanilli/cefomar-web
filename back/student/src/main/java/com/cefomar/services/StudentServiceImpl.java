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
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService{
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    @Override
    public Response add(StudentRequest request) {
        String matricule= request.getMatricule();
        List<String> errors= StudentValidator.validate(request);
        if (!errors.isEmpty()) {
            log.info("Sorry, some field is required");
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
            log.info("student with the matricule: {} already exist", matricule);
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

        URI location= ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("{matricule}")
                .buildAndExpand("api/student/get/"+matricule)
                .toUri();

        return generateResponse(
                HttpStatus.OK,
                location,
                Map.of(
                        "student", studentMapper.mapToStudentResponse(student)
                ),
                "new student added successfully!"
        );
    }

    @Override
    public Response update(StudentRequest request) {
        return null;
    }

    @Override
    public Response get(String id) {
        return null;
    }

    @Override
    public Response all() {
        return null;
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
