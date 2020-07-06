package ag.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ag.api.exception.ResourceNotFoundException;
import ag.api.exception.UserNotFoundException;
import ag.api.model.EmployeeCard;
import ag.api.service.interfaces.EmployeeCardService;
import ag.api.util.SessionData;

@RestController
@RequestMapping(value = "/card")
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
	@PostMapping(value = "/register", 
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EmployeeCard> register(@Valid @RequestBody EmployeeCard employeeDetails) {
		return new ResponseEntity<EmployeeCard>(cardService.addEmployee(employeeDetails), HttpStatus.CREATED); 
	}
	
	/**
	 * 
	 * @param cardNumber
	 * @return fetch single employee card by card number
	 */
	@GetMapping(value = "/cn/{cardNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EmployeeCard> viewSingleCard(@PathVariable(value = "cardNumber") String cardNumber) {
		EmployeeCard card = cardService.getSingleEmployeeCardByCardNumber(cardNumber); 
		return new ResponseEntity<EmployeeCard>(card, HttpStatus.OK); 
	}
	
	
	/**
	 * 
	 * @param id
	 * @return fetch single employee card by id 
	 */
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EmployeeCard> viewCard(@PathVariable(value = "id") Integer id) {
		EmployeeCard card = cardService.getSingleEmployeeCardById(id); 
		return new ResponseEntity<EmployeeCard>(card, HttpStatus.OK); 
	}
	
	/**
	 * 
	 * @return fetch all employee cards 
	 */
	@GetMapping(value = {"", "/"}, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<EmployeeCard>> viewAll(){
		List<EmployeeCard> cardList = cardService.getAllEmployeeCards(); 
		
		for(EmployeeCard item: cardList) {
			// add self link 'product' singular resource 
			// add RepresentationModel to inherit from Product, so  So once we create a link, so
			// we can easily set that value to the resource representation without adding any new fields to it.
			Link getLink = WebMvcLinkBuilder
							.linkTo(EmployeeCardController.class) //the linkTo() method inspects the controller class and obtains its root mapping
							.slash(item.getId()) // adds the productId value as the paths variable of the link 
							.withSelfRel() // qualifies relation as a self link 
							.withTitle("Get card details"); 
			
			// add a link to a singular resource 
			item.add(getLink); 
		}
		return new ResponseEntity<List<EmployeeCard>>(cardList, HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param cardNumber
	 * @param topupAmount
	 * @return new balance
	 */
	@PutMapping(value = "/topup/{cardNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Double> topupBalance(@PathVariable(value = "cardNumber") String cardNumber, @RequestParam(value = "amount") Double topupAmount) {
		Double card = cardService.topupBalanceByCardNumber(cardNumber, topupAmount); 
		return new ResponseEntity<Double>(card, HttpStatus.OK);
	}
		
	/**
	 *  
	 * @param id
	 * @param topupAmount
	 * @return new balance
	 */
	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Double topup(@PathVariable(value = "id") Integer id, @RequestParam(value = "amount") Double topupAmount) {
		EmployeeCard card = cardService.getSingleEmployeeCardById(id); 
		
		if(card != null && card.getActive()) {
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
	@GetMapping(value = "/balance/{id}")
	public Double viewBalance(@PathVariable(value = "id") Integer id) {
		return cardService.getCardBalanceById(id); 
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
			
			if(card.getDataCard().equals(SessionData.getInstance().getCardNumber())) {
				// scanned card matches last card scanned, so it is second tap to singout, 
				// so clear card number from session 
				SessionData.getInstance().setCardNumber("");
				return GOODBYE_MESSAGE + card.getName(); 
			}
			else {
				// scanned card does not match session, so it is a tap in event, first logout previous card
				if(SessionData.getInstance().getCardNumber().length() > 0) {
					return GOODBYE_MESSAGE + SessionData.getInstance().getCardNumber(); 
				}
			}
			
			// login a new card 
			SessionData.getInstance().setCardNumber(cardNumber);
			return WELCOME_MESSAGE + card.getName(); 
		}
		else 
			throw new ResourceNotFoundException("Card not found, please register"); 
	}
	
	/**
	 * 
	 * @param id
	 * delete single card resource 
	 */
	@DeleteMapping(value = "/remove/{id}")
	public void deleteCard(@PathVariable(value = "id") Integer id) {
		cardService.removeSingleEmployeeCardById(id);
	}
	
	
}
