package com.lucasteixeira.controller;

import com.lucasteixeira.infrastructure.exceptions.ConflictException;
import com.lucasteixeira.infrastructure.exceptions.ResourceNotFoundException;
import com.lucasteixeira.infrastructure.exceptions.UnauhthorizedException;
import com.lucasteixeira.infrastructure.exceptions.dto.ErrorResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handlerResourceNotFoundException(ResourceNotFoundException resourceNotFoundException,
                                                                   HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(buildError(HttpStatus.NOT_FOUND.value(),
                resourceNotFoundException.getMessage(),
                request.getRequestURI(),
                "Not Found"));
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponseDTO> handlerConflictException(ConflictException conflictException,
                                                                     HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(buildError(HttpStatus.CONFLICT.value(),
                conflictException.getMessage(),
                request.getRequestURI(),
                "Conflict"));
    }

    @ExceptionHandler(UnauhthorizedException.class)
    public ResponseEntity<ErrorResponseDTO> handlerUnauhthorizedException(UnauhthorizedException unauhthorizedException,
                                                                          HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(buildError(HttpStatus.UNAUTHORIZED.value(),
                unauhthorizedException.getMessage(),
                request.getRequestURI(),
                "Unauthorized"));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDTO> handlerIllegalArgumentException(IllegalArgumentException illegalArgumentException,
                                                                            HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(buildError(HttpStatus.NOT_FOUND.value(),
                illegalArgumentException.getMessage(),
                request.getRequestURI(),
                "Not Found"));
    }

    private ErrorResponseDTO buildError(int status, String message, String path, String error) {
        return ErrorResponseDTO.builder()
                .timestamp(LocalDateTime.now())
                .message(message)
                .path(path)
                .error(error)
                .Status(status)
                .build();
    }
}
