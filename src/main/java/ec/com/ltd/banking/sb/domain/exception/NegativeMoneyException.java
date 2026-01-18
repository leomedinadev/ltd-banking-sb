package ec.com.ltd.banking.sb.domain.exception;

public class NegativeMoneyException extends RuntimeException {

  public NegativeMoneyException(String message) {
    super(message);
  }
}
