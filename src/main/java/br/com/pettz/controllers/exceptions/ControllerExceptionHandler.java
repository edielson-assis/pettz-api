package br.com.pettz.controllers.exceptions;

import java.time.Instant;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;

import br.com.pettz.services.exceptions.DataBaseException;
import br.com.pettz.services.exceptions.FileStorageException;
import br.com.pettz.services.exceptions.ObjectNotFoundException;
import br.com.pettz.services.exceptions.ValidationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class ControllerExceptionHandler {
    
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<StandardError> businessException(ValidationException exception, HttpServletRequest request) {
        String error = "Validation error";
        HttpStatus status = HttpStatus.CONFLICT;
        return ResponseEntity.status(status).body(errors(status, error, exception, request));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> validationError(MethodArgumentNotValidException exception, HttpServletRequest request) {
        String error = "Validation error";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(errors(status, error, exception, request));
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> resourceNotFound(ObjectNotFoundException exception, HttpServletRequest request) {
        String error = "Not found";
        HttpStatus status = HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status).body(errors(status, error, exception, request));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<StandardError> badRequest(HttpMessageNotReadableException exception, HttpServletRequest request) {
        String error = "Invalid request";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(errors(status, error, exception, request));
    }

    @ExceptionHandler(FileStorageException.class)
    public ResponseEntity<StandardError> badRequest(FileStorageException exception, HttpServletRequest request) {
        String error = "Invalid request";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(errors(status, error, exception, request));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<StandardError> badRequest(ConstraintViolationException exception, HttpServletRequest request) {
        String error = "Invalid request";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(errors(status, error, exception, request));
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<StandardError> jwtError(TokenExpiredException exception, HttpServletRequest request) {
        String error = "Access denied";
        HttpStatus status = HttpStatus.FORBIDDEN;
        return ResponseEntity.status(status).body(errors(status, error, exception, request));
    }

    @ExceptionHandler(JWTDecodeException.class)
    public ResponseEntity<StandardError> jwtError(JWTDecodeException exception, HttpServletRequest request) {
        String error = "Access denied";
        HttpStatus status = HttpStatus.FORBIDDEN;
        return ResponseEntity.status(status).body(errors(status, error, exception, request));
    }

    @ExceptionHandler(SignatureVerificationException.class)
    public ResponseEntity<StandardError> InvalidJwt(SignatureVerificationException exception, HttpServletRequest request) {
        String error = "Access denied";
        HttpStatus status = HttpStatus.FORBIDDEN;
        return ResponseEntity.status(status).body(errors(status, error, exception, request));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<StandardError> badCredentialsError(BadCredentialsException exception, HttpServletRequest request) {
        String error = "Invalid credentials";
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        return ResponseEntity.status(status).body(errors(status, error, exception, request));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<StandardError> accessDeniedError(AccessDeniedException exception, HttpServletRequest request) {
        String error = "Access denied";
        HttpStatus status = HttpStatus.FORBIDDEN;
        return ResponseEntity.status(status).body(errors(status, error, exception, request));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<StandardError> authenticationError(AuthenticationException exception, HttpServletRequest request) {
        String error = "Authentication failure";
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        return ResponseEntity.status(status).body(errors(status, error, exception, request));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<StandardError> databaseError(DataBaseException e, HttpServletRequest request) {
        String error = "Internal server error";
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(status).body(errors(status, error, e, request));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<StandardError> userNotFound(UsernameNotFoundException exception, HttpServletRequest request) {
        String error = "Not found";
        HttpStatus status = HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status).body(errors(status, error, exception, request));
    }

    // @ExceptionHandler(Exception.class)
    // public ResponseEntity<StandardError> databaseError(Exception exception, HttpServletRequest request) {
    //     String error = "Internal server error";
    //     HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    //     return ResponseEntity.status(status).body(errors(status, error, exception, request));
    // }

    private StandardError errors(HttpStatus status, String error, Exception message, HttpServletRequest request) {
        return new StandardError(Instant.now(), status.value(), error, message.getMessage(), request.getRequestURI());
    }
}