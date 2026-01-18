package ec.com.ltd.banking.sb.application.dto;

import ec.com.ltd.banking.sb.domain.model.Account;
import ec.com.ltd.banking.sb.domain.model.Transaction;
import java.util.List;

public class MapToAccountDetailsDto {

  public static AccountDetailsDTO from(Account account) {
    List<TransactionDTO> transactionDTOList = account.getTransactions().stream()
        .map(MapToAccountDetailsDto::mapTransaction)
        .toList();

    return new AccountDetailsDTO(
        account.getId().value(),
        account.getCustomerId(),
        account.getBalance().getAmount().doubleValue(),
        transactionDTOList);
  }

  private static TransactionDTO mapTransaction(Transaction transaction){
    return new TransactionDTO(
        transaction.getId(),
        transaction.getType().name(),
        transaction.getAmount().getAmount().doubleValue()
    );
  }
}
