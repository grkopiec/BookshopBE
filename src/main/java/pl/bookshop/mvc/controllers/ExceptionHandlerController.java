package pl.bookshop.mvc.controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
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

		List<ObjectError> errors = exception.getBindingResult().getAllErrors();
    	for (ObjectError error: errors) {
    		String validationError = error.getDefaultMessage();
    		validationErrors.add(validationError);
    	}
    	return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);
    }

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleUnexpectedException(Exception exception) {
		Throwable rootCause = ExceptionUtils.getRootCause(exception);
		if (rootCause instanceof SQLException) {
			return handleSQLException(rootCause);
		}
		return new ResponseEntity<>("Occur unexpected exception: " + exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	public ResponseEntity<String> handleSQLException(Throwable sqlException) {
		return new ResponseEntity<>("Occur SQL exception: " + sqlException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
