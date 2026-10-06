package ec.com.leodev.banking.application.port;

import ec.com.leodev.banking.application.dto.AccountDetailsDTO;
import ec.com.leodev.banking.application.dto.WithdrawMoneyCommand;

public interface IWithdrawMoneyUseCase {
  AccountDetailsDTO withdraw(WithdrawMoneyCommand command);
}
