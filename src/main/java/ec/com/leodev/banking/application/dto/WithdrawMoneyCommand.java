package ec.com.leodev.banking.application.dto;

import java.math.BigDecimal;

public record WithdrawMoneyCommand(
    String accountId,
    BigDecimal amount
) {

}
