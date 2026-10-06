package ec.com.leodev.banking.application.service;

import ec.com.leodev.banking.application.dto.AccountDetailsDTO;
import ec.com.leodev.banking.application.dto.MapToAccountDetailsDto;
import ec.com.leodev.banking.application.dto.WithdrawMoneyCommand;
import ec.com.leodev.banking.application.port.IWithdrawMoneyUseCase;
import ec.com.leodev.banking.domain.exception.AccountNotFoundException;
import ec.com.leodev.banking.domain.model.AccountId;
import ec.com.leodev.banking.domain.model.Money;
import ec.com.leodev.banking.domain.port.IAccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class WithdrawMoneyService implements IWithdrawMoneyUseCase {

  private final IAccountRepository accountRepository;

  public WithdrawMoneyService(IAccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }


  @Override
  @Transactional
  public AccountDetailsDTO withdraw(WithdrawMoneyCommand command) {
    var accountId = new AccountId(command.accountId());

    var account = accountRepository.findById(accountId)
        .orElseThrow( ()-> new AccountNotFoundException(command.accountId()));

    account.withdraw(Money.from(command.amount()));
    accountRepository.save(account);

    return MapToAccountDetailsDto.from(account);
  }
}
