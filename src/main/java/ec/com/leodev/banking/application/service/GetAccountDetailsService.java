package ec.com.leodev.banking.application.service;

import ec.com.leodev.banking.application.dto.AccountDetailsDTO;
import ec.com.leodev.banking.application.dto.MapToAccountDetailsDto;
import ec.com.leodev.banking.application.port.IGetAccountDetailsUseCase;
import ec.com.leodev.banking.domain.exception.AccountNotFoundException;
import ec.com.leodev.banking.domain.model.AccountId;
import ec.com.leodev.banking.domain.port.IAccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class GetAccountDetailsService implements IGetAccountDetailsUseCase {

  private final IAccountRepository accountRepository;

  public GetAccountDetailsService(IAccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }


  @Override
  @Transactional
  public AccountDetailsDTO getById(String accountId) {
    var id = new AccountId(accountId);
    var account = accountRepository.findById(id)
        .orElseThrow(()->new AccountNotFoundException(accountId));
    return MapToAccountDetailsDto.from(account);
  }
}
