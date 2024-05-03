package com.cefomar.controller;

import com.cefomar.dto.StudentRequest;
import com.cefomar.utils.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface StudentController {
    @PostMapping("add")
    ResponseEntity<Response> add(@RequestBody StudentRequest request);
    @PutMapping("update")
    ResponseEntity<Response> update(@RequestBody StudentRequest request);
    @GetMapping("get/{code}")
    ResponseEntity<Response> get(@PathVariable String code);
    @GetMapping("all")
    ResponseEntity<Response> all();
    @DeleteMapping("delete/{code}")
    ResponseEntity<Response> delete(@PathVariable String code);
}
