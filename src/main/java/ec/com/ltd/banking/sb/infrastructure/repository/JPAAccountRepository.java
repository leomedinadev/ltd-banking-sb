package ec.com.ltd.banking.sb.infrastructure.repository;

import ec.com.ltd.banking.sb.infrastructure.persistence.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface JPAAccountRepository extends JpaRepository<AccountEntity, String> {

}
