package ec.com.leodev.banking.infrastructure.mapper;

import ec.com.leodev.banking.domain.model.Account;
import ec.com.leodev.banking.domain.model.AccountId;
import ec.com.leodev.banking.domain.model.Money;
import ec.com.leodev.banking.domain.model.Transaction;
import ec.com.leodev.banking.domain.model.TransactionType;
import ec.com.leodev.banking.infrastructure.persistence.AccountEntity;
import ec.com.leodev.banking.infrastructure.persistence.TransactionEntity;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountMapper {
  // Mapeo entidad JPA -> dominio
  public static Account toDomain(AccountEntity entity){
    Account account = new Account(
        new AccountId(entity.getId()),
        entity.getCustomerId(),
        Money.from(entity.getBalance())
    );

    entity.getTransactions().forEach(
        te->{
          Transaction tx = new Transaction(
              te.getId(),
              TransactionType.valueOf(te.getType()),
              Money.from(te.getAmount()),
              te.getCreatedAt()
          );
          account.getTransactions().add(tx);
        }
    );

    return account;

  }

  // Mapeo dominio -> entidad JPA (nueva o ya gestionada): solo agrega las transacciones nuevas
  public static void updateEntity(Account account, AccountEntity entity){
    entity.setId(account.getId().value());
    entity.setCustomerId(account.getCustomerId());
    entity.setBalance(account.getBalance().getAmount());

    Set<String> persistedIds = entity.getTransactions().stream()
        .map(TransactionEntity::getId)
        .collect(Collectors.toSet());

    for(Transaction t : account.getTransactions()){
      if (persistedIds.contains(t.getId())) {
        continue;
      }
      TransactionEntity te = new TransactionEntity();
      te.setId(t.getId());
      te.setType(t.getType().name());
      te.setAmount(t.getAmount().getAmount());
      te.setCreatedAt(t.getCreatedAt());
      entity.addTransaction(te);
    }
  }
}
