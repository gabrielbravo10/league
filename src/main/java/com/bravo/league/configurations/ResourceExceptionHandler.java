package com.bravo.league.configurations;

import com.bravo.league.controllers.dto.ErroDTO;
import com.bravo.league.exceptions.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ResourceExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ErroDTO> handle(MethodArgumentNotValidException exception) {
       List<ErroDTO> errors = new ArrayList<>();
       List<FieldError> validationErros = exception.getBindingResult().getFieldErrors();

       validationErros.forEach(error -> {
           String message = messageSource.getMessage(error, LocaleContextHolder.getLocale());
           ErroDTO erroDTO = new ErroDTO(message, error.getField());
           errors.add(erroDTO);
       });
       return errors;
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErroDTO handle(IllegalArgumentException exception) {
        String message = messageSource.getMessage(exception.getMessage(), null, LocaleContextHolder.getLocale());
        return new ErroDTO(message);
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(RecordNotFoundException.class)
    public ErroDTO handle(RecordNotFoundException exception) {
        String message = messageSource.getMessage(exception.getMessage(), null, LocaleContextHolder.getLocale());
        return new ErroDTO(message);
    }

}
