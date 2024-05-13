package framexteam.wolframx.calculations.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.*;

import framexteam.wolframx.calculations.operations.integrals.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/calculations/integrals")
@Tag(name = "Вычисления", description = "API для выполнения математических операций")
public class IntegralController {

    private static final Logger logger = LogManager.getLogger(IntegralController.class);

    @PostMapping("/trapezoidal")
    @Operation(summary = "Вычисление определенного интеграла методом трапеций",
               description = "Вычисляет определенный интеграл с использованием метода трапеций.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Результат вычисления интеграла",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = IntegralResponse.class))),
        @ApiResponse(responseCode = "400", description = "Ошибка в формате входных данных")
    })
    public ResponseEntity<?> calculateTrapezoidalIntegral(@RequestBody IntegralRequest integralRequest) {
        try {
            logger.info("Received request to calculate integral using trapezoidal method: {}", integralRequest);

            String function = integralRequest.getFunction();
            double start = integralRequest.getStart();
            double stop = integralRequest.getStop();

            int N = integralRequest.getN();


            IntegralOperation operation = new TrapezoidalMethod();
            double result = IntegralLibrary.performIntegralOperation(function, start, stop, operation, 0, N);

            IntegralResponse integralResponse = new IntegralResponse();
            integralResponse.setResult(result);

            logger.info("Integral calculation completed successfully.");
            return ResponseEntity.ok(integralResponse);
        } catch (IllegalArgumentException e) {
            logger.error("Error during integral calculation: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/simpson")
    @Operation(summary = "Вычисление определенного интеграла методом Симпсона",
               description = "Вычисляет определенный интеграл с использованием метода Симпсона.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Результат вычисления интеграла",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = IntegralResponse.class))),
        @ApiResponse(responseCode = "400", description = "Ошибка в формате входных данных")
    })
    public ResponseEntity<?> calculateSimpsonIntegral(@RequestBody IntegralRequest integralRequest) {
        try {
            logger.info("Received request to calculate integral using Simpson method: {}", integralRequest);

            String function = integralRequest.getFunction();
            double start = integralRequest.getStart();
            double stop = integralRequest.getStop();

            int N = integralRequest.getN();

            IntegralOperation operation = new SimpsonMethod();
            double result = IntegralLibrary.performIntegralOperation(function, start, stop, operation, 0, N);

            IntegralResponse integralResponse = new IntegralResponse();
            integralResponse.setResult(result);

            logger.info("Integral calculation completed successfully.");
            return ResponseEntity.ok(integralResponse);
        } catch (IllegalArgumentException e) {
            logger.error("Error during integral calculation: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping("/romberg")
    @Operation(summary = "Вычисление определенного интеграла методом Ромберга",
               description = "Вычисляет определенный интеграл с использованием метода Ромберга.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Результат вычисления интеграла",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = IntegralResponse.class))),
        @ApiResponse(responseCode = "400", description = "Ошибка в формате входных данных")
    })
    public ResponseEntity<?> calculateRombergIntegral(@RequestBody IntegralRequest integralRequest) {
        try {
            logger.info("Received request to calculate integral using Romberg method: {}", integralRequest);

            String function = integralRequest.getFunction();
            double start = integralRequest.getStart();
            double stop = integralRequest.getStop();

            double tolerance = integralRequest.getTolerance();

            int N = integralRequest.getN();

            IntegralOperation operation = new RombergMethod();
            double result = IntegralLibrary.performIntegralOperation(function, start, stop, operation, tolerance, N);

            IntegralResponse integralResponse = new IntegralResponse();
            integralResponse.setResult(result);

            logger.info("Integral calculation completed successfully.");
            return ResponseEntity.ok(integralResponse);
        } catch (IllegalArgumentException e) {
            logger.error("Error during integral calculation: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Getter
    @Setter
    private static class IntegralRequest {
        private String function;
        private double start;
        private double stop;
        private double tolerance;
        private int n;
    }

    @Getter
    @Setter
    private static class IntegralResponse {
        private double result;
    }
}