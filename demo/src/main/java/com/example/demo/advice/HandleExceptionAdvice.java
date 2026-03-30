package com.example.demo.advice;


import com.example.demo.dto.ErrorFieldDTO;
import com.example.demo.dto.ErrorResponseDTO;
import com.example.demo.exceptions.*;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class HandleExceptionAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleMethodArgumentNotValid(MethodArgumentNotValidException ex){

        Map<String, List<String>> errors = ex.getFieldErrors()
                .stream()
                .collect(Collectors.groupingBy(
                        FieldError::getField,
                        Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())
                ));

        List<ErrorFieldDTO> errorList = errors.entrySet()
                .stream()
                .map(entry -> new ErrorFieldDTO(entry.getKey(), entry.getValue()))
                .toList();

        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                "Invalid Request",
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                errorList
        );

        return ResponseEntity.badRequest().body(errorResponse);
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleResourceNotFoundException(ResourceNotFoundException ex){
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                "Resource Not Found",
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                List.of(new ErrorFieldDTO("resource",List.of(ex.getMessage())))
        );

        return ResponseEntity.badRequest().body(errorResponse);
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDTO> handleConstraintViolationException(ConstraintViolationException ex){
        List<ErrorFieldDTO> errorList = ex.getConstraintViolations()
                .stream()
                .map(violation -> new ErrorFieldDTO(
                        extractFieldName(violation.getPropertyPath()),
                        List.of(violation.getMessage())
                )).toList();
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                "Invalid Request",
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                errorList
        );
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(InvalidEnumValueException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidEnumException(InvalidEnumValueException ex){
        ErrorFieldDTO fieldError = new ErrorFieldDTO(
                ex.getField(),
                List.of("Invalid Value. Accepted values: " + ex.getAcceptedValues())
        );

        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                "Invalid Request",
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                List.of(fieldError)
        );

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDTO> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex){
        assert ex.getRequiredType() != null;
        ErrorFieldDTO fieldError = new ErrorFieldDTO(
                ex.getName(),
                List.of("Invalid value. Expected value: " + ex.getRequiredType().getSimpleName())
        );
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                "Invalid Request",
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                List.of(fieldError)
        );

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(AdventurerException.class)
    public ResponseEntity<ErrorResponseDTO> handleAdventurerException(AdventurerException ex){
        ErrorFieldDTO fieldError = new ErrorFieldDTO(
                "Adventurer",
                List.of(ex.getMessage())
        );
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                "Invalid Request",
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                List.of(fieldError)
        );
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(MissionException.class)
    public ResponseEntity<ErrorResponseDTO> handleMissionException(MissionException ex){
        ErrorFieldDTO fieldError = new ErrorFieldDTO(
                "Mission",
                List.of(ex.getMessage())
        );
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                "Invalid Request",
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                List.of(fieldError)
        );
        return ResponseEntity.badRequest().body(errorResponse);
    }

    private String extractFieldName(Path propertyPath) {
        String field = null;

        for (Path.Node node : propertyPath) {
            field = node.getName();
        }

        return field;
    }
}
