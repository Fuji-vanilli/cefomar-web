package com.cefomar.services;

import com.cefomar.dto.StudentRequest;
import com.cefomar.utils.Response;

public interface StudentService {
    Response add(StudentRequest request);
    Response update(StudentRequest request);
    Response get(String code);
    Response all();
    Response delete(String code);
}
