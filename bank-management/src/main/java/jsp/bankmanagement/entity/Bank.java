package jsp.bankmanagement.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class Bank {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Integer bankId;
	
	@NotBlank(message = "Bank name is required")
	private String bankName;
	
	@Column(unique = true, nullable = false, length = 11)
	@Pattern(
	    regexp = "^[A-Z]{4}0[A-Z0-9]{6}$",
	    message = "Enter a valid 11-character IFSC code"
	)
	@NotBlank(message = "IFSC code is required")
	private String ifsc;
	
	@NotBlank(message = "Branch name is required")
	private String branchName;
	
	@Column(unique = true, nullable = false, length = 10)
	@Pattern(regexp = "^[0-9]{10}$", message = "Contact must contain exactly 10 digits")

	private String contact; 
	
	@OneToMany(mappedBy = "bank", cascade = CascadeType.ALL)
	private List<Account> accounts;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id", unique = true)
	@NotNull(message = "Bank address is mandatory")
	@Valid
	private Address address;
}
