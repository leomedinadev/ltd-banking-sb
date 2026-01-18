package ec.com.ltd.banking.sb.infrastructure.mapper;

import ec.com.ltd.banking.sb.domain.model.Account;
import ec.com.ltd.banking.sb.domain.model.AccountId;
import ec.com.ltd.banking.sb.domain.model.Money;
import ec.com.ltd.banking.sb.domain.model.Transaction;
import ec.com.ltd.banking.sb.domain.model.TransactionType;
import ec.com.ltd.banking.sb.infrastructure.persistence.AccountEntity;
import ec.com.ltd.banking.sb.infrastructure.persistence.TransactionEntity;

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
              TransactionType.valueOf(te.getType()),
              Money.from(te.getAmount())
          );
          account.getTransactions().add(tx);
        }
    );

    return account;

  }

  //Mapeo de una entidad -> JPA
  public  static AccountEntity toEntity(Account account){
    AccountEntity entity = new AccountEntity();
    entity.setId(account.getId().value());
    entity.setCustomerId(account.getCustomerId());
    entity.setBalance(account.getBalance().getAmount());

    entity.getTransactions().clear();
    for(Transaction t : account.getTransactions()){
      TransactionEntity te = new TransactionEntity();
      te.setId(t.getId());
      te.setType(t.getType().name());
      te.setAmount(t.getAmount().getAmount());
      entity.addTransaction(te);
    }
    return entity;
  }
}
