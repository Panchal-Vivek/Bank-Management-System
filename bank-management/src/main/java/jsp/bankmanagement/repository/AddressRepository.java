package jsp.bankmanagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import jsp.bankmanagement.entity.Address;

public interface AddressRepository extends JpaRepository<Address,Integer>{
	
//	Optional<Address> findByBankId(Integer bankId);
    Optional<Address> findByBank_BankId(Integer bankId);

    List<Address> findByCity(String city);

	List<Address> findByStreetAndCity(String street, String city);

}