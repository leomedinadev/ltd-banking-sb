package ec.com.ltd.banking.sb.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ec.com.ltd.banking.sb.domain.exception.InsufficientBalanceException;
import org.junit.jupiter.api.Test;

public class AccountTest {

  /// AAA
  // arrange
  // act
  // assert
  @Test
  void should_deposit_money_to_account() {
    //arrange
    Account account = new Account(AccountId.newId(), "123", Money.of(100));

    //act
    account.deposit(Money.of(50));

    //assert
    assertEquals(Money.of(150).getAmount(), account.getBalance().getAmount());
    assertFalse(account.getTransactions().isEmpty());
    assertEquals(TransactionType.DEPOSIT, account.getTransactions().getFirst().getType());
  }

  @Test
  void should_not_allow_withdraw_when_insufficient_balance() {
    //arrange
    Account account = new Account(AccountId.newId(), "123", Money.of(100));
    //act
    InsufficientBalanceException ex = assertThrows(InsufficientBalanceException.class, () -> account.withdraw(Money.of(150)));
    //assert
    assertNotNull(ex.getMessage());
    assertEquals(Money.of(100).getAmount(), account.getBalance().getAmount());
    assertTrue(account.getTransactions().isEmpty());
  }
}
