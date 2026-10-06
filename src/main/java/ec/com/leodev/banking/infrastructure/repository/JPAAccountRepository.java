package ec.com.leodev.banking.infrastructure.repository;

import ec.com.leodev.banking.infrastructure.persistence.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface JPAAccountRepository extends JpaRepository<AccountEntity, String> {

}
