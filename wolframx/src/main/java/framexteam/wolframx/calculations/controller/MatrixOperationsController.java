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

import framexteam.wolframx.calculations.operations.matrices.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/calculations/matrices")
@Tag(name = "Вычисления", description = "API для выполнения математических операций")
public class MatrixOperationsController {

    private static final Logger logger = LogManager.getLogger(MatrixOperationsController.class);

    @PostMapping("/transpose")
    @Operation(summary = "Транспонирование матрицы",
               description = "Вычисляет транспонированную матрицу.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Транспонированная матрица", 
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = MatrixResponse.class))),
        @ApiResponse(responseCode = "400", description = "Ошибка в формате входных данных")
    })
    public ResponseEntity<?> transposeMatrix(@RequestBody MatrixRequest matrixRequest) {
        try {
            logger.info("Received request to transpose matrix: {}", matrixRequest.getMatrix1());

            int[][] matrix = matrixRequest.getMatrix1AsArray();
            int threads = matrixRequest.getThreads();

            MatrixOperation operation = new MatrixTranspose();
            int[][] resultMatrix = MatrixLibrary.performMatrixOperationMT(matrix, matrix, threads, operation);

            MatrixResponse matrixResponse = new MatrixResponse();
            matrixResponse.setResultMatrix(resultMatrix);

            logger.info("Transpose operation completed successfully.");
            return ResponseEntity.ok(matrixResponse);
        } catch (MatrixOperationException e) {
            logger.error("Error during matrix transpose operation: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/multiply")
    public ResponseEntity<?> multiplyMatrices(@RequestBody MatrixRequest matrixRequest) {
        try {
            logger.info("Received request to multiply matrices: {} and {}", matrixRequest.getMatrix1(), matrixRequest.getMatrix2());

            int[][] matrix1 = matrixRequest.getMatrix1AsArray();
            int[][] matrix2 = matrixRequest.getMatrix2AsArray();
            int threads = matrixRequest.getThreads();

            MatrixOperation operation = new MatrixMultiplication();
            int[][] resultMatrix = MatrixLibrary.performMatrixOperationMT(matrix1, matrix2, threads, operation);

            MatrixResponse matrixResponse = new MatrixResponse();
            matrixResponse.setResultMatrix(resultMatrix);

            logger.info("Matrix multiplication operation completed successfully.");
            return ResponseEntity.ok(matrixResponse);
        } catch (MatrixOperationException e) {
            logger.error("Error during matrix multiplication operation: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addMatrices(@RequestBody MatrixRequest matrixRequest) {
        try {
            logger.info("Received request to add matrices: {} and {}", matrixRequest.getMatrix1(), matrixRequest.getMatrix2());

            int[][] matrix1 = matrixRequest.getMatrix1AsArray();
            int[][] matrix2 = matrixRequest.getMatrix2AsArray();
            int threads = matrixRequest.getThreads();

            MatrixOperation operation = new MatrixAddition();
            int[][] resultMatrix = MatrixLibrary.performMatrixOperationMT(matrix1, matrix2, threads, operation);

            MatrixResponse matrixResponse = new MatrixResponse();
            matrixResponse.setResultMatrix(resultMatrix);

            logger.info("Matrix addition operation completed successfully.");
            return ResponseEntity.ok(matrixResponse);
        } catch (MatrixOperationException e) {
            logger.error("Error during matrix addition operation: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/substract")
    public ResponseEntity<?> subtractMatrices(@RequestBody MatrixRequest matrixRequest) {
        try {
            logger.info("Received request to subtract matrices: {} and {}", matrixRequest.getMatrix1(), matrixRequest.getMatrix2());

            int[][] matrix1 = matrixRequest.getMatrix1AsArray();
            int[][] matrix2 = matrixRequest.getMatrix2AsArray();
            int threads = matrixRequest.getThreads();

            MatrixOperation operation = new MatrixSubtraction();
            int[][] resultMatrix = MatrixLibrary.performMatrixOperationMT(matrix1, matrix2, threads, operation);

            MatrixResponse matrixResponse = new MatrixResponse();
            matrixResponse.setResultMatrix(resultMatrix);

            logger.info("Matrix subtraction operation completed successfully.");
            return ResponseEntity.ok(matrixResponse);
        } catch (MatrixOperationException e) {
            logger.error("Error during matrix subtraction operation: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/multiplybyscalar")
    public ResponseEntity<?> multiplyMatrixByScalar(@RequestBody MatrixRequest matrixRequest) {
        try {
            logger.info("Received request to multiply matrix by scalar: {} * {}", matrixRequest.getMatrix1(), matrixRequest.getScalar());

            int[][] matrix1 = matrixRequest.getMatrix1AsArray();

            int[][] scalarArray = new int[1][1];
            scalarArray[0][0] = matrixRequest.getScalar();

            int threads = matrixRequest.getThreads();

            MatrixOperation operation = new MatrixScalarMultiplication();
            int[][] resultMatrix = MatrixLibrary.performMatrixOperationMT(matrix1, scalarArray, threads, operation);

            MatrixResponse matrixResponse = new MatrixResponse();
            matrixResponse.setResultMatrix(resultMatrix);

            logger.info("Matrix scalar multiplication operation completed successfully.");
            return ResponseEntity.ok(matrixResponse);
        } catch (MatrixOperationException e) {
            logger.error("Error during matrix scalar multiplication operation: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Getter
    @Setter
    private static class MatrixRequest {
        private String matrix1;
        private String matrix2;
        private int threads;
        private int scalar;

        private int[][] getMatrixAsArray(String matrix) {
            // Извлечение содержимого матрицы из строки
            int startIndex = matrix.indexOf("{") + 1;
            int endIndex = matrix.lastIndexOf("}");
            String matrixContent = matrix.substring(startIndex, endIndex);

            // Разделение строки на строки матрицы
            String[] rows = matrixContent.split("},\\{");

            // Определение размера матрицы
            int numRows = rows.length;
            int numCols = rows[0].split(",").length;

            // Создание двумерного массива для хранения матрицы
            int[][] matrixArray = new int[numRows][numCols];

            // Заполнение двумерного массива значениями из строки
            for (int i = 0; i < numRows; i++) {
                String[] elements = rows[i].split(",");
                for (int j = 0; j < numCols; j++) {
                    matrixArray[i][j] = Integer.parseInt(elements[j]);
                }
            }

            return matrixArray;
        }

        public int [][] getMatrix1AsArray() {
            return getMatrixAsArray(matrix1);
        }

        public int [][] getMatrix2AsArray() {
            return getMatrixAsArray(matrix2);
        }
    }

    @Getter
    @Setter
    private static class MatrixResponse {
        private String result;

        public void setResultMatrix (int[][] resultMatrix) {
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < resultMatrix.length; i++) {
                sb.append("{");
                for (int j = 0; j < resultMatrix[i].length; j++) {
                    sb.append(resultMatrix[i][j]);
                    if (j < resultMatrix[i].length - 1) {
                        sb.append(",");
                    }
                }
                sb.append("}");
                if (i < resultMatrix.length - 1) {
                    sb.append(",");
                }
            }

            result = sb.toString();
        }
    }

}