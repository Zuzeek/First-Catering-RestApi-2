package ag.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ag.api.exception.ResourceNotFoundException;
import ag.api.model.EmployeeCard;
import ag.api.service.interfaces.EmployeeCardService;

@RestController
@RequestMapping(value = "card")
public class EmployeeCardController {
	
	@Autowired
	private EmployeeCardService cardService; 
	
	private static final String WELCOME_MESSAGE = "Welcome ";
    private static final String GOODBYE_MESSAGE = "Goodbye ";
	
    /* 
	 * @param employeeDetails
	 * @return register new employee 
	 * 
	 * consumes = MediaType.APPLICATION_JSON_VALUE => connects it to http and consumes JSON output from postman
	 */
	@PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, 
				produces = MediaType.APPLICATION_JSON_VALUE)
	public EmployeeCard register(@Valid @RequestBody EmployeeCard employeeDetails, String cardNumber) {
		return cardService.addEmployee(employeeDetails); 
	}
    
	/**
	 * 
	 * @param id
	 * @return fetch single employee card by id 
	 */
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public EmployeeCard viewCard(@PathVariable(value = "id") Integer id) {
		EmployeeCard card = cardService.getSingleEmployeeCardById(id); 
		
		if(card != null) {
			return card; 
		}
		else throw new ResourceNotFoundException("Employee card not found, enter valid id"); 
	}
	
	/**
	 * 
	 * @return fetch all employee cards 
	 */
	@GetMapping(value = {"", "/"}, produces = MediaType.APPLICATION_JSON_VALUE)
	public Iterable<EmployeeCard> viewAll(){
		return cardService.getAllEmployeeCards(); 
	}
		
	/**
	 *  
	 * @param id
	 * @param topupAmount
	 * @return employee card details with new balance
	 */
	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Double topup(@PathVariable(value = "id") Integer id, @RequestParam(value = "amount") Double topupAmount) {
		EmployeeCard card = cardService.getSingleEmployeeCardById(id); 
		
		if(Boolean.TRUE.equals(card.getActive())) {
			card.topupBalance(topupAmount); 
			cardService.saveCard(card); 
			return card.getBalance(); 
		}
		else 
			throw new ResourceNotFoundException("Card with the provided id not found, please register your card"); 
	}
	
	/**
	 * 
	 * @param id
	 * @return balance 
	 */
	// work on this method -> add extra check for if active then ... 
	@GetMapping(value = "/balance/{id}")
	public Double viewBalance(@PathVariable(value = "id") Integer id) {
		EmployeeCard card = cardService.getSingleEmployeeCardById(id); 
		if(card != null) {
			return cardService.getCardBalanceById(id); 
		}
		else 
			throw new ResourceNotFoundException("Card with the provided id not found, please register your card"); 
	}
	
	/**
	 * 
	 * @param cardNumber
	 * @return scan card and check if card is registered on the system.if card is registered show welcome message
	 */
	@GetMapping(value = "/scan/{cardNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String scan(@PathVariable(value = "cardNumber") String cardNumber) {
		EmployeeCard card = cardService.getSingleEmployeeCardByCardNumber(cardNumber); 
				
		if(card.getDataCard() != null) {
			return WELCOME_MESSAGE + card.getName(); 
		}
		else 
			throw new ResourceNotFoundException("Card not found, please register"); 
	}
	
	@PostMapping(value = "/pin/{cardNumber}", consumes = MediaType.APPLICATION_JSON_VALUE, 
				produces = MediaType.APPLICATION_JSON_VALUE)
	public void pin(@PathVariable(value = "cardNumber") String cardNumber) {
		EmployeeCard card = cardService.getSingleEmployeeCardByCardNumber(cardNumber); 
		
		if(Boolean.TRUE.equals(card.getActive())) {
			card.setPin(card.getPin());
			cardService.saveCard(card); 
		}
		else 
			throw new ResourceNotFoundException("Card not registered, please register your card"); 
	}
	
	
	@GetMapping(value = "/scan/scan/logout/{cardNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String logoutEmployeeCard(@PathVariable(value = "cardNumber") String cardNumber) {
		EmployeeCard card = cardService.getSingleEmployeeCardByCardNumber(cardNumber); 
		
		if(card != null) {
			return GOODBYE_MESSAGE + card.getName(); 
		}
		else 
			throw new ResourceNotFoundException("Card not found, please register"); 
	}
}
