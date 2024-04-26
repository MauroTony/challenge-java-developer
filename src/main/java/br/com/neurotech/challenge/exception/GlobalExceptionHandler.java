package br.com.neurotech.challenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<Object> handleClientNotFoundException(ClientNotFoundException ex) {
        BodyError errors = new BodyError();
        errors.setMessage("Client not found");
        errors.setDetails(ex.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CreditCheckException.class)
    public ResponseEntity<Object> handleCreditCheckException(CreditCheckException ex) {
        BodyError errors = new BodyError();
        errors.setMessage("Error checking credit");
        errors.setDetails(ex.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralException(Exception ex) {
        BodyError errors = new BodyError();
        errors.setMessage("An unexpected error occurred");
        errors.setDetails(ex.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BodyError> customHandleInvalidInput(MethodArgumentNotValidException ex) {
        BodyError errors = new BodyError();
        errors.setMessage("Invalid input data");
        errors.setDetails(ex.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getDefaultMessage())
                .collect(Collectors.joining(", ")));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
