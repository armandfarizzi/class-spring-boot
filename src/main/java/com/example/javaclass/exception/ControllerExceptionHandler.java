package com.example.javaclass.exception;


import com.example.javaclass.dto.EventDto;
import com.example.javaclass.kafka.EventProducer;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @Autowired
    private EventProducer eventProducer;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    private Map<String, Object> getErrorsMap(List<String> errors) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        errorResponse.put("code", 400);
        errorResponse.put("message", "Invalid request");
        return errorResponse;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralException(Exception e) {
        var code = 500;
        var result = new HashMap<>(){{
           put("code", 500);
           put("message", "unexpected error, please try again later");
        }};

        if (e instanceof CustomException){
            CustomException ex = (CustomException) e;
            code = ex.getCode();

            result.put("message", ex.getMessage());
            result.put("error", ex.getError());
            result.put("code", code);
        } else {
            result.put("error", e.getMessage());
        }
        String errorId = UUID.randomUUID().toString();
        result.put("error_id", errorId);
        EventDto event = EventDto.builder()
                .id(errorId)
                .eventName("error exception happen!")
                .message(e.getMessage())
                .build();
        try {
            eventProducer.sendEvent(event);
        } catch (Exception ignored) {}
        return ResponseEntity.status(code).body(result);
    }
}
