package com.example.demo.controller;

import com.example.demo.exception.ErrorResult;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice(basePackages = "com.example.demo.controller")
public class ExceptionController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResult> handleException(Exception ex) {
        ErrorResult errorResult = new ErrorResult("INTERNAL_SERVER_ERROR", "서버 내부 오류 발생");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResult);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorResult> handleNullPointerException(NullPointerException ex) {
        ErrorResult errorResult = new ErrorResult("BAD_REQUEST", "Null 값 오류 발생");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResult);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResult> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        ErrorResult errorResult = new ErrorResult("BAD_REQUEST", "입력 값이 공백으로 전달되었습니다.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResult);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResult> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        ErrorResult errorResult = new ErrorResult("BAD_REQUEST", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResult);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ErrorResult> EmptyResultDataAccessException(EmptyResultDataAccessException ex) {
        ErrorResult errorResult = new ErrorResult("NOT_FOUND", "해당 게시물을 찾을 수 없습니다.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResult);
    }


    @ExceptionHandler(RuntimeException.class)
    // DB 작업 실패시 여기로 오게 됨.
    public ResponseEntity<ErrorResult> handleRuntimeException(RuntimeException ex) {
        ErrorResult errorResult = new ErrorResult("INTERNAL_SERVER_ERROR", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResult);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<String> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        String errorMessage = "잘못된 요청입니다. 올바른 HTTP 요청 메서드를 사용해주세요.";
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(errorMessage);
    }



}
