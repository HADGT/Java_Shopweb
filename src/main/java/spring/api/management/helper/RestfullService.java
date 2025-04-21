package spring.api.management.helper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class RestfullService {
    protected <T> ResponseEntity<T> createResponse(T data, HttpStatus status) {
        return new ResponseEntity<>(data, status);
    }

    protected ResponseEntity<String> createErrorResponse(String message, HttpStatus status) {
        return new ResponseEntity<>(message, status);
    }
}
