package com.rubyplayapp.exceptions.handler;

import com.rubyplayapp.exceptions.EntityNotFoundException;
import com.rubyplayapp.exceptions.NightClubException;
import com.rubyplayapp.exceptions.VisitorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({VisitorException.class, NightClubException.class})
	public ExceptionResponseBody businessExceptionHandler(final RuntimeException e) {
		LOGGER.error("Error: ", e);
		return new ExceptionResponseBody(e.getMessage());
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(EntityNotFoundException.class)
	public ExceptionResponseBody entityNotFoundExceptionHandler(final EntityNotFoundException e) {
		LOGGER.error("Error: ", e);
		return new ExceptionResponseBody(e.getMessage());
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public ExceptionResponseBody exceptionHandler(final Exception e) {
		LOGGER.error("Error: ", e);
		return new ExceptionResponseBody(e.getMessage());
	}
}
