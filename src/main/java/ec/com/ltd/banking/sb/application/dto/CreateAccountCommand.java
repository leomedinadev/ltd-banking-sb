package ec.com.ltd.banking.sb.application.dto;

public record CreateAccountCommand(
    String customerId,
    double initialBalance
) {

}
