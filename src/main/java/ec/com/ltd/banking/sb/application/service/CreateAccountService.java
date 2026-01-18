package ec.com.ltd.banking.sb.application.service;

import ec.com.ltd.banking.sb.application.dto.AccountDetailsDTO;
import ec.com.ltd.banking.sb.application.dto.CreateAccountCommand;
import ec.com.ltd.banking.sb.application.dto.MapToAccountDetailsDto;
import ec.com.ltd.banking.sb.application.port.ICreateAccountUseCase;
import ec.com.ltd.banking.sb.domain.model.Account;
import ec.com.ltd.banking.sb.domain.model.AccountId;
import ec.com.ltd.banking.sb.domain.model.Money;
import ec.com.ltd.banking.sb.domain.port.IAccountRepository;
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
        Money.of(command.initialBalance())
    );
    Account savedAccount = accountRepository.save(account);
    return MapToAccountDetailsDto.from(savedAccount);
  }
}
