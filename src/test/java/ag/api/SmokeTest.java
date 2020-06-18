package ag.api;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ag.api.controller.EmployeeCardController;

@SpringBootTest
public class SmokeTest {
	
	@Autowired
	EmployeeCardController cardController; 
	
	@Test
	public void contexLoads() throws Exception {
		assertThat(cardController).isNotNull();
	}
}
