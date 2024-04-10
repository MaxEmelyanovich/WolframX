package framexteam.wolframx.controller.calculations;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import framexteam.wolframx.calculations.matrices.*;
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
public class MatrixCalculationsController {
    @PostMapping("/transpose")
    @Operation(summary = "Транспонирование матрицы",
               description = "Вычисляет транспонированную матрицу.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Транспонированная матрица", 
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = MatrixResponse.class))),
        @ApiResponse(responseCode = "400", description = "Ошибка в формате входных данных")
    })
    public ResponseEntity<?> transposeMatrix(@RequestBody MatrixRequest matrixRequest) throws MatrixOperationException {
        // String matrixString = matrixRequest.getMatrix();
        // System.out.println(matrixString);

        int [][] matrix = matrixRequest.getMatrix1AsArray();

        int threads = matrixRequest.getThreads();

        MatrixOperation operation = new MatrixTranspose();
        int [][] resultMatrix = MatrixLibrary.performMatrixOperationMT(matrix, matrix, threads, operation);

        MatrixResponse matrixResponse = new MatrixResponse();
        matrixResponse.setResultMatrix(resultMatrix);

        return ResponseEntity.ok(matrixResponse);
    }

    @PostMapping("/multiply")
    public ResponseEntity<?> multiplyMatrices(@RequestBody MatrixRequest matrixRequest) throws MatrixOperationException {
        int [][] matrix1 = matrixRequest.getMatrix1AsArray();
        int [][] matrix2 = matrixRequest.getMatrix2AsArray();

        int threads = matrixRequest.getThreads();

        MatrixOperation operation = new MatrixMultiplication();
        int [][] resultMatrix = MatrixLibrary.performMatrixOperationMT(matrix1, matrix2, threads, operation);

        MatrixResponse matrixResponse = new MatrixResponse();
        matrixResponse.setResultMatrix(resultMatrix);

        return ResponseEntity.ok(matrixResponse);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addMatrices(@RequestBody MatrixRequest matrixRequest) throws MatrixOperationException {
        int [][] matrix1 = matrixRequest.getMatrix1AsArray();
        int [][] matrix2 = matrixRequest.getMatrix2AsArray();

        int threads = matrixRequest.getThreads();

        MatrixOperation operation = new MatrixAddition();
        int [][] resultMatrix = MatrixLibrary.performMatrixOperationMT(matrix1, matrix2, threads, operation);

        MatrixResponse matrixResponse = new MatrixResponse();
        matrixResponse.setResultMatrix(resultMatrix);

        return ResponseEntity.ok(matrixResponse);
    }

    @PostMapping("/substract")
    public ResponseEntity<?> substractMatrices(@RequestBody MatrixRequest matrixRequest) throws MatrixOperationException {
        int [][] matrix1 = matrixRequest.getMatrix1AsArray();
        int [][] matrix2 = matrixRequest.getMatrix2AsArray();

        int threads = matrixRequest.getThreads();

        MatrixOperation operation = new MatrixSubtraction();
        int [][] resultMatrix = MatrixLibrary.performMatrixOperationMT(matrix1, matrix2, threads, operation);

        MatrixResponse matrixResponse = new MatrixResponse();
        matrixResponse.setResultMatrix(resultMatrix);

        return ResponseEntity.ok(matrixResponse);
    }

    @PostMapping("/multiplybyscalar")
    public ResponseEntity<?> multiplyMatrixByScalar(@RequestBody MatrixRequest matrixRequest) throws MatrixOperationException {
        int [][] matrix1 = matrixRequest.getMatrix1AsArray();

        int [][] scalarArray = new int[1][1];
        scalarArray[0][0] = matrixRequest.getScalar();

        int threads = matrixRequest.getThreads();

        MatrixOperation operation = new MatrixScalarMultiplication();
        int [][] resultMatrix = MatrixLibrary.performMatrixOperationMT(matrix1, scalarArray, threads, operation);

        MatrixResponse matrixResponse = new MatrixResponse();
        matrixResponse.setResultMatrix(resultMatrix);

        return ResponseEntity.ok(matrixResponse);
    }


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

        public String getMatrix1() {
            return matrix1;
        }

        public String getMatrix2() {
            return matrix2;
        }

        public int getScalar() {
            return scalar;
        }

        public int getThreads(){
            return threads;
        }

        public void setMatrix1(String matrix1) {
            this.matrix1 = matrix1;
        }

        public void setMatrix2(String matrix2) {
            this.matrix2 = matrix2;
        }

        public void setThreads(int threads) {
            this.threads = threads;
        }

        public void setScalar(int scalar) {
            this.scalar = scalar;
        }
    }

    private static class MatrixResponse {
        private String result;

        public void setResult(String result) {
            this.result = result;
        }

        public String getResult() {
            return result;
        }

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
