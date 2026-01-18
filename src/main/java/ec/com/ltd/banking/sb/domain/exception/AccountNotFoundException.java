package ec.com.ltd.banking.sb.domain.exception;

public class AccountNotFoundException extends RuntimeException {

  public AccountNotFoundException(String id) {
    super("Account not found: " + id);
  }
}
