package com.cefomar.services;

import com.cefomar.dto.StudentRequest;
import com.cefomar.mapper.StudentMapper;
import com.cefomar.repository.StudentRepository;
import com.cefomar.utils.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService{
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    @Override
    public Response add(StudentRequest request) {
        return null;
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
}
