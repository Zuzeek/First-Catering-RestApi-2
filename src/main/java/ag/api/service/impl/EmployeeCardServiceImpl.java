package ag.api.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ag.api.model.EmployeeCard;
import ag.api.repository.EmployeeCardRepository;
import ag.api.service.interfaces.EmployeeCardService;

@Service
public class EmployeeCardServiceImpl implements EmployeeCardService {
	
	@Autowired
	private EmployeeCardRepository cardRepository; 

	@Override
	public EmployeeCard saveCard(EmployeeCard cardDetails) {
		return cardRepository.save(cardDetails);
	}

	@Override
	public EmployeeCard addEmployee(EmployeeCard employeeDetails) {
		employeeDetails.setBowsEmployeeId(employeeDetails.getBowsEmployeeId());
		employeeDetails.setName(employeeDetails.getName());
		employeeDetails.setEmail(employeeDetails.getEmail());
		employeeDetails.setMobile(employeeDetails.getMobile());
		
		employeeDetails.setDataCard(employeeDetails.getDataCard());
		employeeDetails.setBalance(employeeDetails.getBalance());
		employeeDetails.setActive(true);
		return saveCard(employeeDetails); 
	}
	
	@Override
	public EmployeeCard topupBalanceByCardNumber(String cardNumber, Double topupAmount) {
		EmployeeCard card = getSingleEmployeeCardByCardNumber(cardNumber); 
		
		if(card != null) {
			card.topupBalance(topupAmount); 
			return card; 
		}
		return null;
	}
	
	@Override
	public EmployeeCard topupBalanceById(Integer id, Double topupAmount) {
		EmployeeCard card = getSingleEmployeeCardById(id); 
		if(card != null) {
			card.topupBalance(topupAmount); 
			return saveCard(card);
		}
		return null; 
	}

	@Override
	public Double getCardBalanceById(Integer id) {
		EmployeeCard card = getSingleEmployeeCardById(id); 
		if(card != null)
			return card.getBalance(); 
		return null; 
	}

	@Override
	public EmployeeCard getSingleEmployeeCardByCardNumber(String cardNumber) {
		EmployeeCard card =  cardRepository.findByDataCard(cardNumber); 
		
		if(card != null) {
			return card; 
		}
		return null; 
	}

	@Override
	public EmployeeCard getSingleEmployeeCardById(Integer id) {
		Optional<EmployeeCard> card = cardRepository.findById(id); 
		
		if(card.isPresent()) {
			return card.get(); 
		}
		return null; 
	}

	@Override
	public List<EmployeeCard> getAllEmployeeCards() {
		return cardRepository.findAll();
	}

	@Override
	public void removeSingleEmployeeCardById(Integer id) {
		cardRepository.deleteById(id);
	}

	@Override
	public void removeAllEmployeeCards() {
		cardRepository.deleteAll();
	}

}
