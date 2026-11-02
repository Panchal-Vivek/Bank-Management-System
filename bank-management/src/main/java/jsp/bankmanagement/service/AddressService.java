package jsp.bankmanagement.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jsp.bankmanagement.entity.Address;
import jsp.bankmanagement.repository.AddressRepository;

@Service
public class AddressService {

	public final AddressRepository addressRepository;

	AddressService(AddressRepository addressRepository) {
		this.addressRepository = addressRepository;
	}

	public Address getAddressById(Integer addressId) {

		return addressRepository.findById(addressId)
				.orElseThrow(() -> new RuntimeException("Address not found with ID: " + addressId));
	}

	public Address addAddress(Address address) {
		return addressRepository.save(address);
	}

	public List<Address> addAllAddress(List<Address> address) {
		return addressRepository.saveAll(address);
	}

	public Address getAddressByBank(Integer bankId) {

		return addressRepository.findByBank_BankId(bankId)
				.orElseThrow(() -> new RuntimeException("Address not found for Bank ID: " + bankId));
	}

	public List<Address> getAddressByCity(String city) {
		return addressRepository.findByCity(city);
	}

	public List<Address> getAddressByStreetAndCity(String street, String city) {
		// TODO Auto-generated method stub
		return addressRepository.findByStreetAndCity(street, city);
	}

	public List<Address> getAllAddress() {
		// TODO Auto-generated method stub
		return addressRepository.findAll();
	}
}
