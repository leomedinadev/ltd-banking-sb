package ec.com.leodev.banking.application.port;

import ec.com.leodev.banking.application.dto.AccountDetailsDTO;
import ec.com.leodev.banking.application.dto.CreateAccountCommand;
import ec.com.leodev.banking.domain.model.Account;

public interface ICreateAccountUseCase {
  AccountDetailsDTO create(CreateAccountCommand command);
}
