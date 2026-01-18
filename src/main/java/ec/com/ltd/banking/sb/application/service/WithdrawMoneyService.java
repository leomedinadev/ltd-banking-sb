package ec.com.ltd.banking.sb.application.service;

import ec.com.ltd.banking.sb.application.dto.AccountDetailsDTO;
import ec.com.ltd.banking.sb.application.dto.MapToAccountDetailsDto;
import ec.com.ltd.banking.sb.application.dto.WithdrawMoneyCommand;
import ec.com.ltd.banking.sb.application.port.IWithdrawMoneyUseCase;
import ec.com.ltd.banking.sb.domain.exception.AccountNotFoundException;
import ec.com.ltd.banking.sb.domain.model.AccountId;
import ec.com.ltd.banking.sb.domain.model.Money;
import ec.com.ltd.banking.sb.domain.port.IAccountRepository;
import org.springframework.stereotype.Service;

@Service
public class WithdrawMoneyService implements IWithdrawMoneyUseCase {

  private final IAccountRepository accountRepository;

  public WithdrawMoneyService(IAccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }


  @Override
  public AccountDetailsDTO withdraw(WithdrawMoneyCommand command) {
    var accountId = new AccountId(command.accountId());

    var account = accountRepository.findById(accountId)
        .orElseThrow( ()-> new AccountNotFoundException(command.accountId()));

    account.withdraw(Money.of(command.amount()));
    accountRepository.save(account);

    return MapToAccountDetailsDto.from(account);
  }
}
