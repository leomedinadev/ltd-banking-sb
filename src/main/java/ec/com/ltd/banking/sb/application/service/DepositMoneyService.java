package ec.com.ltd.banking.sb.application.service;

import ec.com.ltd.banking.sb.application.dto.AccountDetailsDTO;
import ec.com.ltd.banking.sb.application.dto.DepositMoneyCommand;
import ec.com.ltd.banking.sb.application.dto.MapToAccountDetailsDto;
import ec.com.ltd.banking.sb.application.port.IDepositMoneyUseCase;
import ec.com.ltd.banking.sb.domain.exception.AccountNotFoundException;
import ec.com.ltd.banking.sb.domain.model.Account;
import ec.com.ltd.banking.sb.domain.model.AccountId;
import ec.com.ltd.banking.sb.domain.model.Money;
import ec.com.ltd.banking.sb.domain.port.IAccountRepository;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class DepositMoneyService implements IDepositMoneyUseCase {

  private final IAccountRepository accountRepository;

  public DepositMoneyService(IAccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }
  @Override
  @Transactional
  public AccountDetailsDTO deposit(DepositMoneyCommand command) {
    var accountId = new AccountId(command.accountId());

    var account = accountRepository.findById(accountId)
        .orElseThrow( ()-> new AccountNotFoundException(command.accountId()));

    account.deposit(Money.of(command.amount()));

    accountRepository.save(account);

    return MapToAccountDetailsDto.from(account);
  }
}
