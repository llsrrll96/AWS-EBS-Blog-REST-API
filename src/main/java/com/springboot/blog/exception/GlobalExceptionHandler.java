package com.springboot.blog.exception;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.springboot.blog.payload.ErrorDetails;

// To handle exceptions globally
// Will available for an auto detaction while component scanning.
@ControllerAdvice
public class GlobalExceptionHandler 
{
	// handle specific exceptions
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest webRequest)
	{
		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),webRequest.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(BlogAPIException.class)
	public ResponseEntity<ErrorDetails> handleBlogAPIException(BlogAPIException exception, WebRequest webRequest)
	{
		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),webRequest.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}
	
	// global exceptions
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> handleGlobalException(Exception exception, WebRequest webRequest)
	{
		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),webRequest.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR	);	
	}
	
	//MethodArgumentNotValidException 
	@ExceptionHandler(MethodArgumentNotValidException .class)
	public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException  exception, WebRequest webRequest)
	{
		Map<String , String >errors= new HashMap<>();
		exception.getBindingResult().getAllErrors().forEach((error)->{
			String fieldName = ((FieldError) error).getField();
			String message = error.getDefaultMessage();
			errors.put(fieldName, message);
		});
		return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
	}
}
