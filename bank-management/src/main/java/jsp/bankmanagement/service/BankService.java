
package jsp.bankmanagement.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import jsp.bankmanagement.entity.Address;
import jsp.bankmanagement.entity.Bank;
import jsp.bankmanagement.exception.DuplicateBankException;
import jsp.bankmanagement.repository.AccountRepository;
import jsp.bankmanagement.repository.AddressRepository;
import jsp.bankmanagement.repository.BankRepository;

@Service
public class BankService {

	private final BankRepository bankRepository;

	private final AccountRepository accountRepository;

	private final AddressRepository addressRepository;

	public BankService(BankRepository bankRepository, AccountRepository accountRepository,
			AddressRepository addressRepository) {
		this.bankRepository = bankRepository;
		this.accountRepository = accountRepository;
		this.addressRepository = addressRepository;

	}

	public Bank createBank(Bank bank) {
		if (bankRepository.existsByIfsc(bank.getIfsc())) {
			throw new DuplicateBankException("IFSC code already exists");
		}

		if (bankRepository.existsByContact(bank.getContact())) {
			throw new RuntimeException("Contact number already exists");
		}
		return bankRepository.save(bank);
	}

	public List<Bank> getAllBanks() {
		return bankRepository.findAll();
	}

	public Bank getById(Integer id) {

		return bankRepository.findById(id).orElseThrow(() -> new RuntimeException("Bank not found with id: " + id));
	}

	public Bank getByIFSC(String ifsc) {
		return bankRepository.findByIfsc(ifsc)
				.orElseThrow(() -> new RuntimeException("Bank not found with IFSC: " + ifsc));
	}

	public List<Bank> getBankByAddress(String street) {
		return bankRepository.findByAddressStreet(street);
	}

	public List<Bank> getBankByCity(String city) {
		return bankRepository.findByAddressCity(city);
	}

	public Bank getBankByContact(String contact) {
		return bankRepository.findByContact(contact)
				.orElseThrow(() -> new RuntimeException("Bank not found with contact: " + contact));
	}

	@Transactional
	public List<Bank> saveAllBanks(List<Bank> banks) {
		Set<String> ifscSet = new HashSet<>();
	    Set<String> contactSet = new HashSet<>();

	    for (Bank bank : banks) {

	        if (bank == null) {
	            throw new IllegalArgumentException("Bank details cannot be null");
	        }

	        // Check duplicate IFSC inside the request
	        if (!ifscSet.add(bank.getIfsc())) {
	            throw new DuplicateBankException(
	                "Duplicate IFSC found in request: " + bank.getIfsc()
	            );
	        }

	        // Check duplicate contact inside the request
	        if (!contactSet.add(bank.getContact())) {
	            throw new DuplicateBankException(
	                "Duplicate contact found in request: " + bank.getContact()
	            );
	        }

	        // Check duplicate IFSC in database
	        if (bankRepository.existsByIfsc(bank.getIfsc())) {
	            throw new DuplicateBankException(
	                "IFSC code already exists: " + bank.getIfsc()
	            );
	        }

	        // Check duplicate contact in database
	        if (bankRepository.existsByContact(bank.getContact())) {
	            throw new DuplicateBankException(
	                "Contact number already exists: " + bank.getContact()
	            );
	        }
	    }
	    return bankRepository.saveAll(banks);
	}

//    public void deleteBank(Integer bankId) {
//
//    	if (!bankRepository.existsById(bankId)) {
//    		throw new RuntimeException("Bank not found with ID: " + bankId);
//    	}
//    	
//    	if (accountRepository.existsByBank_BankId(bankId)) {
//    	    throw new RuntimeException("Bank cannot be deleted because it has accounts");
//    	}
//    	bankRepository.deleteById(bankId);
//    }

	public void deleteBank(Integer bankId) {

		if (!bankRepository.existsById(bankId)) {
			throw new RuntimeException("Bank not found with ID: " + bankId);
		}

		if (accountRepository.existsByBank_BankId(bankId)) {
			throw new RuntimeException("Bank cannot be deleted because it has accounts");
		}

		bankRepository.deleteById(bankId);
	}

	public Bank updateBankContact(Integer bankId, String contact) {

		Bank bank = bankRepository.findById(bankId)
				.orElseThrow(() -> new RuntimeException("Bank not found with ID: " + bankId));

		if (bankRepository.existsByContact(contact) && !bank.getContact().equals(contact)) {
			throw new DuplicateBankException("Contact number already exists");
		}
		bank.setContact(contact);

		return bankRepository.save(bank);
	}

	public Bank addAddressToBank(Integer bankId, Integer addressId) {

		Bank bank = bankRepository.findById(bankId).orElseThrow(() -> new RuntimeException("Bank not found"));

		Address address = addressRepository.findById(addressId)
				.orElseThrow(() -> new RuntimeException("Address not found"));

		bank.setAddress(address);
		address.setBank(bank);

		return bankRepository.save(bank);
	}

	public Page<Bank> getByPaginationAndSorting(Integer pageNur, Integer pageSize, String sortBy, String direction) {
		if (pageNur == null || pageNur < 0) {
			throw new IllegalArgumentException("Page number cannot be negative");
		}

		if (pageSize == null || pageSize <= 0) {
			throw new IllegalArgumentException("Page size must be greater than zero");
		}

		List<String> allowedFields = List.of("bankId", "bankName", "ifsc", "branchName", "contact");

		if (!allowedFields.contains(sortBy)) {
			throw new IllegalArgumentException("Invalid sorting field");
		}

		if (!direction.equalsIgnoreCase("asc") && !direction.equalsIgnoreCase("desc")) {
			throw new IllegalArgumentException("Direction must be asc or desc");
		}
		Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

		Pageable pageable = PageRequest.of(pageNur, pageSize, sort);

		return bankRepository.findAll(pageable);
	}
}
