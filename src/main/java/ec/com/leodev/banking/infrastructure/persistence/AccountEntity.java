package ec.com.leodev.banking.infrastructure.persistence;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "account")
@Getter
@Setter
public class AccountEntity {

  @Id
  private String id;
  @Column(nullable = false)
  private String customerId;
  @Column(nullable = false, precision = 19, scale = 2)
  private BigDecimal balance;

  // Bloqueo optimista: evita que dos operaciones concurrentes pisen el saldo
  @Version
  @ColumnDefault("0")
  private Long version;

  @OneToMany(mappedBy = "accountEntity", cascade = CascadeType.ALL, orphanRemoval = true)
  @OrderBy("createdAt ASC")
  private List<TransactionEntity> transactions = new ArrayList<>();

  public void addTransaction(TransactionEntity transactionEntity) {
    transactionEntity.setAccountEntity(this);
    this.transactions.add(transactionEntity);
  }

}
