package ec.com.leodev.banking.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ec.com.leodev.banking.application.dto.AccountDetailsDTO;
import ec.com.leodev.banking.application.dto.CreateAccountCommand;
import ec.com.leodev.banking.application.dto.DepositMoneyCommand;
import ec.com.leodev.banking.application.dto.TransactionDTO;
import ec.com.leodev.banking.application.dto.WithdrawMoneyCommand;
import ec.com.leodev.banking.application.port.ICreateAccountUseCase;
import ec.com.leodev.banking.application.port.IDepositMoneyUseCase;
import ec.com.leodev.banking.application.port.IGetAccountDetailsUseCase;
import ec.com.leodev.banking.application.port.IWithdrawMoneyUseCase;
import ec.com.leodev.banking.domain.exception.AccountNotFoundException;
import ec.com.leodev.banking.domain.exception.InsufficientBalanceException;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AccountUseCasesIntegrationTest {

  @Autowired
  private ICreateAccountUseCase createAccountUseCase;
  @Autowired
  private IDepositMoneyUseCase depositMoneyUseCase;
  @Autowired
  private IWithdrawMoneyUseCase withdrawMoneyUseCase;
  @Autowired
  private IGetAccountDetailsUseCase getAccountDetailsUseCase;

  @Test
  void should_persist_balance_after_deposit_and_withdraw() {
    //arrange
    String id = createAccountUseCase.create(new CreateAccountCommand("123", new BigDecimal("100.00"))).id();

    //act
    depositMoneyUseCase.deposit(new DepositMoneyCommand(id, new BigDecimal("50.50")));
    withdrawMoneyUseCase.withdraw(new WithdrawMoneyCommand(id, new BigDecimal("30.25")));

    //assert
    AccountDetailsDTO account = getAccountDetailsUseCase.getById(id);
    assertEquals(new BigDecimal("120.25"), account.balance());
    assertEquals(List.of("DEPOSIT", "WITHDRAWAL"),
        account.transactions().stream().map(TransactionDTO::type).toList());
  }

  @Test
  void should_keep_transaction_ids_between_operations() {
    //arrange
    String id = createAccountUseCase.create(new CreateAccountCommand("123", new BigDecimal("100.00"))).id();
    depositMoneyUseCase.deposit(new DepositMoneyCommand(id, new BigDecimal("10.00")));
    String firstTransactionId = getAccountDetailsUseCase.getById(id).transactions().getFirst().id();

    //act
    withdrawMoneyUseCase.withdraw(new WithdrawMoneyCommand(id, new BigDecimal("5.00")));

    //assert
    AccountDetailsDTO account = getAccountDetailsUseCase.getById(id);
    assertEquals(2, account.transactions().size());
    assertEquals(firstTransactionId, account.transactions().getFirst().id());
  }

  @Test
  void should_not_change_balance_when_insufficient_funds() {
    //arrange
    String id = createAccountUseCase.create(new CreateAccountCommand("123", new BigDecimal("100.00"))).id();

    //act
    assertThrows(InsufficientBalanceException.class,
        () -> withdrawMoneyUseCase.withdraw(new WithdrawMoneyCommand(id, new BigDecimal("100.01"))));

    //assert
    AccountDetailsDTO account = getAccountDetailsUseCase.getById(id);
    assertEquals(new BigDecimal("100.00"), account.balance());
    assertEquals(0, account.transactions().size());
  }

  @Test
  void should_fail_when_account_does_not_exist() {
    assertThrows(AccountNotFoundException.class,
        () -> withdrawMoneyUseCase.withdraw(new WithdrawMoneyCommand("no-existe", new BigDecimal("1.00"))));
  }
}
