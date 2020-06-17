package ag.api.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Entity
@Table(name = "card")
@Data
public class EmployeeCard implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;
	
	@Pattern(regexp = "^\\d{3}\\D{1}$", message = "Enter employee id") // ^[0-9]{3}[a-z]{1}$
	@NotEmpty
	@Column(unique = true)
	@JsonProperty("bows-id")
	private String bowsEmployeeId; 
	
	@NotEmpty(message = "Enter name")
	private String name; 
	
	@Email(message = "Please provide valid email")
	@Column(unique = true)
	private String email; 
	
	@Pattern(regexp = "^07[\\d]{9}$", message = "Enter valid mobile number")
	private String mobile; 
	
	@Pattern(regexp = "^[\\d]{4}$", message = "Enter 4 digit pin")
	private String pin; 
	
	@Pattern(regexp = "^[\\d\\D]{16}$", message = "Enter 16 alphanumeric characters card number")
	@JsonProperty("card-number")
	@NotEmpty
	private String dataCard; 
	
	private Double balance;
	
	private Boolean active; 
	
	@Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	@JsonIgnore
	private Date createdAt = new Date(); 
	
	@Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	@JsonIgnore
	private Date modifiedAt = new Date();
	
	public EmployeeCard() {}
	
	public EmployeeCard(
			@Pattern(regexp = "^\\d{3}\\D{1}$", message = "Enter employee id") @NotEmpty String bowsEmployeeId,
			@NotEmpty(message = "Enter name") String name, @Email(message = "Please provide valid email") String email,
			@Pattern(regexp = "^07[\\d]{9}$", message = "Enter valid mobile number") String mobile,
			@Pattern(regexp = "^[\\d\\D]{16}$", message = "Enter 16 alphanumeric characters card number") @NotEmpty String cardNumber,
			Double balance, Boolean active) {
		super();
		this.bowsEmployeeId = bowsEmployeeId;
		this.name = name;
		this.email = email;
		this.mobile = mobile;
		this.dataCard = cardNumber;
		this.balance = balance;
		this.active = active;
	}

	public Double topupBalance(Double topupAmount) {
		if(topupAmount > 0.00) {
			balance += topupAmount; 
			return balance; 
		}
		return balance; 
	}

	
	/*
	* hashCode()is a unique hash/number attached to every object whenever the object is created.
	* 
	* equals()method is used to compare two objects based on their properties.
	* 
	* So whenever two objects are compared. Their hash code and properties are compared. 
	* If both (hash code & properties value) are same then the object is considered equal otherwise not equal.
	* Therefore, it is very important to override hascode() and equals() method of an object.
	*/
	
	
	
}
