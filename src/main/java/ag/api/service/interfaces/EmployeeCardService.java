package ag.api.service.interfaces;

import java.util.List;

import ag.api.model.EmployeeCard;

public interface EmployeeCardService {
	
	EmployeeCard saveCard(EmployeeCard cardDetails); 	
	EmployeeCard addEmployee(EmployeeCard employeeDetails);
	
	Double topupBalanceByCardNumber(String cardNumber, Double topupAmount);
	Double getCardBalanceById(int id); 
	
	EmployeeCard getSingleEmployeeCardByCardNumber(String cardNumber); 
	EmployeeCard getSingleEmployeeCardById(int id); 
	List<EmployeeCard> getAllEmployeeCards();
	
	void removeSingleEmployeeCardById(int id); 
	void removeAllEmployeeCards();
		
	boolean isDataCardAlreadyInUse(String cardNumber);

	
	

	
}
