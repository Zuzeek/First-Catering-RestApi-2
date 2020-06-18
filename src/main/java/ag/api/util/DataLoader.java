//package ag.api.util;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import ag.api.MembershipSystemApi1Application;
//import ag.api.model.EmployeeCard;
//import ag.api.service.interfaces.EmployeeCardService;
//
//@Component
//public class DataLoader implements CommandLineRunner {
//
//	private static final Logger LOG =  LoggerFactory.getLogger(MembershipSystemApi1Application.class);
//	
//	@Autowired
//	private EmployeeCardService cardService; 
//	
//	@Override
//	public void run(String... args) throws Exception {
//		
//		// load some employees 
//		LOG.info("Preloading: " + cardService.addEmployee(new EmployeeCard("121d", "Severus Snape", "severus@bows.com", "07123456789", "r345G7dqBy5wG456", 10.00, true)));
//		LOG.info("Preloading: " + cardService.addEmployee(new EmployeeCard("121d", "Albus Dumbledor", "albus@bows.com", "07123456465", "r345G7dqBy5w2344", 10.00, true)));
//		LOG.info("Preloading: " + cardService.addEmployee(new EmployeeCard("121d", "Draco Malfoy", "draco@bows.com", "07123456987", "r345G098By5wG123", 10.00, true)));
//
//	}
//}