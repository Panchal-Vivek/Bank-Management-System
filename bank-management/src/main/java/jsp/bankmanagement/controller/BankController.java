package jsp.bankmanagement.controller;

import java.util.List;

import org.springframework.data.domain.Page;
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
//import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jsp.bankmanagement.dto.ResponseStructure;
import jsp.bankmanagement.entity.Bank;
import jsp.bankmanagement.service.BankService;

@RestController
@RequestMapping("/banks")
public class BankController {

	private final BankService bankService;

	public BankController(BankService bankService) {
		this.bankService = bankService;
	}

	@PostMapping
	public ResponseEntity<ResponseStructure<Bank>> createBank(@Valid @RequestBody Bank bank) {

		Bank savedBank = bankService.createBank(bank);

		ResponseStructure<Bank> response = new ResponseStructure<>(HttpStatus.CREATED.value(),
				"Bank created successfully", savedBank);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@PostMapping("/all")
	public ResponseEntity<ResponseStructure<List<Bank>>> saveAllBanks(@Valid @RequestBody List<@Valid Bank> banks) {

		List<Bank> savedBanks = bankService.saveAllBanks(banks);

		ResponseStructure<List<Bank>> res = new ResponseStructure<>();
		res.setStatusCode(HttpStatus.CREATED.value());
		res.setMessage("Banks created successfully");
		res.setData(savedBanks);

		return new ResponseEntity<>(res, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<ResponseStructure<List<Bank>>> getAllBanks() {

		List<Bank> banks = bankService.getAllBanks();

		ResponseStructure<List<Bank>> response = new ResponseStructure<>();

		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Banks fetched successfully");
		response.setData(banks);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/id/{id}")
	public ResponseEntity<ResponseStructure<Bank>> getBankById(@PathVariable Integer id) {

		Bank bank = bankService.getById(id);

		ResponseStructure<Bank> res = new ResponseStructure<>();
		res.setStatusCode(HttpStatus.OK.value());
		res.setMessage("Bank fetched successfully");
		res.setData(bank);

		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@GetMapping("/ifsc/{ifsc}")
	public ResponseEntity<ResponseStructure<Bank>> getBankByIFSC(@PathVariable String ifsc) {

		Bank bank = bankService.getByIFSC(ifsc);
		ResponseStructure<Bank> res = new ResponseStructure<>();
		res.setStatusCode(HttpStatus.OK.value());
		res.setMessage("Banks fetched successfully");
		res.setData(bank);

		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@GetMapping("/address/{street}")
	public ResponseEntity<ResponseStructure<List<Bank>>> getBankByAddress(@PathVariable String street) {

		List<Bank> banks = bankService.getBankByAddress(street);

		ResponseStructure<List<Bank>> res = new ResponseStructure<>();
		res.setStatusCode(HttpStatus.OK.value());
		res.setMessage("Banks fetched successfully");
		res.setData(banks);

		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@GetMapping("/city/{city}")
	public ResponseEntity<ResponseStructure<List<Bank>>> getBankByaddressCity(@PathVariable String city) {

		List<Bank> bank = bankService.getBankByCity(city);
		ResponseStructure<List<Bank>> res = new ResponseStructure<>();
		res.setStatusCode(HttpStatus.OK.value());
		res.setMessage("Banks fetched successfully");
		res.setData(bank);

		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@GetMapping("/contact/{contact}")
	public ResponseEntity<ResponseStructure<Bank>> getBankByContact(@PathVariable String contact) {

		Bank bank = bankService.getBankByContact(contact);
		ResponseStructure<Bank> res = new ResponseStructure<>();
		res.setStatusCode(HttpStatus.OK.value());
		res.setMessage("Banks fetched successfully");
		res.setData(bank);

		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@DeleteMapping("/{bankId}")
	public ResponseEntity<ResponseStructure<String>> deleteBank(@PathVariable Integer bankId) {

		bankService.deleteBank(bankId);

		ResponseStructure<String> res = new ResponseStructure<>();
		res.setStatusCode(HttpStatus.OK.value());
		res.setMessage("Bank deleted successfully");
		res.setData("Bank ID: " + bankId);

		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@PutMapping("/contact/{bankId}")
	public ResponseEntity<ResponseStructure<Bank>> updateBankContact(@PathVariable Integer bankId,
			@RequestParam String contact) {
		Bank bank = bankService.updateBankContact(bankId, contact);

		ResponseStructure<Bank> res = new ResponseStructure<>();
		res.setStatusCode(HttpStatus.OK.value());
		res.setMessage("Bank contact updated successfully");
		res.setData(bank);

		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	@PutMapping("/{bankId}/address/{addressId}")
	public ResponseEntity<ResponseStructure<Bank>> addAddressToBank(@PathVariable Integer bankId,
			@PathVariable Integer addressId) {

		Bank bank = bankService.addAddressToBank(bankId, addressId);

		ResponseStructure<Bank> res = new ResponseStructure<>();
		res.setStatusCode(HttpStatus.OK.value());
		res.setMessage("Address linked to bank successfully");
		res.setData(bank);

		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@GetMapping("/pagination")

	public ResponseEntity<ResponseStructure<Page<Bank>>> getByPaginationAndSorting(@RequestParam Integer pageNur,
			@RequestParam Integer pageSize, @RequestParam String sortBy, @RequestParam String direction) {

		Page<Bank> banks = bankService.getByPaginationAndSorting(pageNur, pageSize, sortBy, direction);
		ResponseStructure<Page<Bank>> res = new ResponseStructure<>();

		res.setStatusCode(HttpStatus.OK.value());
		res.setMessage("Banks fetched successfully");
		res.setData(banks);

		return new ResponseEntity<>(res, HttpStatus.OK);

	}

}
