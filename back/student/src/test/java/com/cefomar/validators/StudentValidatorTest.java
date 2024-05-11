package com.cefomar.validators;

import com.cefomar.dto.StudentRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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

    }
    @Test
    void should_notValidThis_studentRequest_withoutFirstname() {
        request.setLastname("me");

    }
    @Test
    void should_notValidThis_studentRequest_withoutLastName() {
        request.setFirstname("test");
    }

}