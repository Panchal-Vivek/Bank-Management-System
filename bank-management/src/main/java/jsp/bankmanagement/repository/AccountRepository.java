package jsp.bankmanagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import jsp.bankmanagement.entity.Account;
import jsp.bankmanagement.entity.AccountType;

public interface AccountRepository extends JpaRepository<Account, Integer>{

	List<Account> findByBank_BankId(Integer id);

	List<Account> findByAccountType(AccountType accountType);

	List<Account> findByBalanceGreaterThan(Double balance);

	Optional<Account> findByAccountNur(Long accountNur);
	
    boolean existsByBank_BankId(Integer bankId);

}
