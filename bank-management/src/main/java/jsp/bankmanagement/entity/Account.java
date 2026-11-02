package jsp.bankmanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer accountId;

	@Column(unique = true, nullable = false)
	private Long accountNur;
	private String accountHolder;
	@Enumerated(EnumType.STRING)
	
	@NotNull(message = "Account type is required")
	private AccountType accountType;
	private Double balance;

	@ManyToOne(optional = false)
	@JoinColumn(name = "bank_id")
	@JsonIgnore
	private Bank bank;
}
