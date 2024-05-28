package framexteam.wolframx.database.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import framexteam.wolframx.database.entity.Calculation;
import framexteam.wolframx.database.entity.User;

@Repository
public interface CalculationRepository extends JpaRepository<Calculation, Long>{
    @Query("SELECT c FROM Calculation c WHERE c.user = :user ORDER BY c.calculationId DESC LIMIT 10")
    //@Query("SELECT c FROM Calculation c WHERE c.user = :user")
    List<Calculation> findLast10ByUser(User user);
}