package ec.com.ltd.banking.sb.application.dto;

import java.util.List;

public record AccountDetailsDTO(
    String id,
    String customerId,
    double balance,
    List<TransactionDTO> transactions
) {

}
