package ec.com.leodev.banking.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ec.com.leodev.banking.domain.exception.InsufficientBalanceException;
import ec.com.leodev.banking.domain.exception.NegativeMoneyException;
import org.junit.jupiter.api.Test;

public class AccountTest {

  /// AAA
  // arrange
  // act
  // assert
  @Test
  void should_deposit_money_to_account() {
    //arrange
    Account account = new Account(AccountId.newId(), "123", Money.of("100"));

    //act
    account.deposit(Money.of("50"));

    //assert
    assertEquals(Money.of("150"), account.getBalance());
    assertFalse(account.getTransactions().isEmpty());
    assertEquals(TransactionType.DEPOSIT, account.getTransactions().getFirst().getType());
  }

  @Test
  void should_withdraw_money_from_account() {
    //arrange
    Account account = new Account(AccountId.newId(), "123", Money.of("100"));

    //act
    account.withdraw(Money.of("30.25"));

    //assert
    assertEquals(Money.of("69.75"), account.getBalance());
    assertEquals(1, account.getTransactions().size());
    assertEquals(TransactionType.WITHDRAWAL, account.getTransactions().getFirst().getType());
  }

  @Test
  void should_allow_withdraw_of_full_balance() {
    //arrange
    Account account = new Account(AccountId.newId(), "123", Money.of("100"));

    //act
    account.withdraw(Money.of("100"));

    //assert
    assertEquals(Money.of("0"), account.getBalance());
  }

  @Test
  void should_not_allow_withdraw_when_insufficient_balance() {
    //arrange
    Account account = new Account(AccountId.newId(), "123", Money.of("100"));
    //act
    InsufficientBalanceException ex = assertThrows(InsufficientBalanceException.class, () -> account.withdraw(Money.of("150")));
    //assert
    assertNotNull(ex.getMessage());
    assertEquals(Money.of("100"), account.getBalance());
    assertTrue(account.getTransactions().isEmpty());
  }

  @Test
  void should_not_allow_zero_or_negative_amounts() {
    //arrange
    Account account = new Account(AccountId.newId(), "123", Money.of("100"));
    //act + assert
    assertThrows(NegativeMoneyException.class, () -> account.withdraw(Money.of("0")));
    assertThrows(NegativeMoneyException.class, () -> account.deposit(Money.of("-5")));
    assertEquals(Money.of("100"), account.getBalance());
    assertTrue(account.getTransactions().isEmpty());
  }

  @Test
  void should_keep_exact_decimals() {
    //arrange
    Account account = new Account(AccountId.newId(), "123", Money.of("0.10"));

    //act
    account.deposit(Money.of("0.20"));

    //assert: con double esto daba 0.30000000000000004
    assertEquals(Money.of("0.30"), account.getBalance());
  }
}
