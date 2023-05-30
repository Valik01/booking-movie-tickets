package com.issoft.cinemaapplication.exception.handler;

import com.issoft.cinemaapplication.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ControllerAdvice
public class ResponseExceptionHandler {

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse entityHandleException(final EntityNotFoundException exception) {
        final String devMessage = exception.getMessage();
        final String userMessage = String.format("%s The entity you are trying to refer to does not exist on the system.", exception.getMessage());

        return new ErrorResponse(devMessage, userMessage);
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse userHandleException(final HttpMessageNotReadableException exception) {
        final String devMessage = exception.getMessage();
        final String userMessage = "The specified system role does not exist. The value of this field can be USER or ADMIN.";

        return new ErrorResponse(devMessage, userMessage);
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse userHandleException(final MethodArgumentNotValidException exception) {
        final String devMessage = exception.getMessage();
        final List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        final StringBuilder userMessage = new StringBuilder();
        fieldErrors.forEach(fieldError -> userMessage.append(fieldError.getDefaultMessage()));

        return new ErrorResponse(devMessage, userMessage.toString());
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse userHandleException(final MostActiveUserNotFoundException exception) {
        final String devMessage = exception.getMessage();
        final String userMessage = "None of the users have yet bought tickets for the sessions that take place in this hall.";

        return new ErrorResponse(devMessage, userMessage);
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse userHandleException(final ForbiddenException exception) {
        final String devMessage = exception.getMessage();
        final String userMessage = exception.getMessage();

        return new ErrorResponse(devMessage, userMessage);
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse ticketHandleException(final TicketCannotUpdateException exception) {
        final String devMessage = exception.getMessage();
        final String userMessage = String.format("%s It is possible to update tickets that have not yet been purchased.", exception.getMessage());

        return new ErrorResponse(devMessage, userMessage);
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse ticketHandleException(final TicketCannotBuyException exception) {
        final String devMessage = exception.getMessage();
        final String userMessage = exception.getMessage();

        return new ErrorResponse(devMessage, userMessage);
    }
}
