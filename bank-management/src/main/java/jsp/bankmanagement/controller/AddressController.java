package jsp.bankmanagement.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jsp.bankmanagement.dto.ResponseStructure;
import jsp.bankmanagement.entity.Address;
import jsp.bankmanagement.entity.Bank;
import jsp.bankmanagement.repository.AddressRepository;
import jsp.bankmanagement.service.AddressService;
import jsp.bankmanagement.service.BankService;

@RestController
@RequestMapping("/address")

public class AddressController {

	private final AddressService addressService;

	public AddressController(AddressService addressService) {
		this.addressService = addressService;
	}
	
	@PostMapping
	public ResponseEntity<ResponseStructure<Address>> addAddress(@Valid @RequestBody Address address){
		Address saveAddress = addressService.addAddress(address);
		ResponseStructure<Address> res = new ResponseStructure<>();

		res.setStatusCode(HttpStatus.CREATED.value());
        res.setMessage("Address added successfully");
        res.setData(saveAddress);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
	}
	
	@PostMapping("/all")
	public ResponseEntity<ResponseStructure<List<Address>>> addAllAddress(
			@RequestBody List<@Valid Address> address){
		List<Address> saveAddress = addressService.addAllAddress(address);
		ResponseStructure<List<Address>> res = new ResponseStructure<>();

		res.setStatusCode(HttpStatus.CREATED.value());
        res.setMessage("Address added successfully");
        res.setData(saveAddress);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
	}

	@GetMapping("/{addressId}")
	public ResponseEntity<ResponseStructure<Address>> getAddressById(
		@PathVariable Integer addressId) {

        Address address = addressService.getAddressById(addressId);

        ResponseStructure<Address> res = new ResponseStructure<>();

        res.setStatusCode(HttpStatus.OK.value());
        res.setMessage("Address fetched successfully");
        res.setData(address);

        return new ResponseEntity<>(res, HttpStatus.OK);
	
	}
	
	@GetMapping
    public ResponseEntity<ResponseStructure<List<Address>>> getAllBanks() {

        List<Address> banks = addressService.getAllAddress();

        ResponseStructure<List<Address>> response =
                new ResponseStructure<>();

        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Banks fetched successfully");
        response.setData(banks);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
	
	@GetMapping("/bank/{bankId}")
	public ResponseEntity<ResponseStructure<Address>> getAddressByBank(
		@PathVariable Integer bankId) {

        Address address = addressService.getAddressByBank(bankId);

        ResponseStructure<Address> res = new ResponseStructure<>();

        res.setStatusCode(HttpStatus.OK.value());
        res.setMessage("Address fetched successfully");
        res.setData(address);

        return new ResponseEntity<>(res, HttpStatus.OK);
	
	}
	
	@GetMapping("/city/{city}")
	public ResponseEntity<ResponseStructure<List<Address>>> getAddressByCity(
		@PathVariable String city) {

        List<Address> address = addressService.getAddressByCity(city);

        ResponseStructure<List<Address>> res = new ResponseStructure<>();

        res.setStatusCode(HttpStatus.OK.value());
        res.setMessage("City fetched successfully");
        res.setData(address);

        return new ResponseEntity<>(res, HttpStatus.OK);
	
	}
	
	@GetMapping("/search")
	public ResponseEntity<ResponseStructure<List<Address>>> getAddressByStreetAndCity(
		@RequestParam String street, @RequestParam String city) {

        List<Address> address = addressService.getAddressByStreetAndCity(street,city);

        ResponseStructure<List<Address>> res = new ResponseStructure<>();

        res.setStatusCode(HttpStatus.OK.value());
        res.setMessage("Adresses fetched successfully");
        res.setData(address);

        return new ResponseEntity<>(res, HttpStatus.OK);
	
	}
}


