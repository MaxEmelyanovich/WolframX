package framexteam.wolframx.calculations.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.*;
import framexteam.wolframx.calculations.operations.vectors.VectorLibrary;
import framexteam.wolframx.calculations.operations.vectors.VectorLibraryJNIWrapper;
import framexteam.wolframx.calculations.operations.vectors.VectorLibraryJava;
import framexteam.wolframx.history.service.CalculationHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/calculations/vectors")
@Tag(name = "Вычисления", description = "API для выполнения математических операций с векторами")
public class VectorController {

    private static final Logger logger = LogManager.getLogger(VectorController.class);

    @Autowired
    private CalculationHistoryService calculationHistoryService;

    @PostMapping("/twoVectors")
    @Operation(summary = "Операции с двумя векторами", description = "Выполняет операции над двумя векторами.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Результат операции над векторами",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = VectorResponse.class))),
            @ApiResponse(responseCode = "400", description = "Ошибка в формате входных данных")
    })
    public ResponseEntity<?> twoVectorsOperation(@RequestBody VectorRequest request) {
        return performVectorOperation(request);
    }

    @PostMapping("/vectorAndNumber")
    @Operation(summary = "Операции с вектором и числом", description = "Выполняет операции над вектором и числом.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Результат операции над вектором и числом",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = VectorResponse.class))),
            @ApiResponse(responseCode = "400", description = "Ошибка в формате входных данных")
    })
    public ResponseEntity<?> vectorAndNumberOperation(@RequestBody VectorRequest request) {
        return performVectorOperation(request);
    }

    @PostMapping("/oneVector")
    @Operation(summary = "Операции с одним вектором", description = "Выполняет операции над одним вектором.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Результат операции над вектором",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = VectorResponse.class))),
            @ApiResponse(responseCode = "400", description = "Ошибка в формате входных данных")
    })
    public ResponseEntity<?> oneVectorOperation(@RequestBody VectorRequest request) {
        return performVectorOperation(request);
    }

    private ResponseEntity<?> performVectorOperation(VectorRequest request) {
        try {
            logger.info("Received request to perform {} operation: {}", request.getOperation(), request);

            double[] v1 = request.getVector1AsArray();
            double[] v2 = request.getVector2AsArray();
            double number = request.getNumber();
            String language = request.getLanguage();

            VectorLibrary vectorLibrary = getVectorLibrary(language);

            Object result = null;

            String operation;

            switch (request.getOperation()) {
                case "sum":
                    result = vectorLibrary.sum(v1, v2);
                    operation = "Vectors: Sum";
                    break;
                case "sub":
                    result = vectorLibrary.sub(v1, v2);
                    operation = "Vectors: Subtraction";
                    break;
                case "scalarMul":
                    result = vectorLibrary.scalarMul(v1, v2);
                    operation = "Vectors: Scalar Multiplication";
                    break;
                case "vectorMul":
                    result = vectorLibrary.vectorMul(v1, v2);
                    operation = "Vectors: Vector Multiplication";
                    break;
                case "numberMul":
                    result = vectorLibrary.numberMul(v1, number);
                    operation = "Vectors: Multiplication by Number";
                    break;
                case "numberDiv":
                    result = vectorLibrary.numberDiv(v1, number);
                    operation = "Vectors: Division by Number";
                    break;
                case "abs":
                    result = vectorLibrary.abs(v1);
                    operation = "Vectors: Module";
                    break;
                case "angle":
                    result = vectorLibrary.angle(v1, v2);
                    operation = "Vectors: Angle";
                    break;
                default:
                    throw new IllegalArgumentException("Invalid vector operation: " + request.getOperation());
            }

            VectorResponse response = new VectorResponse();

            if (result instanceof double[]) {
                response.setVectorResult((double[]) result);
                calculationHistoryService.saveCalculationToHistory(operation, request.toString(), response.toString(), request.getEmail());
                logger.info("{} operation completed successfully.", request.getOperation());
                return ResponseEntity.ok(response);
            } else if (result instanceof Double) {
                response.setScalarResult((double) result);
                calculationHistoryService.saveCalculationToHistory(operation, request.toString(), response.toString(), request.getEmail());
                logger.info("{} operation completed successfully.", request.getOperation());
                return ResponseEntity.ok(response);
            } else {
                throw new IllegalArgumentException("Unsupported result type: " + result.getClass().getName());
            }

        } catch (IllegalArgumentException e) {
            logger.error("Error during vector operation: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private VectorLibrary getVectorLibrary(String language){
        return "C++".equalsIgnoreCase(language)
                ? new VectorLibraryJNIWrapper()
                : new VectorLibraryJava();
    }

    @Getter
    @Setter
    private static class VectorRequest {
        private String operation;
        private String vector1;
        private String vector2;
        private double number;
        private String language;
        private String email;

        private double[] getVectorAsArray(String vector) {
            String[] parts = vector.substring(1, vector.length() - 1).split(",");
            double[] array = new double[parts.length];
            for (int i = 0; i < parts.length; i++) {
                array[i] = Double.parseDouble(parts[i]);
            }
            return array;
        }

        public double[] getVector1AsArray() {
            return getVectorAsArray(vector1);
        }

        public double[] getVector2AsArray() {
            if (vector2 == null) {
                return null;
            } else return getVectorAsArray(vector2);
        }

        @Override
        public String toString() {
            return "{" +
                    "operation='" + operation + '\'' +
                    ", vector1='" + vector1 + '\'' +
                    ", vector2='" + vector2 + '\'' +
                    ", number=" + number +
                    ", language='" + language + '\'' +
                    '}';
        }
    }

    @Getter
    @Setter
    private static class VectorResponse {
        private String result;

        public void setVectorResult(double[] vectorResult) {
            this.result = arrayToString(vectorResult);
        }

        public void setScalarResult(double scalarResult) {
            this.result = String.valueOf(scalarResult);
        }

        private String arrayToString(double[] array) {
            if (array == null) {
                return null;
            }

            StringBuilder sb = new StringBuilder();
            sb.append("{");
            for (int i = 0; i < array.length; i++) {
                sb.append(array[i]);
                if (i < array.length - 1) {
                    sb.append(",");
                }
            }
            sb.append("}");
            return sb.toString();
        }

        @Override
        public String toString() {
            return "{" +
                    "result='" + result + '\'' +
                    '}';
        }
    }
}