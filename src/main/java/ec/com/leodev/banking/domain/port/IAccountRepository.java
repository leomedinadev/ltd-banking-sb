package ec.com.leodev.banking.domain.port;

import ec.com.leodev.banking.domain.model.Account;
import ec.com.leodev.banking.domain.model.AccountId;
import java.util.Optional;

public interface IAccountRepository {

  Account save(Account account);
  Optional<Account> findById(AccountId id);

}
