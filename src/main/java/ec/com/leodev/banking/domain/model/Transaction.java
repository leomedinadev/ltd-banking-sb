package ec.com.leodev.banking.domain.model;

import java.time.Instant;
import java.util.UUID;
import lombok.Getter;

@Getter
public class Transaction {

  private final String id;
  private final TransactionType type;
  private final Money amount;
  private final Instant createdAt;

  public Transaction(TransactionType type, Money amount) {
    this(UUID.randomUUID().toString(), type, amount, Instant.now());
  }

  // Reconstruye una transacción ya persistida
  public Transaction(String id, TransactionType type, Money amount, Instant createdAt) {
    this.id = id;
    this.type = type;
    this.amount = amount;
    this.createdAt = createdAt;
  }

}
