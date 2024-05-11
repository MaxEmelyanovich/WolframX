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

import framexteam.wolframx.calculations.operations.equations.GaussSolver;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/calculations/equations")
@Tag(name = "Вычисления", description = "API для выполнения математических операций")
public class EquationSolverController {

    private static final Logger logger = LogManager.getLogger(EquationSolverController.class);

    @PostMapping("/gauss")
    @Operation(summary = "Решение системы линейных уравнений",
               description = "Решает систему линейных уравнений методом Гаусса.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Решение системы уравнений", 
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = EquationSolverResponse.class))),
        @ApiResponse(responseCode = "400", description = "Ошибка в формате входных данных")
    })
    public ResponseEntity<?> solveEquations(@RequestBody EquationSolverRequest request) {
        try {
            logger.info("Received request to solve equations: {}", request);

            double[][] coefficients = request.getCoefficientsAsArray();
            double[] constants = request.getConstantsAsArray();
            int threads = request.getThreads();

            double[] solution = GaussSolver.solve(coefficients, constants, threads);

            EquationSolverResponse response = new EquationSolverResponse();
            response.setSolution(solution);

            logger.info("Equations solved successfully.");
            return ResponseEntity.ok(response);
        } catch (InterruptedException | IllegalArgumentException e) {
            logger.error("Error during equations solving: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Getter
    @Setter
    private static class EquationSolverRequest {
        private String coefficients;
        private String constants;
        private int threads;

        private double[][] getCoefficientsAsArray() {
            return getArrayFromString(coefficients);
        }

        private double[] getConstantsAsArray() {
            return getArrayFromString(constants)[0]; // Constants are represented as a single row
        }

        private double[][] getArrayFromString(String arrayString) {
            // Извлечение содержимого массива из строки
            int startIndex = arrayString.indexOf("{") + 1;
            int endIndex = arrayString.lastIndexOf("}");
            String arrayContent = arrayString.substring(startIndex, endIndex);

            // Разделение строки на строки массива
            String[] rows = arrayContent.split("},\\{");

            // Определение размера массива
            int numRows = rows.length;
            int numCols = rows[0].split(",").length;

            // Создание двумерного массива для хранения массива
            double[][] array = new double[numRows][numCols];

            // Заполнение двумерного массива значениями из строки
            for (int i = 0; i < numRows; i++) {
                String[] elements = rows[i].split(",");
                for (int j = 0; j < numCols; j++) {
                    array[i][j] = Double.parseDouble(elements[j]);
                }
            }

            return array;
        }
    }

    @Getter
    @Setter
    private static class EquationSolverResponse {
        private String solution;

        public void setSolution(double[] solutionArray) {
            StringBuilder sb = new StringBuilder();
            sb.append("{");
            for (int i = 0; i < solutionArray.length; i++) {
                sb.append(solutionArray[i]);
                if (i < solutionArray.length - 1) {
                    sb.append(",");
                }
            }
            sb.append("}");
            solution = sb.toString();
        }
    }
}