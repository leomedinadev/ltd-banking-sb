package ec.com.leodev.banking.application.port;

import ec.com.leodev.banking.application.dto.AccountDetailsDTO;

public interface IGetAccountDetailsUseCase {
  AccountDetailsDTO getById(String id);
}
