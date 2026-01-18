package ec.com.ltd.banking.sb.domain.model;

import java.math.BigDecimal;

public class Money {

  private final BigDecimal amount;

  public Money(BigDecimal amount) {
    this.amount = amount;
  }

  public static Money of(double value) {
    return new Money(BigDecimal.valueOf(value));
  }

  public static Money from(BigDecimal value) {
    return new Money(value);
  }

  //Deposit
  public Money add(Money other) {
    return new Money(this.amount.add(other.amount));
  }

  // Withdraw
  public Money subtract(Money other) {
    return new Money(this.amount.subtract(other.amount));
  }

  public boolean isNegative() {
    return this.amount.compareTo(BigDecimal.ZERO) < 0;
  }

  public BigDecimal getAmount() {
    return amount;
  }
}
