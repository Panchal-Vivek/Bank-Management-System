package jsp.bankmanagement.repository;

import jsp.bankmanagement.entity.Bank;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepository extends JpaRepository<Bank,Integer>{

	
	Optional<Bank> findByIfsc(String ifsc);
	
	// Find bank by street address
    List<Bank> findByAddressStreet(String street);
	
	List<Bank> findByAddressCity(String city);
	
	Optional<Bank> findByContact(String contact);
	
	boolean existsByIfsc(String ifsc);

	boolean existsByContact(String contact);
//	Page<Bank> findAll(Pageable pageable);
}
