package ec.com.leodev.banking.domain.exception;

public class NegativeMoneyException extends RuntimeException {

  public NegativeMoneyException(String message) {
    super(message);
  }
}
