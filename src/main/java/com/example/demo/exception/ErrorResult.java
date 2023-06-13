package com.example.demo.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorResult {


    private HttpStatus status;
    private LocalDateTime timestamp = LocalDateTime.now();
    private String code;
    private String message;

    public int getStatus() {
        return status.value();
    }



}
