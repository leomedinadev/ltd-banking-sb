package ec.com.ltd.banking.sb.application.dto;

public record DepositMoneyCommand(
    String accountId,
    double amount
) {

}
