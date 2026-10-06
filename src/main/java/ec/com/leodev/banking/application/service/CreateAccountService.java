package ec.com.leodev.banking.application.service;

import ec.com.leodev.banking.application.dto.AccountDetailsDTO;
import ec.com.leodev.banking.application.dto.CreateAccountCommand;
import ec.com.leodev.banking.application.dto.MapToAccountDetailsDto;
import ec.com.leodev.banking.application.port.ICreateAccountUseCase;
import ec.com.leodev.banking.domain.model.Account;
import ec.com.leodev.banking.domain.model.AccountId;
import ec.com.leodev.banking.domain.model.Money;
import ec.com.leodev.banking.domain.port.IAccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CreateAccountService implements ICreateAccountUseCase {

  private final IAccountRepository accountRepository;

  public CreateAccountService(IAccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }


  @Override
  @Transactional
  public AccountDetailsDTO create(CreateAccountCommand command) {
    Account account = new Account(
        AccountId.newId(),
        command.customerId(),
        Money.from(command.initialBalance())
    );
    Account savedAccount = accountRepository.save(account);
    return MapToAccountDetailsDto.from(savedAccount);
  }
}
