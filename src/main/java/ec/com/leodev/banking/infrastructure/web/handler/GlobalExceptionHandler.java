package ec.com.leodev.banking.infrastructure.web.handler;

import ec.com.leodev.banking.domain.exception.AccountNotFoundException;
import ec.com.leodev.banking.domain.exception.InsufficientBalanceException;
import ec.com.leodev.banking.domain.exception.NegativeMoneyException;
import ec.com.leodev.banking.infrastructure.web.dto.ErrorMessage;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(AccountNotFoundException.class)
  public ResponseEntity<ErrorMessage> handleAccountNotFound(AccountNotFoundException ex, HttpServletRequest request){
    return build(HttpStatus.NOT_FOUND, ex.getMessage(), request, List.of());
  }

  @ExceptionHandler({InsufficientBalanceException.class, NegativeMoneyException.class})
  public ResponseEntity<ErrorMessage> handleBusinessRuleException(
      RuntimeException ex, HttpServletRequest request
  ){
    return build(HttpStatus.BAD_REQUEST, ex.getMessage(), request, List.of());
  }

  // Dos operaciones concurrentes sobre la misma cuenta
  @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
  public ResponseEntity<ErrorMessage> handleOptimisticLock(
      ObjectOptimisticLockingFailureException ex, HttpServletRequest request
  ){
    return build(HttpStatus.CONFLICT, "Account was modified by another operation, retry", request, List.of());
  }

  // Errores de validacion de entrada
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public  ResponseEntity<ErrorMessage> handleValidationErrors(
      MethodArgumentNotValidException ex, HttpServletRequest request
  ){
    List<String> details = ex.getBindingResult().getFieldErrors().stream().map(this::formatFieldError).toList();

    return build(HttpStatus.BAD_REQUEST, "Validation failed for request", request, details);
  }

  // JSON mal formado o con tipos incorrectos
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ErrorMessage> handleNotReadable(
      HttpMessageNotReadableException ex, HttpServletRequest request
  ){
    return build(HttpStatus.BAD_REQUEST, "Malformed JSON request", request, List.of());
  }

  private String formatFieldError(FieldError error){
    return "%s: %s".formatted(
        error.getField(),
        error.getDefaultMessage() !=null ? error.getDefaultMessage() : "invalid value"
    );
  }

  // error 500 (las excepciones de Spring MVC, como 404 o 405, conservan su propio código)
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorMessage> handleErrorGeneric(
      Exception ex, HttpServletRequest request
  ){
    if (ex instanceof ErrorResponse errorResponse) {
      HttpStatus status = HttpStatus.valueOf(errorResponse.getStatusCode().value());
      return build(status, status.getReasonPhrase(), request, List.of());
    }
    log.error("Unexpected error on {}", request.getRequestURI(), ex);
    return build(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected Error", request,
        List.of(ex.getClass().getSimpleName()));
  }

  private ResponseEntity<ErrorMessage> build(
      HttpStatus status, String message, HttpServletRequest request, List<String> details
  ){
    ErrorMessage errorMessage = new ErrorMessage(
        status.value(),
        status.getReasonPhrase(),
        message,
        request.getRequestURI(),
        Instant.now(),
        details
    );
    return ResponseEntity.status(status).body(errorMessage);
  }

}
