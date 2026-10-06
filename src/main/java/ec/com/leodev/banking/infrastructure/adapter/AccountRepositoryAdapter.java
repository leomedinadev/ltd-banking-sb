package ec.com.leodev.banking.infrastructure.adapter;

import ec.com.leodev.banking.domain.model.Account;
import ec.com.leodev.banking.domain.model.AccountId;
import ec.com.leodev.banking.domain.port.IAccountRepository;
import ec.com.leodev.banking.infrastructure.mapper.AccountMapper;
import ec.com.leodev.banking.infrastructure.persistence.AccountEntity;
import ec.com.leodev.banking.infrastructure.repository.JPAAccountRepository;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AccountRepositoryAdapter implements IAccountRepository {

  private final JPAAccountRepository jpaAccountRepository;


  @Override
  public Account save(Account account) {
    AccountEntity accountEntity = jpaAccountRepository.findById(account.getId().value())
        .orElseGet(AccountEntity::new);
    AccountMapper.updateEntity(account, accountEntity);
    AccountEntity savedAccount = jpaAccountRepository.save(accountEntity);
    return AccountMapper.toDomain(savedAccount);
  }

  @Override
  public Optional<Account> findById(AccountId id) {
    return jpaAccountRepository.findById(id.value()).map(AccountMapper::toDomain);
  }
}
