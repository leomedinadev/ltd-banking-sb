package ec.com.ltd.banking.sb.application.port;

import ec.com.ltd.banking.sb.application.dto.AccountDetailsDTO;
import ec.com.ltd.banking.sb.application.dto.DepositMoneyCommand;

public interface IDepositMoneyUseCase {
  AccountDetailsDTO deposit(DepositMoneyCommand command);
}
