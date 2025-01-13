package com.itsecasia.example.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.itsecasia.example.dto.responses.MessageResponse;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class ControllerAdvisor {

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	MessageResponse onConstraintValidationException(ConstraintViolationException e) {

		List<ConstraintViolation<?>> violations = new ArrayList<>(e.getConstraintViolations());
		String errorMsg = violations.get(0).getMessage();

		return new MessageResponse(false, errorMsg);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	MessageResponse onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		List<FieldError> violations = new ArrayList<>(e.getBindingResult().getFieldErrors());
		String errorMessage = violations.get(0).getDefaultMessage();

		return new MessageResponse(false, errorMessage);
	}

}
