package ag.api.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ag.api.exception.ResourceNotFoundException;
import ag.api.exception.UserNotFoundException;
import ag.api.model.EmployeeCard;
import ag.api.repository.EmployeeCardRepository;
import ag.api.service.interfaces.EmployeeCardService;

@Service
public class EmployeeCardServiceImpl implements EmployeeCardService {
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder; 
	
	@Autowired
	private EmployeeCardRepository cardRepository; 

	
	@Override
	public EmployeeCard saveCard(EmployeeCard cardDetails) {
		return cardRepository.save(cardDetails);
	}
	
	@Override
	public boolean isDataCardAlreadyInUse(String cardNumber) {
		EmployeeCard card = cardRepository.findByDataCard(cardNumber); 
			
		if(card != null && card.getActive()) {
			return true; 
		}
		else 
			return false; 
	}
	
	@Override
	public EmployeeCard addEmployee(EmployeeCard employeeDetails) {
		EmployeeCard newEmployeeCard = new EmployeeCard();
		
		while(!isDataCardAlreadyInUse(newEmployeeCard.getDataCard())) {
			newEmployeeCard.setBowsEmployeeId(employeeDetails.getBowsEmployeeId());
			newEmployeeCard.setName(employeeDetails.getName());
			newEmployeeCard.setEmail(employeeDetails.getEmail());
			newEmployeeCard.setMobile(employeeDetails.getMobile());
			
			// encode pin 
			newEmployeeCard.setPin(bCryptPasswordEncoder.encode(employeeDetails.getPin()));
			
			newEmployeeCard.setDataCard(employeeDetails.getDataCard());
			newEmployeeCard.setBalance(employeeDetails.getBalance());
			newEmployeeCard.setActive(true);
			return saveCard(newEmployeeCard); 
		}
		
			throw new UserNotFoundException("Card already in the system");
	}
	
	@Override
	public Double topupBalanceByCardNumber(String cardNumber, Double topupAmount) {
		EmployeeCard card = getSingleEmployeeCardByCardNumber(cardNumber); 
		
		if(isDataCardAlreadyInUse(cardNumber)) {
			card.topupBalance(topupAmount); 
			saveCard(card); 
			return card.getBalance(); 
		}
		else 
			throw new ResourceNotFoundException("Card with the provided card number not found, please register your card"); 
	}
	
	@Override
	public Double getCardBalanceById(int id) {
		EmployeeCard card = getSingleEmployeeCardById(id); 
		
		if(card != null && card.getActive())
			return card.getBalance(); 
		else 
			throw new UserNotFoundException();  
	}

	@Override
	public EmployeeCard getSingleEmployeeCardByCardNumber(String cardNumber) {		
		if(isDataCardAlreadyInUse(cardNumber)) {
			return cardRepository.findByDataCard(cardNumber); 
		}
		else 
			throw new ResourceNotFoundException("Employee card not found, enter valid card number or register your card"); 
	}

	@Override
	public EmployeeCard getSingleEmployeeCardById(int id) {
		Optional<EmployeeCard> card = cardRepository.findById(id); 
		
		if(card.isPresent()) {
			return card.get(); 
		}
		else 
			throw new ResourceNotFoundException("Employee card not found, enter valid id or register your card"); 
	}

	@Override
	public List<EmployeeCard> getAllEmployeeCards() {
		List<EmployeeCard> cardList = cardRepository.findAll(); 
		
		if(!cardList.isEmpty()) {
			return cardList; 
		}
		else 
			throw new ResourceNotFoundException("No employee cards records were found");
	}

	@Override
	public void removeSingleEmployeeCardById(int id) {
		if(getSingleEmployeeCardById(id) != null) {
			cardRepository.deleteById(id);
		}
		else 
			throw new ResourceNotFoundException("Card with the provided id not found");
	}

	@Override
	public void removeAllEmployeeCards() {
		cardRepository.deleteAll();
	}

	
}

