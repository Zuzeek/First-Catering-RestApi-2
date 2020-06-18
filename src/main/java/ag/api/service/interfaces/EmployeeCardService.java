package ag.api.service.interfaces;

import java.util.List;

import ag.api.model.EmployeeCard;

public interface EmployeeCardService {
	
	EmployeeCard saveCard(EmployeeCard cardDetails); 	
	EmployeeCard addEmployee(EmployeeCard employeeDetails);
	
	EmployeeCard topupBalanceByCardNumber(String cardNumber, Double topupAmount);
	Double getCardBalanceById(Integer id); 
	
	EmployeeCard getSingleEmployeeCardByCardNumber(String cardNumber); 
	EmployeeCard getSingleEmployeeCardById(Integer id); 
	List<EmployeeCard> getAllEmployeeCards();
	
	void removeSingleEmployeeCardById(Integer id); 
	void removeAllEmployeeCards();
		
	boolean isDataCardAlreadyInUse(String cardNumber);

	
	

	
}
