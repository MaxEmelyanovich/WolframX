package framexteam.wolframx.calculations.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.*;
import framexteam.wolframx.calculations.operations.calculator.*;
import framexteam.wolframx.history.service.CalculationHistoryService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/calculations/calculator")
public class CalculatorController {

    @Autowired
    private CalculationHistoryService calculationHistoryService;
    
    @PostMapping
    public ResponseEntity<?> calculate(@RequestBody CalculatorRequest request) {

        String tokens[] = FunctionParser.infixToRpn(request.getExpression());
        double result = BasicCalculator.solve(tokens);

        CalculatorResponse response = new CalculatorResponse();
        response.setResult(result);
        calculationHistoryService.saveCalculationToHistory(request.toString(), response.toString(), request.getEmail());

        return ResponseEntity.ok(response);
    }

    @Getter
    @Setter
    @ToString
    private static class CalculatorRequest {
        String expression;
        String email;
    }

    @Getter
    @Setter
    @ToString
    private static class CalculatorResponse {
        double result;
    }
}
