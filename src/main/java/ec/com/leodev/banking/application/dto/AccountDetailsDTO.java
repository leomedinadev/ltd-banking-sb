package ec.com.leodev.banking.application.dto;

import java.math.BigDecimal;
import java.util.List;

public record AccountDetailsDTO(
    String id,
    String customerId,
    BigDecimal balance,
    List<TransactionDTO> transactions
) {

}
