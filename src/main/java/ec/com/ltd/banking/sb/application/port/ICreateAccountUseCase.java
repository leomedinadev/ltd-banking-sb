package ec.com.ltd.banking.sb.application.port;

import ec.com.ltd.banking.sb.application.dto.AccountDetailsDTO;
import ec.com.ltd.banking.sb.application.dto.CreateAccountCommand;
import ec.com.ltd.banking.sb.domain.model.Account;

public interface ICreateAccountUseCase {
  AccountDetailsDTO create(CreateAccountCommand command);
}
