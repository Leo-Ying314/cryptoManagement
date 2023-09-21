package com.project.crypto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CryptoExceptionController {
    @ExceptionHandler(value = CryptoClientException.class)
    public ResponseEntity<Object> exception(CryptoClientException exception) {
        return new ResponseEntity<>("Invalid Symbol "+exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = InvalidInputException.class)
    public ResponseEntity<Object> invalidInputException(CryptoClientException exception) {
        return new ResponseEntity<>("invalid input: "+exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
