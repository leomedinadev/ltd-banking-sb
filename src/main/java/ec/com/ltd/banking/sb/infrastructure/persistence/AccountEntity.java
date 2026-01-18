package ec.com.ltd.banking.sb.infrastructure.persistence;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

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

  @OneToMany(mappedBy = "accountEntity", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<TransactionEntity> transactions = new ArrayList<>();

  public void addTransaction(TransactionEntity transactionEntity) {
    transactionEntity.setAccountEntity(this);
    this.transactions.add(transactionEntity);
  }

}
