package framexteam.wolframx.calculations.controller;

import java.util.Set;

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
import framexteam.wolframx.calculations.operations.equations.GaussSolverJNI;
import framexteam.wolframx.calculations.operations.equations.NonlinearEquationSolver;
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
            String language = request.getLanguage();
            double[] solution;
            long elapsedTime = 0;
            
            if ("C++".equalsIgnoreCase(language)) {
                solution = GaussSolverJNI.solve(coefficients, constants, threads);
                elapsedTime = GaussSolverJNI.getExecutionTime();
            }
            else {
                solution = GaussSolver.solve(coefficients, constants, threads);
                elapsedTime = GaussSolver.getElapsedTime();
            }

            EquationSolverResponse response = new EquationSolverResponse();
            response.setArraySolution(solution);
            response.setElapsedTime(elapsedTime);

            logger.info("Equations solved successfully.");
            return ResponseEntity.ok(response);
        } catch (InterruptedException | RuntimeException e) {
            logger.error("Error during equations solving: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/newton")
    @Operation(summary = "Решение нелинейного уравнения",
               description = "Решает нелинейное уравнение методом Ньютона.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Корни уравнения",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = EquationSolverResponse.class))),
        @ApiResponse(responseCode = "400", description = "Ошибка в формате входных данных"),
        @ApiResponse(responseCode = "500", description = "Ошибка при решении уравнения")
    })
    public ResponseEntity<?> solveNonlinearEquation(@RequestBody NonlinearEquationSolverRequest request) {
        EquationSolverResponse response = new EquationSolverResponse();
        try {
            logger.info("Received request to solve nonlinear equation: {}", request);

            double[] coefficients = request.getCoefficientsAsArray();
            int threads = request.getThreads();
            double epsilon = request.getEpsilon();
            int maxIterations = request.getMaxIterations();
            Set<Double> roots;
            roots = NonlinearEquationSolver.solve(coefficients, threads, epsilon, maxIterations);
            
            
            response.setSolution(roots.toString());

            response.setElapsedTime(NonlinearEquationSolver.getElapsedTime());

            logger.info("Nonlinear equation solved successfully.");
        } catch (Exception e) {
            logger.error("Error during nonlinear equation solving: {}", e.getMessage());
            response.setSolution(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    @Getter
    @Setter
    private static class EquationSolverRequest {
        private String coefficients;
        private String constants;
        private int threads;
        private String language;

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
        private long elapsedTime;

        public void setArraySolution(double[] solutionArray) {
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

    @Getter
    @Setter
    private static class NonlinearEquationSolverRequest {
        private String coefficients;
        private int threads;
        private double epsilonDegree;
        private int maxIterations;

        public double getEpsilon() {
            return Math.pow(10, -epsilonDegree);
        }

        private double[] getCoefficientsAsArray() {
            String[] parts = coefficients.substring(1, coefficients.length() - 1).split(",");
            double[] array = new double[parts.length];
            for (int i = 0; i < parts.length; i++) {
                array[i] = Double.parseDouble(parts[i]);
            }
            return array;
        }
    }
}