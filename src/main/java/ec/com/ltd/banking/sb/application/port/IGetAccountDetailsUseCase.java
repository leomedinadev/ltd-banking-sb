package ec.com.ltd.banking.sb.application.port;

import ec.com.ltd.banking.sb.application.dto.AccountDetailsDTO;

public interface IGetAccountDetailsUseCase {
  AccountDetailsDTO getById(String id);
}
