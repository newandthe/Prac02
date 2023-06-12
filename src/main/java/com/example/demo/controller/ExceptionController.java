package com.example.demo.controller;

import com.example.demo.exception.ErrorCode;
import com.example.demo.exception.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResult> handleException(Exception ex) {
        ErrorResult errorResult = new ErrorResult(HttpStatus.INTERNAL_SERVER_ERROR, LocalDateTime.now(),"INTERNAL_SERVER_ERROR", "서버 내부 오류 발생");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResult);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorResult> handleNullPointerException(NullPointerException ex) {
        log.error("handleNullPointerException: {}", ex.getMessage());
        ErrorResult errorResult = new ErrorResult(HttpStatus.INTERNAL_SERVER_ERROR, LocalDateTime.now(),"BAD_REQUEST", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResult);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResult> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        // 유효성(Validation) 검사에서 Blank 오류 발생한경우.
        log.error("handleMethodArgumentNotValid: {}", ex.getMessage());

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        List<String> messages = fieldErrors.stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        ErrorResult errorResult = new ErrorResult(HttpStatus.BAD_REQUEST, LocalDateTime.now(), "BAD_REQUEST", messages.toString());
//        ErrorCode.BAD_REQUEST.getStatus();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResult);

    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    // Validation 결여 에러
    public ResponseEntity<ErrorResult> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        log.error("handleDataIntegrityViolationException: {}", ex.getMessage());
        ErrorResult errorResult = new ErrorResult(ErrorCode.BAD_REQUEST.getStatus(), LocalDateTime.now(),"BAD_REQUEST", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResult);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)    // 메소드 타입 미스매치 예외
    public ResponseEntity<ErrorResult> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        log.error("handleMethodArgumentTypeMismatch: {}", ex.getMessage());
        ErrorResult errorResult = new ErrorResult(ErrorCode.BAD_REQUEST.getStatus(), LocalDateTime.now(), "BAD_REQUEST", "메소드 요청 방식을 다시 확인해주세요.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResult);
    }



    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ErrorResult> EmptyResultDataAccessException(EmptyResultDataAccessException ex) {
        log.error("EmptyResultDataAccessException: {}", ex.getMessage());
        ErrorResult errorResult = new ErrorResult(HttpStatus.NOT_FOUND, LocalDateTime.now(), "NOT_FOUND", "해당 게시물을 찾을 수 없습니다.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResult);
    }

    @ExceptionHandler(ConfigDataResourceNotFoundException.class)
    public ResponseEntity<ErrorResult> handleResourceNotFoundException(ConfigDataResourceNotFoundException ex) {
        log.error("handleResourceNotFoundException: {}", ex.getMessage());
        ErrorResult errorResult = new ErrorResult(HttpStatus.NOT_FOUND, LocalDateTime.now(),"NOT_FOUND", "해당 게시물을 찾을 수 없습니다.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResult);
    }


    @ExceptionHandler(RuntimeException.class)
    // DB 작업 실패시, 혹은 입력 값 혹은 잘못된 요청일 경우 여기로 오게 됨.
    public ResponseEntity<ErrorResult> handleRuntimeException(RuntimeException ex) {
        log.error("handleRuntimeException: {}", ex.getMessage());
        ErrorResult errorResult = new ErrorResult(HttpStatus.INTERNAL_SERVER_ERROR, LocalDateTime.now(),"INTERNAL_SERVER_ERROR", "요청을 다시 한번 확인해주세요.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResult);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    // 올바르지 못한 메소드 요청이 오는 경우. (GET인데 POST 요청 등 ..)
    public ResponseEntity<ErrorResult> httpRequestMethodNotSupportedException(Exception ex) {
//        System.out.println("405번 예외처리 시작.");
        log.error("handleHttpRequestMethodNotSupportedException: {}", ex.getMessage());
        ErrorResult errorResult = new ErrorResult(HttpStatus.METHOD_NOT_ALLOWED, LocalDateTime.now(),"METHOD_NOT_ALLOWED", "허용되지 않은 메서드입니다. 올바른 HTTP 요청 메서드를 사용해주세요.");
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(errorResult);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResult> httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException ex){
        log.error("httpMessageNotReadableExceptionHandler: {}", ex.getMessage());
        ErrorResult errorResult = new ErrorResult(HttpStatus.INTERNAL_SERVER_ERROR, LocalDateTime.now(),"ResultForm.ResultCode.NO_BODY", "파라미터가 존재하지 않거나, 문법 오류 입니다.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResult);
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<ErrorResult> NumberFormatExceptionHandler(NumberFormatException ex){
        log.error("NumberFormatExceptionHandler: {}", ex.getMessage());
        ErrorResult errorResult = new ErrorResult(HttpStatus.BAD_REQUEST, LocalDateTime.now(), "BAD_REQUEST", "양식을 맞추어 요청하시거나, 길이가 너무 깁니다.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResult);
    }







}
