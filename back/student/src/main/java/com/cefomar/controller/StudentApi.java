package com.cefomar.controller;

import com.cefomar.dto.StudentRequest;
import com.cefomar.services.StudentService;
import com.cefomar.utils.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.cefomar.utils.Root.APP_ROOT;

@RestController
@RequestMapping(APP_ROOT)
@RequiredArgsConstructor
public class StudentApi implements StudentController {
    private final StudentService studentService;
    @Override
    public ResponseEntity<Response> add(StudentRequest request) {
        return ResponseEntity.ok(studentService.add(request));
    }

    @Override
    public ResponseEntity<Response> update(StudentRequest request) {
        return ResponseEntity.ok(studentService.update(request));
    }

    @Override
    public ResponseEntity<Response> get(String code) {
        return ResponseEntity.ok(studentService.get(code));
    }

    @Override
    public ResponseEntity<Response> all() {
        return ResponseEntity.ok(studentService.all());
    }

    @Override
    public ResponseEntity<Response> delete(String code) {
        return ResponseEntity.ok(studentService.delete(code));
    }
}
