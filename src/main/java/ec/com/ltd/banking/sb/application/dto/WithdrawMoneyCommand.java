package ec.com.ltd.banking.sb.application.dto;

public record WithdrawMoneyCommand(
    String accountId,
    double amount
) {

}
