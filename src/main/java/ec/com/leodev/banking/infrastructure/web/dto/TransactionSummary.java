package ec.com.leodev.banking.infrastructure.web.dto;

import java.math.BigDecimal;

public record TransactionSummary(
    String id,
    String type,
    BigDecimal amount
) {

}
