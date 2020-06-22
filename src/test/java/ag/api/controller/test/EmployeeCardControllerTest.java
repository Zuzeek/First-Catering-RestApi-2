//package ag.api.controller.test;
//
//import static org.hamcrest.CoreMatchers.is;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import javax.sql.DataSource;
//
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import ag.api.controller.EmployeeCardController;
//import ag.api.model.EmployeeCard;
//import ag.api.service.interfaces.EmployeeCardService;
//
//@RunWith(SpringRunner.class)
//@WebMvcTest(value = EmployeeCardController.class)
//@ActiveProfiles("test")
//public class EmployeeCardControllerTest {
//	
//	@Autowired
//	MockMvc mockMvc; 
//	@Autowired
//	WebApplicationContext webAppContext; 
//	
//	@Autowired
//	private ObjectMapper mapper; // Object mapper for object -> JSON conversion 
//	
//	@MockBean
//	@Autowired
//	private EmployeeCardService cardService; 
//	
//	@MockBean
//	private DataSource dataSource; 
//	@MockBean
//	private BCryptPasswordEncoder passwordEncoder; 
//	
//	
//	@InjectMocks
//	EmployeeCardController cardController; 
//	
//	EmployeeCard card; 
//	Double amount = 25.00; 
//	EmployeeCard mockEmployeeCard = new EmployeeCard("121t", 
//													"Severus Snape", 
//													"severus@bows.com", 
//													"07123456789", 
//													"1234", 
//													"r345G7dqBy5wG456", 
//													10.00, true);
//	
//	String exampleCardJson = "{\"bows-id\":\"121t\","
//								+ "\"name\":\"Severus Snape\","
//								+ "\"email\": \"severus@bows.com\","
//								+ "\"mobile\":\"07123456789\","
//								+ "\"pin\":\"1234\","
//								+ "\"card-number\":\"r345G7dqBy5wG456\","
//								+ "\"balance\":10.0,"
//								+ "\"active\":true\"}"; 
//
//	@Before
//	public void setUp() throws Exception {
//		mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build(); 
//		
//		EmployeeCard employeeDetails = new EmployeeCard(); 
//		
//		employeeDetails.setId(456);
//		employeeDetails.setActive(true);
//		employeeDetails.setName("test");
//		employeeDetails.setBowsEmployeeId("tes1");
//		employeeDetails.setDataCard("r345G7dqBy5wG400");
//		employeeDetails.setEmail("test@test.com");
//		employeeDetails.setMobile("07987654321");
//		employeeDetails.setBalance(100.00);
//		employeeDetails.setPin("1234");
//		
//		cardService.addEmployee(employeeDetails); 
//	}
//	
//	
//	@Test
//	public void givenValidInputWhenCreatingNewEmployeeCardThenReturnsObjectWith201() throws Exception {
//		Mockito
//			.when(cardService.addEmployee(Mockito.any(EmployeeCard.class)))
//			.thenReturn(mockEmployeeCard); 
//		
//		// build post request with mockEmployeeCard object payload 
//		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/card/register")
//				.contentType(MediaType.APPLICATION_JSON_VALUE)
//				.accept(MediaType.APPLICATION_JSON)
//				.characterEncoding("UTF-8")
//				.content(this.mapper.writeValueAsBytes(mockEmployeeCard)); 
//		
//		mockMvc.perform(builder).andExpect(status().isCreated())
//				.andExpect(jsonPath("$.card-number", is("r345G7dqBy5wG456")))
//				.andExpect(MockMvcResultMatchers.content()
//				.string(this.mapper.writeValueAsString(mockEmployeeCard))); 
//	}
//	
//	
//	@Test
//	public void givenValidInputWhenFetchingAllEmployeeCardsThenReturnsListOfAllEmployeesWith200() throws Exception {
//		this.mockMvc.perform(get("/card")
//				.accept(MediaType.APPLICATION_JSON))
//				.andExpect(status().isOk())
//				.andDo(print()); 
//	}
//	
//	@Test
//	public void givenValidTopupAmountWhenFetchingTopupAmountThenObjectReturnsWithOk() throws Exception {
//		mockMvc.perform(put("/card/topup/{cardNumber}", "r345G7dqBy5wG456")
//				.param("amount", String.valueOf(amount)))
//				.andExpect(status().isOk())
//				.andDo(print()); 
//	}	
//	
//	@Test 
//	public void givenPutMethodWhenEmployeeTopupBalanceThenExpectedStatusIs200() throws Exception {
//		mockMvc.perform(put("/card/{id}", 1)
//				.param("amount", String.valueOf(amount)))
//				.andExpect(status().isOk())
//				.andDo(print()); 
//	}
//	
//	@Test
//	public void givenValidJsonPayloadForSingleEmployeeCardWhenIsValidJsonPayloadThenIsEquals() throws Exception {
//		Assert.assertEquals(exampleCardJson, "{\"bows-id\":\"121t\","
//												+ "\"name\":\"Severus Snape\"," 
//												+ "\"email\": \"severus@bows.com\"," 
//												+ "\"mobile\":\"07123456789\"," 
//												+ "\"pin\":\"1234\","
//												+ "\"card-number\":\"r345G7dqBy5wG456\","
//												+ "\"balance\":10.0," 
//												+ "\"active\":true\"}");
//		Assert.assertEquals(new String("r345G7dqBy5wG456"), mockEmployeeCard.getDataCard());
//	}
//}
//
//
//
//
