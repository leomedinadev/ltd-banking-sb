package ec.com.ltd.banking.sb.application.service;

import ec.com.ltd.banking.sb.application.dto.AccountDetailsDTO;
import ec.com.ltd.banking.sb.application.dto.MapToAccountDetailsDto;
import ec.com.ltd.banking.sb.application.port.IGetAccountDetailsUseCase;
import ec.com.ltd.banking.sb.domain.exception.AccountNotFoundException;
import ec.com.ltd.banking.sb.domain.model.AccountId;
import ec.com.ltd.banking.sb.domain.port.IAccountRepository;
import org.springframework.stereotype.Service;

@Service
public class GetAccountDetailsService implements IGetAccountDetailsUseCase {

  private final IAccountRepository accountRepository;

  public GetAccountDetailsService(IAccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }


  @Override
  public AccountDetailsDTO getById(String accountId) {
    var id = new AccountId(accountId);
    var account = accountRepository.findById(id)
        .orElseThrow(()->new AccountNotFoundException(accountId));
    return MapToAccountDetailsDto.from(account);
  }
}
