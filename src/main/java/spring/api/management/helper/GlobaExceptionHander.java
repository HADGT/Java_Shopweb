package spring.api.management.helper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobaExceptionHander {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handerValidExceptions(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMess = error.getDefaultMessage();
            errors.put(fieldName, errorMess);
        });
        return new ResponseEntity<>(errors, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
