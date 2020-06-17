package ag.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ag.api.model.EmployeeCard;

@Repository
public interface EmployeeCardRepository extends JpaRepository<EmployeeCard, Integer>{

	EmployeeCard findByDataCard(String dataCard); 

}
