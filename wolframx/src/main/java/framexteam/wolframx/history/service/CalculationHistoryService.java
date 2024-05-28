package framexteam.wolframx.history.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import framexteam.wolframx.authentication.service.UserService;
import framexteam.wolframx.database.entity.Calculation;
import framexteam.wolframx.database.entity.User;
import framexteam.wolframx.database.repository.CalculationRepository;

@Service
public class CalculationHistoryService {

    @Autowired
    private CalculationRepository repository;

    @Autowired
    private UserService userService;

    public void saveCalculationToHistory(String operation, String task, String result, String email) {
        if (email == null) {
            return;
        }

        User user = userService.getUserByEmail(email);

        Calculation calculation = new Calculation();
        calculation.setUser(user);
        calculation.setOperation(operation);
        calculation.setTask(task);
        calculation.setResult(result);
        repository.save(calculation);
    }

    public List<Calculation> getCalculationHistory(User user) {
        return repository.findLast10ByUser(user);
    }
}