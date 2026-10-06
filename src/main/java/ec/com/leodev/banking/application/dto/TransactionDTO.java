package ec.com.leodev.banking.application.dto;

import java.math.BigDecimal;

public record TransactionDTO(
    String id,
    String type,
    BigDecimal amount
) {

}
