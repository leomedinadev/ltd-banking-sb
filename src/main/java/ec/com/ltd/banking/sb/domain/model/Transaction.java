package ec.com.ltd.banking.sb.domain.model;

import java.time.Instant;
import java.util.UUID;
import lombok.Getter;

public class Transaction {

  @Getter
  private final String id;
  @Getter
  private final TransactionType type;
  @Getter
  private final Money amount;
  private final Instant createdAt;

  public Transaction(TransactionType type, Money amount) {
    this.id = UUID.randomUUID().toString();
    this.type = type;
    this.amount = amount;
    this.createdAt = Instant.now();
  }

}
