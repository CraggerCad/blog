package com.treeleaf.task.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;

/**
 * Created by User on 2/12/2022.
 */

@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> customExceptionHandler(CustomException e){
        e.printStackTrace();
        return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {AccessDeniedException.class})
    protected ResponseEntity<?> accessDenied(AccessDeniedException e) {
        e.printStackTrace();
        return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.FORBIDDEN);

    }
}
