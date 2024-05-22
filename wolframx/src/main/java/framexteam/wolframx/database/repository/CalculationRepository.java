package framexteam.wolframx.database.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import framexteam.wolframx.database.entity.Calculation;
import framexteam.wolframx.database.entity.User;

@Repository
public interface CalculationRepository extends JpaRepository<Calculation, Long>{
    //Calculation findByUser(User user);
}