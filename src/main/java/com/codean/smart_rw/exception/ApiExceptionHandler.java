package com.codean.smart_rw.exception;

import com.codean.smart_rw.exception.custom.ForbiddenException;
import com.codean.smart_rw.exception.custom.NotFoundException;
import com.codean.smart_rw.model.response.DefaultResponse;
import com.codean.smart_rw.model.response.ErrorsResponse;
import com.codean.smart_rw.model.response.ResponseMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApiExceptionHandler {

    private static final String SUCCESS = "Success";

    private static final String FAILED = "Failed";
    private static final String VERSION = "v1";

    // ================== DUPLICATE ==================
    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<DefaultResponse> handleAlreadyExistsException(
            DuplicateKeyException e,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.CONFLICT;

        DefaultResponse response = new DefaultResponse(
                SUCCESS,
                ResponseMessage.DATA_ALREADY_EXISTS,
                status.value()
        );

        return new ResponseEntity<>(response, new HttpHeaders(), status);
    }

    // ================== NOT FOUND ==================
    @ExceptionHandler({
            NotFoundException.class,
            EmptyResultDataAccessException.class,
            NoSuchElementException.class,
            IllegalArgumentException.class
    })
    public ResponseEntity<Object> handleNotFoundException(
            Exception e,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        DefaultResponse response = new DefaultResponse(
                SUCCESS,
               e.getMessage(),
                status.value()
        );

        return new ResponseEntity<>(response, new HttpHeaders(), status);
    }

    // ================== VALIDATION ==================
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationErrors(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;

        ErrorsResponse response = new ErrorsResponse(
                SUCCESS,
                ResponseMessage.DATA_INVALID,
                request.getRequestURI(),
                LocalDateTime.now().toString(),
                status.value(),
                VERSION,
                errors
        );

        return new ResponseEntity<>(response, new HttpHeaders(), status);
    }

    // ================== MALFORMED JSON ==================
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleNotReadableExceptionErrors(
            HttpMessageNotReadableException ex,
            HttpServletRequest request
    ) {
        List<String> errors = new ArrayList<>();
        errors.add(ResponseMessage.DATA_INVALID);

        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;

        ErrorsResponse response = new ErrorsResponse(
                SUCCESS,
                //ResponseMessage.DATA_INVALID,
                ex.getMessage(),
                request.getRequestURI(),
                LocalDateTime.now().toString(),
                status.value(),
                VERSION,
                errors
        );

        return new ResponseEntity<>(response, new HttpHeaders(), status);
    }

    // ================== DATA INTEGRITY ==================
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolation(
            DataIntegrityViolationException e,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        DefaultResponse response = new DefaultResponse(
                SUCCESS,
                ResponseMessage.DATA_INVALID,
                status.value()
        );

        return new ResponseEntity<>(response, new HttpHeaders(), status);
    }

    // ================== ACCESS DENIED ==================
    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<Object> handleForbiddenException(
            AuthorizationDeniedException e,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.FORBIDDEN;

        DefaultResponse response = new DefaultResponse(
                FAILED,
                ResponseMessage.ACCESS_DENIED,
                status.value()
        );

        return new ResponseEntity<>(response, new HttpHeaders(), status);
    }
}
