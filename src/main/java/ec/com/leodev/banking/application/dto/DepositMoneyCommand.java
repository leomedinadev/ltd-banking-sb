package ec.com.leodev.banking.application.dto;

import java.math.BigDecimal;

public record DepositMoneyCommand(
    String accountId,
    BigDecimal amount
) {

}
