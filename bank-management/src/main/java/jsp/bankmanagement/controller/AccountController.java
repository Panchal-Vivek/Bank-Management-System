package jsp.bankmanagement.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import jsp.bankmanagement.dto.ResponseStructure;
import jsp.bankmanagement.entity.Account;
import jsp.bankmanagement.entity.AccountType;
import jsp.bankmanagement.service.AccountService;

@RestController
@RequestMapping("/account")

public class AccountController {

	private final AccountService accountService;

	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}

	@PostMapping("/bank/{bankId}")
	public ResponseEntity<ResponseStructure<Account>> createAccount(@PathVariable Integer bankId,
			@RequestBody Account account) {

		Account savedAccount = accountService.createAccount(bankId, account);

		ResponseStructure<Account> res = new ResponseStructure<>();

		res.setStatusCode(HttpStatus.CREATED.value());
		res.setMessage("Account created successfully");
		res.setData(savedAccount);

		return new ResponseEntity<>(res, HttpStatus.CREATED);
	}

	@PostMapping("/all/{bankId}")
	public ResponseEntity<ResponseStructure<List<Account>>> addAllAccount(@PathVariable Integer bankId,
			@RequestBody List<Account> account) {

		List<Account> savedAccount = accountService.saveAllAccount(bankId, account);

		ResponseStructure<List<Account>> res = new ResponseStructure<>();
		res.setStatusCode(HttpStatus.CREATED.value());
		res.setMessage("Accounts created successfully");
		res.setData(savedAccount);

		return new ResponseEntity<>(res, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<ResponseStructure<List<Account>>> getAllAccounts() {

		List<Account> account = accountService.getAllAccounts();

		ResponseStructure<List<Account>> res = new ResponseStructure<>();

		res.setStatusCode(HttpStatus.OK.value());
		res.setMessage("Accounts fetched successfully");
		res.setData(account);

		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@GetMapping("{id}")
	public ResponseEntity<ResponseStructure<Account>> getAccountById(@PathVariable Integer id) {

		Account account = accountService.getAccountById(id);

		ResponseStructure<Account> res = new ResponseStructure<>();

		res.setStatusCode(HttpStatus.OK.value());
		res.setMessage("Accounts fetched successfully with id:" + id);
		res.setData(account);

		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseStructure<String>> deleteAccount(@PathVariable Integer id) {

		accountService.deleteAccount(id);

		ResponseStructure<String> res = new ResponseStructure<>();

		res.setStatusCode(HttpStatus.OK.value());
		res.setMessage("Accounts deleted successfully");
		res.setData("Account id: " + id);

		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ResponseStructure<Account>> updateAccountHolderNameAndAccountType(@PathVariable Integer id,
			@RequestParam String accountHolder, @RequestParam AccountType accountType) {

		Account account = accountService.updateAccountHolderNameAndAccountType(id, accountHolder, accountType);
		ResponseStructure<Account> res = new ResponseStructure<>();

		res.setStatusCode(HttpStatus.OK.value());
		res.setMessage("Accounts updated successfully");
		res.setData(account);

		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@PutMapping("/deposit/{id}")
	public ResponseEntity<ResponseStructure<Account>> depositeAmount(@PathVariable Integer id,@RequestParam Double amount){

		Account account = accountService.depositAmount(id,amount);

		ResponseStructure<Account> res = new ResponseStructure<>();

		res.setStatusCode(HttpStatus.OK.value());
	    res.setMessage("Amount withdraw Successfully");
		res.setData(account);

		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@PutMapping("/withdraw/{id}")
	public ResponseEntity<ResponseStructure<Account>> withdrawAmount(@PathVariable Integer id, @RequestParam Double amount){

		Account account = accountService.withdrawAmount(id,amount);

		ResponseStructure<Account> res = new ResponseStructure<>();

		res.setStatusCode(HttpStatus.OK.value());
	    res.setMessage("Amount deposit Successfully");
		res.setData(account);

		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@PutMapping("/transfer")
	public ResponseEntity<ResponseStructure<String>> transferAmount(
			@RequestParam Integer senderId, @RequestParam Integer receiverId,@RequestParam Double amount){

		String result = accountService.transferAmount(senderId,receiverId,amount);

		ResponseStructure<String> res = new ResponseStructure<>();

		res.setStatusCode(HttpStatus.OK.value());
	    res.setMessage(result);
		res.setData(result);

		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("/bank/{id}")
	public ResponseEntity<ResponseStructure<List<Account>>> getAccountByBank(@PathVariable Integer id) {

		List<Account> accounts = accountService.getAccountByBank(id);

		ResponseStructure<List<Account>> res = new ResponseStructure<>();

		res.setStatusCode(HttpStatus.OK.value());
	    res.setMessage("Accounts fetched successfully for bank ID: " + id);
		res.setData(accounts);

		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("/type/{accountType}")
	public ResponseEntity<ResponseStructure<List<Account>>> getAccountByType(@PathVariable AccountType accountType){

		List<Account> accounts = accountService.getAccountByType(accountType);

		ResponseStructure<List<Account>> res = new ResponseStructure<>();

		res.setStatusCode(HttpStatus.OK.value());
	    res.setMessage("Accounts fetched successfully for bank accountType: " + accountType);
		res.setData(accounts);

		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("/balance")
	public ResponseEntity<ResponseStructure<List<Account>>> getAccountWithBalanceGreaterValue(@RequestParam Double balance){
		

		List<Account> accounts = accountService.getAccountWithBalanceGreaterValue(balance);

		ResponseStructure<List<Account>> res = new ResponseStructure<>();

		res.setStatusCode(HttpStatus.OK.value());
//	    res.setMessage("Accounts fetched successfully with balance greater than " + balance);
		if (accounts.isEmpty()) {
	        res.setMessage("No accounts available with the given balance");
	    } else {
	        res.setMessage("Accounts fetched successfully");
	    }
		res.setData(accounts);

		return new ResponseEntity<>(res, HttpStatus.OK);
		
	}
	
	@GetMapping("/number/{number}")
	public ResponseEntity<ResponseStructure<Account>> getAccountByAccountNumber(@PathVariable Long number){
		

		Account accounts = accountService.getAccountByAccountNumber(number);

		ResponseStructure<Account> res = new ResponseStructure<>();

		res.setStatusCode(HttpStatus.OK.value());
	    res.setMessage("Account fetched successfully");
//		if (accounts.isEmpty()) {
//	        res.setMessage("No accounts available");
//	    } else {
//	        res.setMessage("Accounts fetched successfully");
//	    }
		res.setData(accounts);

		return new ResponseEntity<>(res, HttpStatus.OK);
		
	}
	
	@GetMapping("/sorting")
	
	public ResponseEntity<ResponseStructure<List<Account>>> getAccountBySorting(@RequestParam String field, @RequestParam String direction){

		List<Account> accounts = accountService.getAccountBySorting(field,direction);

		ResponseStructure<List<Account>> res = new ResponseStructure<>();

		res.setStatusCode(HttpStatus.OK.value());
	    res.setMessage("Accounts displayed in sorting order");
		res.setData(accounts);

		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
}
