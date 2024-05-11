package framexteam.wolframx.calculations.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.*;

import framexteam.wolframx.calculations.operations.calculator.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/calculations/calculator")
public class CalculatorController {
    
    @PostMapping
    public ResponseEntity<?> calculate(@RequestBody CalculatorRequest request) {
        String tokens[] = FunctionParser.infixToRpn(request.getExpression());
        double result = BasicCalculator.solve(tokens);
        CalculatorResponse response = new CalculatorResponse();
        response.setResult(result);
        return ResponseEntity.ok(response);
    }

    @Getter
    @Setter
    private static class CalculatorRequest {
        String expression;
    }

    @Getter
    @Setter
    private static class CalculatorResponse {
        double result;
    }
}
