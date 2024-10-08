package com.rakbank.accountmanagement.exception;

import com.rakbank.accountmanagement.util.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException ex, WebRequest request) {
		return new ResponseEntity<>(ResponseUtil.error(ex.getMessage()), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, Object>> handleGlobalException(Exception ex, WebRequest request) {
		return new ResponseEntity<>(ResponseUtil.error(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        String errorMessage = "Data integrity violation occurred.";
        
        // You might want to extract more specific details from the exception if available
        if (ex.getMessage().contains("Duplicate entry")) {
            errorMessage = "A record with this email already exists. Please use a different email address.";
        }
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseUtil.error(errorMessage));
    }

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex,
			WebRequest request) {
		Map<String, String> errors = new HashMap<>();

		// Extract field-specific errors
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});

		// Create the response body with a general error message
		Map<String, Object> response = ResponseUtil
				.error("Validation failed. Please correct the errors and try again.");
		response.put("errors", errors);

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(EmailAlreadyExistsException.class)
	public ResponseEntity<Map<String, Object>> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex,
			WebRequest request) {
		return new ResponseEntity<>(ResponseUtil.error(ex.getMessage()), HttpStatus.CONFLICT); // 409 Conflict
	}
}
