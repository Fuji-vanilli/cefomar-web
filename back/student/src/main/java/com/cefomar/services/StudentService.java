package com.cefomar.services;

import com.cefomar.dto.StudentRequest;
import com.cefomar.utils.Response;

public interface StudentService {
    Response add(StudentRequest request);
    Response update(StudentRequest request);
    Response get(String matricule);
    Response all();
    Response delete(String id);
}
