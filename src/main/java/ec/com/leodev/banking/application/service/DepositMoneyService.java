package ec.com.leodev.banking.application.service;

import ec.com.leodev.banking.application.dto.AccountDetailsDTO;
import ec.com.leodev.banking.application.dto.DepositMoneyCommand;
import ec.com.leodev.banking.application.dto.MapToAccountDetailsDto;
import ec.com.leodev.banking.application.port.IDepositMoneyUseCase;
import ec.com.leodev.banking.domain.exception.AccountNotFoundException;
import ec.com.leodev.banking.domain.model.Account;
import ec.com.leodev.banking.domain.model.AccountId;
import ec.com.leodev.banking.domain.model.Money;
import ec.com.leodev.banking.domain.port.IAccountRepository;

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

    account.deposit(Money.from(command.amount()));

    accountRepository.save(account);

    return MapToAccountDetailsDto.from(account);
  }
}
