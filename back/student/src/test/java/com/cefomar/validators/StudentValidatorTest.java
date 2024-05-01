package com.cefomar.validators;

import com.cefomar.dto.StudentRequest;
import com.cefomar.student.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class StudentValidatorTest {
    private StudentRequest request;
    @BeforeEach
    void setUp() {
        request= StudentRequest.builder()
                .dateOfBirth(new Date())
                .build();
    }
    @Test
    void should_validThis_studentRequest() {
        request.setFirstname("first");
        request.setLastname("me");
        List<String> errors = StudentValidator.validate(request);

        assertThat(errors).isEmpty();
    }
    @Test
    void should_notValidThis_studentRequest() {
        List<String> errors = StudentValidator.validate(request);

        assertThat(errors).isNotNull();
    }

}