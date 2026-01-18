package ec.com.ltd.banking.sb.infrastructure.adapter;

import ec.com.ltd.banking.sb.domain.model.Account;
import ec.com.ltd.banking.sb.domain.model.AccountId;
import ec.com.ltd.banking.sb.domain.port.IAccountRepository;
import ec.com.ltd.banking.sb.infrastructure.mapper.AccountMapper;
import ec.com.ltd.banking.sb.infrastructure.persistence.AccountEntity;
import ec.com.ltd.banking.sb.infrastructure.repository.JPAAccountRepository;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AccountRepositoryAdapter implements IAccountRepository {

  private final JPAAccountRepository jpaAccountRepository;


  @Override
  public Account save(Account account) {
    AccountEntity accountEntity = AccountMapper.toEntity(account);
    AccountEntity savedAccount = jpaAccountRepository.save(accountEntity);
    return AccountMapper.toDomain(savedAccount);
  }

  @Override
  public Optional<Account> findById(AccountId id) {
    return jpaAccountRepository.findById(id.value()).map(AccountMapper::toDomain);
  }
}
