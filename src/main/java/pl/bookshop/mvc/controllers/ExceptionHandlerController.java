package pl.bookshop.mvc.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {
	@ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List<String>> handleConstraintViolationException(ConstraintViolationException exception) {
		List<String> validationErrors = new ArrayList<>();
		
		Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();
    	for (ConstraintViolation<?> constraintViolation: constraintViolations) {
    		String error = constraintViolation.getMessage();
    		validationErrors.add(error);
    	}
    	return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
		List<String> validationErrors = new ArrayList<>();
		
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
    	for (FieldError fieldError: fieldErrors) {
    		String error = fieldError.getDefaultMessage();
    		validationErrors.add(error);
    	}
    	return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);
    }
}

