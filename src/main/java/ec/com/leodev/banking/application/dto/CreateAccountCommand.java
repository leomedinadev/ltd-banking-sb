package ec.com.leodev.banking.application.dto;

import java.math.BigDecimal;

public record CreateAccountCommand(
    String customerId,
    BigDecimal initialBalance
) {

}
