package org.sid.exception;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

	@ExceptionHandler(ResultNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public @ResponseBody ExceptionReponse ResourceNotFound(final ResultNotFoundException exception,
			final HttpServletRequest request) {

		ExceptionReponse error = new ExceptionReponse();
		error.setMessage(exception.getMessage());
		error.setDate(new Date());
		error.setHttpCodeMessage(request.getRequestURI());
		error.setStatus(415);
		return error;
	}

	@ExceptionHandler(EntityAlreadyExistException.class)

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody ExceptionReponse EntityAlreadyExistException(final EntityAlreadyExistException exception,
			final HttpServletRequest request) {

		ExceptionReponse error = new ExceptionReponse();
		error.setMessage(exception.getMessage());
		error.setDate(new Date());
		error.setHttpCodeMessage(request.getRequestURI());
		error.setStatus(420);
		return error;
	}

	@ExceptionHandler(ForbiddenException.class)

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody ExceptionReponse ForbiddenException(final ForbiddenException exception,
			final HttpServletRequest request) {

		ExceptionReponse error = new ExceptionReponse();
		error.setMessage(exception.getMessage());
		error.setDate(new Date());
		error.setHttpCodeMessage(request.getRequestURI());
		error.setStatus(425);
		return error;
	}

}
