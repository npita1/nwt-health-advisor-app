package com.example.accessingdatamysql.exceptions;

import com.example.accessingdatamysql.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.validation.FieldError;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        String errorMessage = fieldErrors.stream()
                .map(fe -> fe.getDefaultMessage())
                .collect(Collectors.joining(", "));
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "validation");
        errorResponse.put("message", errorMessage);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ReservationNotFoundException.class, EventNotFoundException.class, UserNotFoundException.class, DoctorInfoNotFoundException.class,AppointmentNotFoundException.class})
    @ResponseBody
    public ResponseEntity<Object> handleNotFoundExceptions(Exception exc) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "not_found");
        errorResponse.put("message", exc.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleOtherExceptions(Exception exc) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(exc.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
