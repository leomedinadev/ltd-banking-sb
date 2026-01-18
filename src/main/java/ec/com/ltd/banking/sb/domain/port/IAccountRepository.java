package ec.com.ltd.banking.sb.domain.port;

import ec.com.ltd.banking.sb.domain.model.Account;
import ec.com.ltd.banking.sb.domain.model.AccountId;
import java.util.Optional;

public interface IAccountRepository {

  Account save(Account account);
  Optional<Account> findById(AccountId id);

}
