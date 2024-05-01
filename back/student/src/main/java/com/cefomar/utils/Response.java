package com.cefomar.utils;

import java.net.URI;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.HttpStatus;

public class Response {
    private Instant timeStamp;
    private HttpStatus status;
    private int statusCode;
    private Map<?, ?> data;
    private URI locations;
    private String message;

}
