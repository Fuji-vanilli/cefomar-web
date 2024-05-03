package com.cefomar.controller;

import com.cefomar.dto.StudentRequest;
import com.cefomar.utils.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface StudentController {
    @PostMapping("add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ResponseEntity<Response> add(@RequestBody StudentRequest request);
    @PutMapping("update")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ResponseEntity<Response> update(@RequestBody StudentRequest request);
    @GetMapping("get/{code}")
    ResponseEntity<Response> get(@PathVariable String code);
    @GetMapping("all")
    ResponseEntity<Response> all();
    @DeleteMapping("delete/{code}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ResponseEntity<Response> delete(@PathVariable String code);

    @GetMapping("authorities")
    List<String> authorities();
}
