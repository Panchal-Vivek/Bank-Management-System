package jsp.bankmanagement.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import jakarta.transaction.Transactional;
import jsp.bankmanagement.entity.Account;
import jsp.bankmanagement.entity.AccountType;
import jsp.bankmanagement.entity.Bank;
import jsp.bankmanagement.exception.AccountNotFoundException;
import jsp.bankmanagement.exception.InsufficientBalanceException;
import jsp.bankmanagement.exception.InvalidAmountException;
import jsp.bankmanagement.exception.MinimumBalanceException;
import jsp.bankmanagement.repository.AccountRepository;
import jsp.bankmanagement.repository.BankRepository;

@Service
public class AccountService {

	private final AccountRepository accountRepository;
	private final BankRepository bankRepository;

	public AccountService(AccountRepository accountRepository, BankRepository bankRepository) {
		this.accountRepository = accountRepository;
		this.bankRepository = bankRepository;
	}

	public Account createAccount(Integer bankId, Account account) {

		Bank bank = bankRepository.findById(bankId)
				.orElseThrow(() -> new RuntimeException("Bank not found with ID: " + bankId));

		account.setBank(bank);

		return accountRepository.save(account);
	}

	public List<Account> saveAllAccount(Integer bankId, List<Account> accounts) {

		Bank bank = bankRepository.findById(bankId)
				.orElseThrow(() -> new RuntimeException("Bank not found with ID: " + bankId));

		for (Account account : accounts) {
			account.setBank(bank);
		}

		return accountRepository.saveAll(accounts);

	}

	public List<Account> getAllAccounts() {
		return accountRepository.findAll();
	}

	public Account getAccountById(Integer accountId) {
		return accountRepository.findById(accountId)
				.orElseThrow(() -> new RuntimeException("Account not found with ID: " + accountId));
	}

	public void deleteAccount(Integer id) {

		if (!accountRepository.existsById(id)) {
			throw new RuntimeException("Account is not present");
		}
		accountRepository.deleteById(id);

	}

	public Account updateAccountHolderNameAndAccountType(Integer id, String accountHolder, AccountType accountType) {

		Account account = accountRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Account not found with ID: " + id));

		account.setAccountHolder(accountHolder);
		account.setAccountType(accountType);

		return accountRepository.save(account);
	}

	public List<Account> getAccountByBank(Integer id) {

		if (!bankRepository.existsById(id)) {
			throw new RuntimeException("Bank not found with id: " + id);
		}
		return accountRepository.findByBank_BankId(id);
	}

	public List<Account> getAccountByType(AccountType accountType) {
		return accountRepository.findByAccountType(accountType);
	}
	
	@Transactional
	public Account depositAmount(Integer id, Double amount) {

		Account account = accountRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Account not found with id: " + id));

		if (amount < 0) {
			throw new RuntimeException("Deposit amount must be greater than zero");
		}
		account.setBalance(account.getBalance() + amount);
		return accountRepository.save(account);
	}

//	@Transactional
//	public Account withdrawAmount(Integer id, Double amount) {
//
//		Account account = accountRepository.findById(id)
//				.orElseThrow(() -> new RuntimeException("Account not found with id: " + id));
//
//		if (amount < 0) {
//			throw new RuntimeException("Withdrawal amount must be greater than zero");
//		}
//
//		if (account.getBalance() < amount) {
//			throw new RuntimeException("Insuffcient Balance..");
//		}
//
//		account.setBalance(account.getBalance() - amount);
//		return accountRepository.save(account);
//	}
	
	@Transactional
	public Account withdrawAmount(Integer accountId, Double amount){

	    Account account = accountRepository.findById(accountId)
	            .orElseThrow(() -> new AccountNotFoundException(
	                    "Account not found with ID: " + accountId));

	    if (amount <= 0) {
	    	throw new InsufficientBalanceException(
	    	        "Withdrawal amount must be greater than zero");
	    }

	    double remainingBalance = account.getBalance() - amount;

	    if (remainingBalance < 0) {
	        throw new InsufficientBalanceException("Insufficient balance");
	    }

	    validateMinimumBalance(account, remainingBalance);

	    account.setBalance(remainingBalance);

	    return accountRepository.save(account);
	}
	
	@Transactional
	public String transferAmount(Integer senderId, Integer receiverId, Double amount) {

	    // Validate transfer amount
	    if (amount == null || amount <= 0) {
	        throw new InvalidAmountException(
	                "Transfer amount must be greater than zero");
	    }

	    // Check sender and receiver are different
	    if (senderId.equals(receiverId)) {
	        throw new RuntimeException(
	                "Sender and receiver accounts cannot be the same");
	    }

	    // Find sender account
	    Account sender = accountRepository.findById(senderId)
	            .orElseThrow(() -> new AccountNotFoundException(
	                    "Sender account not found"));

	    // Find receiver account
	    Account receiver = accountRepository.findById(receiverId)
	            .orElseThrow(() -> new AccountNotFoundException(
	                    "Receiver account not found"));

	    // Calculate remaining balance
	    double remainingBalance = sender.getBalance() - amount;

	    // Check sufficient balance
	    if (remainingBalance < 0) {
	        throw new InsufficientBalanceException(
	                "Insufficient balance");
	    }

	    // Check minimum balance
	    validateMinimumBalance(sender, remainingBalance);

	    // Deduct amount from sender
	    sender.setBalance(remainingBalance);

	    // Add amount to receiver
	    receiver.setBalance(receiver.getBalance() + amount);

	    // Save both accounts
	    accountRepository.save(sender);
	    accountRepository.save(receiver);

	    return "Amount transferred successfully";
	}
	
	public List<Account> getAccountWithBalanceGreaterValue(Double balance) {

//		List<Account> accounts = accountRepository.findByBalanceGreaterThan(balance);
//	
//		 if (accounts.isEmpty()) {
//		        throw new RuntimeException(
//		                "No accounts available with balance greater than " + balance);
//		    }
		 if (balance == null || balance < 0) {
		        throw new InvalidAmountException(
		                "Balance search value cannot be negative");
		 }
		return accountRepository.findByBalanceGreaterThan(balance);
	}

	public Account getAccountByAccountNumber(long accountNur) {

	    return accountRepository.findByAccountNur(accountNur)
	            .orElseThrow(() -> new AccountNotFoundException(
	                    "Account not found with account number: " + accountNur));
	}

	public List<Account> getAccountBySorting(String field, String direction) {

		Sort sort = direction.equalsIgnoreCase("desc")
				?Sort.by(field).descending()
				:Sort.by(field).ascending();
		return accountRepository.findAll(sort);
	}	

	private void validateMinimumBalance(Account account, Double remainingBalance) {

	    if (account.getAccountType() == AccountType.SAVINGS
	            && remainingBalance < 1000) {

	        throw new MinimumBalanceException(
	                "Savings account must maintain a minimum balance of 1000");
	    }

	    if (account.getAccountType() == AccountType.CURRENT
	            && remainingBalance < 5000) {

	        throw new RuntimeException(
	                "Current account must maintain a minimum balance of 5000");
	    }
	}
	
}
