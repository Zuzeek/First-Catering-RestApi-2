package ag.api.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ag.api.exception.UserNotFoundException;
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
		EmployeeCard newEmployeeCard = new EmployeeCard();
		newEmployeeCard.setBowsEmployeeId(employeeDetails.getBowsEmployeeId());
		newEmployeeCard.setName(employeeDetails.getName());
		newEmployeeCard.setEmail(employeeDetails.getEmail());
		newEmployeeCard.setMobile(employeeDetails.getMobile());
		newEmployeeCard.setPin(employeeDetails.getPin());
		
		newEmployeeCard.setDataCard(employeeDetails.getDataCard());
		newEmployeeCard.setBalance(employeeDetails.getBalance());
		newEmployeeCard.setActive(true);
		return saveCard(newEmployeeCard); 
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

	@Override
	@Transactional
	public boolean isDataCardAlreadyInUse(String cardNumber) {
		EmployeeCard card = cardRepository.findByDataCard(cardNumber); 
			
		if(card != null && card.getActive()) {
			return true; 
		}
		else 
			return false; 
	}
}

