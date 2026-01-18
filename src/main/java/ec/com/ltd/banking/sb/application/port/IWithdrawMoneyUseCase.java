package ec.com.ltd.banking.sb.application.port;

import ec.com.ltd.banking.sb.application.dto.AccountDetailsDTO;
import ec.com.ltd.banking.sb.application.dto.WithdrawMoneyCommand;

public interface IWithdrawMoneyUseCase {
  AccountDetailsDTO withdraw(WithdrawMoneyCommand command);
}
