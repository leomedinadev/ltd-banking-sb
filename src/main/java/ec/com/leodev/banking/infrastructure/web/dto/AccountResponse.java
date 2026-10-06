package ec.com.leodev.banking.infrastructure.web.dto;

import ec.com.leodev.banking.application.dto.AccountDetailsDTO;
import java.math.BigDecimal;
import java.util.List;

public record AccountResponse(
    String id,
    String customerId,
    BigDecimal balance,
    List<TransactionSummary> transactions
) {

  public static AccountResponse from(AccountDetailsDTO dto) {
    List<TransactionSummary> transactionSummaryList = dto.transactions().stream()
        .map(t -> new TransactionSummary(t.id(), t.type(), t.amount()))
        .toList();

    return new AccountResponse(dto.id(), dto.customerId(), dto.balance(), transactionSummaryList);
  }
}
