package poly.edu.ka.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import poly.edu.ka.domain.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

}
