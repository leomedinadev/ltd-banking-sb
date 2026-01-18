package ec.com.ltd.banking.sb.infrastructure.web.dto;

import ec.com.ltd.banking.sb.application.dto.AccountDetailsDTO;
import java.util.List;

public record AccountResponse(
    String id,
    String customerId,
    double balance,
    List<TransactionSummary> transactions
) {

  public static AccountResponse from(AccountDetailsDTO dto) {
    List<TransactionSummary> transactionSummaryList = dto.transactions().stream()
        .map(t -> new TransactionSummary(t.id(), t.type(), t.amount()))
        .toList();

    return new AccountResponse(dto.id(), dto.customerId(), dto.balance(), transactionSummaryList);
  }
}
