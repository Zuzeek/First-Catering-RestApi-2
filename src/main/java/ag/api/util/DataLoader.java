package ag.api.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import ag.api.MembershipSystemApi1Application;
import ag.api.model.EmployeeCard;
import ag.api.service.interfaces.EmployeeCardService;

@Component
public class DataLoader implements CommandLineRunner {

	private static final Logger LOG =  LoggerFactory.getLogger(MembershipSystemApi1Application.class);
	
	@Autowired
	private EmployeeCardService cardService; 
	
	@Override
	public void run(String... args) throws Exception {
		
		// load some employees 
		LOG.info("Preloading: " + cardService.addEmployee(new EmployeeCard("131a", "Severus Snape", "severus@bows.com", "07123456789", "1234", "17jTG7dqBy5wGO4L", 10.00, true)));
		LOG.info("Preloading: " + cardService.addEmployee(new EmployeeCard("151b", "Albus Dumbledor", "albus@bows.com", "07987654321", "2345","27jTG7dqBy5wGO4L", 10.00, true)));
		LOG.info("Preloading: " + cardService.addEmployee(new EmployeeCard("161c", "Draco Malfoy", "draco@bows.com", "07654789321", "3455", "37jTG7dqBy5wGO4L", 10.00, true)));

	}
}