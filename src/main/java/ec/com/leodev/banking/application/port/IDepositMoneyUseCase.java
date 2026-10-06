package ec.com.leodev.banking.application.port;

import ec.com.leodev.banking.application.dto.AccountDetailsDTO;
import ec.com.leodev.banking.application.dto.DepositMoneyCommand;

public interface IDepositMoneyUseCase {
  AccountDetailsDTO deposit(DepositMoneyCommand command);
}
