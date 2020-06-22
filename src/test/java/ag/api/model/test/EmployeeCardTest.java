//package ag.api.model.test;
//
//import static org.junit.Assert.assertTrue;
//
//import java.util.Set;
//
//import javax.validation.ConstraintViolation;
//import javax.validation.Validation;
//import javax.validation.Validator;
//import javax.validation.ValidatorFactory;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.junit.MockitoJUnitRunner;
//
//import ag.api.model.EmployeeCard;
//
//@RunWith(MockitoJUnitRunner.class)
//public class EmployeeCardTest {
//	
//	private Validator validator; 
//	
//	@Before
//	public void setUp() {
//		ValidatorFactory factory = Validation.buildDefaultValidatorFactory(); 
//		validator = factory.getValidator(); 
//	}
//	
//	@Test
//	public void givenValidEmployeDetailsWhenIsValidCharacterSetThenIsTrue() {
//		EmployeeCard employeeDetails = new EmployeeCard(); 
//		employeeDetails.setName("Tom Riddle");
//		employeeDetails.setBowsEmployeeId("121p");
//		employeeDetails.setEmail("tom.riddle@bows.com");
//		employeeDetails.setMobile("07123456789");
//		employeeDetails.setDataCard("r7jTG5dqBy5wGO4L");
//		employeeDetails.setBalance(10.00);
//		employeeDetails.setActive(true);
//		
//		Set<ConstraintViolation<EmployeeCard>> violations = validator.validate(employeeDetails); 
//		assertTrue(violations.isEmpty());
//	}
//}
