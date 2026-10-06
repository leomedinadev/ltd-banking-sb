package ec.com.leodev.banking.application.dto;

import ec.com.leodev.banking.domain.model.Account;
import ec.com.leodev.banking.domain.model.Transaction;
import java.util.List;

public class MapToAccountDetailsDto {

  public static AccountDetailsDTO from(Account account) {
    List<TransactionDTO> transactionDTOList = account.getTransactions().stream()
        .map(MapToAccountDetailsDto::mapTransaction)
        .toList();

    return new AccountDetailsDTO(
        account.getId().value(),
        account.getCustomerId(),
        account.getBalance().getAmount(),
        transactionDTOList);
  }

  private static TransactionDTO mapTransaction(Transaction transaction){
    return new TransactionDTO(
        transaction.getId(),
        transaction.getType().name(),
        transaction.getAmount().getAmount()
    );
  }
}
