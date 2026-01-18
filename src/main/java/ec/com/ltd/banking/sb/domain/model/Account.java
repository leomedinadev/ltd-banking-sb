package ec.com.ltd.banking.sb.domain.model;

import ec.com.ltd.banking.sb.domain.exception.InsufficientBalanceException;
import ec.com.ltd.banking.sb.domain.exception.NegativeMoneyException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import lombok.Getter;

public class Account {
  @Getter
  private final AccountId id;
  @Getter
  private final String customerId;
  @Getter
  private Money balance;
  private List<Transaction> transactions = new ArrayList<>();

  public Account(AccountId id, String customerId, Money initialBalance) {
    this.id = id;
    this.customerId = customerId;
    this.balance = initialBalance;
  }

  // deposit
  public void deposit(Money amount) {
    Objects.requireNonNull(amount, "Deposit amount cannot be null");
    if (amount.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
      throw new NegativeMoneyException("Deposit amount must be greater than zero");
    }
    this.balance = this.balance.add(amount);
    this.transactions.add(new Transaction(TransactionType.DEPOSIT, amount));
  }

  // retiro
  public void withdraw(Money amount) {
    Objects.requireNonNull(amount, "Withdraw amount cannot be null");
    if (amount.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
      throw new NegativeMoneyException("Withdraw amount must be greater than zero");
    }
    Money newAmount = this.balance.subtract(amount);
    if (newAmount.isNegative()) {
      throw new InsufficientBalanceException("Insufficient funds.");
    }
    this.transactions.add(new Transaction(TransactionType.WITHDRAWAL, amount));
  }

  public List<Transaction> getTransactions() {
    return this.transactions;
  }

}
