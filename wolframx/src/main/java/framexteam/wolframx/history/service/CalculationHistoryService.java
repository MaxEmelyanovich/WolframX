package framexteam.wolframx.history.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public void saveCalculationToHistory(String task, String result) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return;
        }

        String email = authentication.getName();
        User user = userService.getUserByEmail(email);

        Calculation calculation = new Calculation();
        calculation.setUser(user);
        calculation.setTask(task);
        calculation.setTask(result);
        repository.save(calculation);

    }
}
