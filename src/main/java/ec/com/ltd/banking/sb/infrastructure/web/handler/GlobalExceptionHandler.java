package ec.com.ltd.banking.sb.infrastructure.web.handler;

import ec.com.ltd.banking.sb.domain.exception.AccountNotFoundException;
import ec.com.ltd.banking.sb.domain.exception.InsufficientBalanceException;
import ec.com.ltd.banking.sb.infrastructure.web.dto.ErrorMessage;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(AccountNotFoundException.class)
  public ResponseEntity<ErrorMessage> handleAccountNotFound(AccountNotFoundException ex, HttpServletRequest request){
    HttpStatus status = HttpStatus.NOT_FOUND;
    ErrorMessage errorMessage = new ErrorMessage(
        status.value(),
        status.getReasonPhrase(),
        ex.getMessage(),
        request.getRequestURI(),
        Instant.now(),
        List.of()
    );
    return ResponseEntity.status(status).body(errorMessage);
  }

  @ExceptionHandler(InsufficientBalanceException.class)
  public ResponseEntity<ErrorMessage> handleInsufficenteBalanceException(
      InsufficientBalanceException ex, HttpServletRequest request
  ){
    HttpStatus status = HttpStatus.BAD_REQUEST;
    ErrorMessage errorMessage = new ErrorMessage(
        status.value(),
        status.getReasonPhrase(),
        ex.getMessage(),
        request.getRequestURI(),
        Instant.now(),
        List.of()
    );
    return ResponseEntity.status(status).body(errorMessage);
  }

  // Errores de validacion de entrada
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public  ResponseEntity<ErrorMessage> handleValidationErrors(
      MethodArgumentNotValidException ex, HttpServletRequest request
  ){
    HttpStatus status = HttpStatus.BAD_REQUEST;

    String message = "Validation failed for request";

    List<String> details = ex.getBindingResult().getFieldErrors().stream().map(this::formatFieldError).toList();

    ErrorMessage body = new ErrorMessage(
        status.value(),
        status.getReasonPhrase(),
        message,
        request.getRequestURI(),
        Instant.now(),
        details
    );

    return  ResponseEntity.status(status).body(body);
  }

  private String formatFieldError(FieldError error){
    return "%s: %s".formatted(
        error.getField(),
        error.getDefaultMessage() !=null ? error.getDefaultMessage() : "invalid value"
    );
  }

  // error 500
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorMessage> handleErrorGeneric(
      Exception ex, HttpServletRequest request
  ){
    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    ErrorMessage errorMessage = new ErrorMessage(
        status.value(),
        status.getReasonPhrase(),
        "Unexpected Error",
        request.getRequestURI(),
        Instant.now(),
        List.of(ex.getClass().getSimpleName())
    );
    return ResponseEntity.status(status).body(errorMessage);
  }

}
