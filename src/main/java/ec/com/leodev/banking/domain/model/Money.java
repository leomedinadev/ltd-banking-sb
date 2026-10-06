package ec.com.leodev.banking.domain.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public final class Money {

  private static final int SCALE = 2;

  private final BigDecimal amount;

  public Money(BigDecimal amount) {
    Objects.requireNonNull(amount, "Amount cannot be null");
    this.amount = amount.setScale(SCALE, RoundingMode.HALF_EVEN);
  }

  public static Money of(String value) {
    return new Money(new BigDecimal(value));
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

  @Override
  public boolean equals(Object o) {
    return o instanceof Money other && this.amount.equals(other.amount);
  }

  @Override
  public int hashCode() {
    return amount.hashCode();
  }

  @Override
  public String toString() {
    return amount.toPlainString();
  }
}
