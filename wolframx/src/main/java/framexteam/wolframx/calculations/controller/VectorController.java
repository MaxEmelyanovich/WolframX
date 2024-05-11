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

import framexteam.wolframx.calculations.operations.vectors.VectorLibrary;
import framexteam.wolframx.calculations.operations.vectors.VectorLibraryJNIWrapper;
import framexteam.wolframx.calculations.operations.vectors.VectorLibraryJava;
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

    @PostMapping("/sum")
    @Operation(summary = "Сложение векторов", description = "Вычисляет сумму двух векторов.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Результат сложения векторов",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = VectorResultResponse.class))),
            @ApiResponse(responseCode = "400", description = "Ошибка в формате входных данных")
    })
    public ResponseEntity<?> sumVectors(@RequestBody VectorRequest request) {
        return performVectorOperation(request, "sum");
    }

    @PostMapping("/sub")
    @Operation(summary = "Вычитание векторов", description = "Вычисляет разность двух векторов.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Результат вычитания векторов",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = VectorResultResponse.class))),
            @ApiResponse(responseCode = "400", description = "Ошибка в формате входных данных")
    })
    public ResponseEntity<?> subVectors(@RequestBody VectorRequest request) {
        return performVectorOperation(request, "sub");
    }

    @PostMapping("/scalarMul")
    @Operation(summary = "Скалярное произведение векторов",
            description = "Вычисляет скалярное произведение двух векторов.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Результат скалярного произведения векторов",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ScalarResultResponse.class))),
            @ApiResponse(responseCode = "400", description = "Ошибка в формате входных данных")
    })
    public ResponseEntity<?> scalarMulVectors(@RequestBody VectorRequest request) {
        return performVectorOperation(request, "scalarMul");
    }

    @PostMapping("/vectorMul")
    @Operation(summary = "Векторное произведение векторов",
            description = "Вычисляет векторное произведение двух векторов.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Результат векторного произведения векторов",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = VectorResultResponse.class))),
            @ApiResponse(responseCode = "400", description = "Ошибка в формате входных данных")
    })
    public ResponseEntity<?> vectorMulVectors(@RequestBody VectorRequest request) {
        return performVectorOperation(request, "vectorMul");
    }

    @PostMapping("/numberMul")
    @Operation(summary = "Умножение вектора на число",
            description = "Вычисляет произведение вектора на число.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Результат умножения вектора на число",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = VectorResultResponse.class))),
            @ApiResponse(responseCode = "400", description = "Ошибка в формате входных данных")
    })
    public ResponseEntity<?> numberMulVector(@RequestBody VectorRequest request) {
        return performVectorOperation(request, "numberMul");
    }

    @PostMapping("/numberDiv")
    @Operation(summary = "Деление вектора на число", description = "Вычисляет частное вектора и числа.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Результат деления вектора на число",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = VectorResultResponse.class))),
            @ApiResponse(responseCode = "400", description = "Ошибка в формате входных данных")
    })
    public ResponseEntity<?> numberDivVector(@RequestBody VectorRequest request) {
        return performVectorOperation(request, "numberDiv");
    }

    @PostMapping("/abs")
    @Operation(summary = "Длина вектора", description = "Вычисляет длину вектора.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Длина вектора",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ScalarResultResponse.class))),
            @ApiResponse(responseCode = "400", description = "Ошибка в формате входных данных")
    })
    public ResponseEntity<?> absVector(@RequestBody VectorRequest request) {
        return performVectorOperation(request, "abs");
    }

    @PostMapping("/angle")
    @Operation(summary = "Угол между векторами", description = "Вычисляет угол между двумя векторами.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Угол между векторами",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ScalarResultResponse.class))),
            @ApiResponse(responseCode = "400", description = "Ошибка в формате входных данных")
    })
    public ResponseEntity<?> angleVectors(@RequestBody VectorRequest request) {
        return performVectorOperation(request, "angle");
    }

    private ResponseEntity<?> performVectorOperation(VectorRequest request, String operationName) {
        try {
            logger.info("Received request to perform {} operation: {}", operationName, request);

            double[] v1 = request.getVector1AsArray();
            double[] v2 = request.getVector2AsArray();
            double number = request.getNumber();
            String language = request.getLanguage();

            VectorLibrary vectorLibrary = getVectorLibrary(language);

            Object result;

            switch (operationName) {
                case "sum":
                    result = vectorLibrary.sum(v1, v2);
                    break;
                case "sub":
                    result = vectorLibrary.sub(v1, v2);
                    break;
                case "scalarMul":
                    result = vectorLibrary.scalarMul(v1, v2);
                    break;
                case "vectorMul":
                    result = vectorLibrary.vectorMul(v1, v2);
                    break;
                case "numberMul":
                    result = vectorLibrary.numberMul(v1, number);
                    break;
                case "numberDiv":
                    result = vectorLibrary.numberDiv(v1, number);
                    break;
                case "abs":
                    result = vectorLibrary.abs(v1);
                    break;
                case "angle":
                    result = vectorLibrary.angle(v1, v2);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid vector operation: " + operationName);
            }

            if (result instanceof double[]) {
                VectorResultResponse response = new VectorResultResponse();
                response.setVectorResult((double[]) result);
                logger.info("{} operation completed successfully.", operationName);
                return ResponseEntity.ok(response);
            } else if (result instanceof Double) {
                ScalarResultResponse response = new ScalarResultResponse();
                response.setScalarResult((double) result);
                logger.info("{} operation completed successfully.", operationName);
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
        private String vector1;
        private String vector2;
        private double number;
        private String language;

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
            return getVectorAsArray(vector2);
        }
    }

    @Getter
    @Setter
    private static class VectorResultResponse {
        private String vectorResult;

        public void setVectorResult(double[] vectorResult) {
            this.vectorResult = arrayToString(vectorResult);
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
    }

    @Getter
    @Setter
    private static class ScalarResultResponse {
        private double scalarResult;
    }
}