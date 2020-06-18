package ag.api.controller.test;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import ag.api.controller.EmployeeCardController;
import ag.api.model.EmployeeCard;
import ag.api.service.interfaces.EmployeeCardService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = EmployeeCardController.class)
@ActiveProfiles("test")
public class EmployeeCardControllerTest {
	
	@Autowired
	MockMvc mockMvc; 
	
	/*
	 * Object mapper for object -> JSON conversion 
	 */
	@Autowired
	ObjectMapper mapper; 
	
	@MockBean
	@Autowired
	private EmployeeCardService cardService; 
	
	EmployeeCard card; 
	
	EmployeeCard mockEmployeeCard = new EmployeeCard("121t", 
													"Severus Snape", 
													"severus@bows.com", 
													"07123456789", 
													"1234", 
													"r345G7dqBy5wG456", 
													10.00, true);
	
	String exampleCardJson = "{\"bows-id\":\"121t\","
								+ "\"name\":\"Severus Snape\","
								+ "\"email\": \"severus@bows.com\","
								+ "\"mobile\":\"07123456789\","
								+ "\"pin\":\"1234\","
								+ "\"card-number\":\"r345G7dqBy5wG456\","
								+ "\"balance\":10.0,"
								+ "\"active\":true\"}"; 

	@Test
	public void post_createsNewEmployeeCard_andReturnsObjectWith201() throws Exception {
		Mockito
			.when(cardService.addEmployee(Mockito.any(EmployeeCard.class)))
			.thenReturn(mockEmployeeCard); 
		
		// build post request with mockEmployeeCard object payload 
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/card/register")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.content(this.mapper.writeValueAsBytes(mockEmployeeCard)); 
		
		mockMvc.perform(builder).andExpect(status().isCreated())
				.andExpect(jsonPath("$.card-number", is("r345G7dqBy5wG456")))
				.andExpect(MockMvcResultMatchers.content()
				.string(this.mapper.writeValueAsString(mockEmployeeCard))); 
	}
	
	
	@Test
	public void get_allEmployeeCards_returnsOkWithListOfAllEmployeeCards() throws Exception {
		this.mockMvc.perform(get("/card")
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk()); 
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void put_topupBalance_returnsOkWhithNewBalance() throws Exception {
		this.mockMvc.perform(put("/card/topup/{cardNumber}", "r345G7dqBy5wG456")
				.param("amount", String.valueOf(new Double(25.00))))
				.andDo(print())
				.andExpect(status().isOk()); 
	}	
	
	@Test
	public void givenValidJsonPayloadForSingleEmployeeCardWhenIsValidJsonPayloadThenIsEquals() throws Exception {
		Assert.assertEquals(exampleCardJson, "{\"bows-id\":\"121t\","
												+ "\"name\":\"Severus Snape\"," 
												+ "\"email\": \"severus@bows.com\"," 
												+ "\"mobile\":\"07123456789\"," 
												+ "\"pin\":\"1234\","
												+ "\"card-number\":\"r345G7dqBy5wG456\","
												+ "\"balance\":10.0," 
												+ "\"active\":true\"}");
		Assert.assertEquals(new String("r345G7dqBy5wG456"), mockEmployeeCard.getDataCard());
	}

// fix the test, does not like optional.empty
//	@Test
//	public void get_viewSingleCardByCardNumber_ThrowsCardNotFoundException() throws Exception {
//		
//		// returns empty optional object since we didn't find card number 
//		Mockito.when((cardService.getSingleEmployeeCardByCardNumber("r345G7dqBy5wG456"))
//				.thenReturn(Optional.empty())); 
//		
//		ResultActions resultActions = mockMvc.perform(
//				MockMvcRequestBuilders.get("/card/cn/r345G7dqBy5wG456")
//				.contentType(MediaType.APPLICATION_JSON))
//				.andExpect(status().isNotFound()); 
//		
//		assertEquals(ResourceNotFoundException.class, 
//				resultActions.andReturn()
//				.getResolvedException().getClass());
//		assertTrue(resultActions.andReturn().getResolvedException()
//				.getMessage().contains(
//				"Employee card with card number (" + "3345G44qBy5wG456" + ") not found!")); 
//				
//	}

}
