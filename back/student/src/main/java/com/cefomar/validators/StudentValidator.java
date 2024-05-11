package com.cefomar.validators;

import com.cefomar.dto.StudentRequest;
import lombok.Data;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Configuration
public class StudentValidator {

    public List<String> validate(StudentRequest request) {
        List<String> errors= new ArrayList<>();

        if (Objects.isNull(request)) {
            errors.add("student request null");
            return errors;
        }

        if (Objects.isNull(request.getFirstname())) {
            errors.add("firstname doesn't null");
        }

        if (Objects.isNull(request.getLastname())) {
            errors.add(("lastname doesn't null"));
        }

        if (Objects.isNull(request.getDateOfBirth())) {
            errors.add("date of birth doesn't null");
        }

        return errors;
    }
}
