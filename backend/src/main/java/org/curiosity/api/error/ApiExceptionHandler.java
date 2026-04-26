package org.curiosity.api.error;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handleNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
        return createProblem(HttpStatus.NOT_FOUND, "Resource not found",
                ex.getMessage(), "resource-not-found", request);
    }

    @ExceptionHandler(BadRequestException.class)
    public ProblemDetail handleBadRequest(BadRequestException ex, HttpServletRequest request) {
        return createProblem(HttpStatus.BAD_REQUEST, "Bad request", ex.getMessage(),
                "bad-request", request);
    }

    @ExceptionHandler(ConflictException.class)
    public ProblemDetail handleConflict(ConflictException ex, HttpServletRequest request) {
        return createProblem(
                HttpStatus.CONFLICT,
                "Conflict",
                ex.getMessage(),
                "conflict",
                request
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        ProblemDetail problem = createProblem(
                HttpStatus.BAD_REQUEST,
                "Validation failed",
                "One or more fields are invalid",
                "validation-failed",
                request
        );

        Map<String, String> errors = new LinkedHashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        problem.setProperty("errors", errors);
        return problem;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleUnknown(Exception ex, HttpServletRequest request) {
        LOGGER.error("Unhandled exception for request {}", request.getRequestURI(), ex);

        return createProblem(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal server error",
                "An unexpected error occurred",
                "internal-server-error",
                request
        );
    }

    private ProblemDetail createProblem(HttpStatus status,
                                        String title, String detail, String code,
                                        HttpServletRequest request) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(status, detail);
        problem.setTitle(title);
        problem.setType(URI.create("https://api.iudicium.local/problems/" + code));
        problem.setInstance(URI.create(request.getRequestURI()));
        problem.setProperty("code", code);
        return problem;
    }
}
