package framexteam.wolframx.controller;

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
@RequestMapping("/calculations")
@Tag(name = "Вычисления", description = "API для выполнения математических операций")
public class CalculationsController {
    @PostMapping("/matrices/transpose")
    @Operation(summary = "Транспонирование матрицы",
               description = "Вычисляет транспонированную матрицу.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Транспонированная матрица", 
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))),
        @ApiResponse(responseCode = "400", description = "Ошибка в формате входных данных")
    })
    public ResponseEntity<?> transposeMatrix(@RequestBody MatrixRequest matrixRequest) throws MatrixOperationException {
        String matrixString = matrixRequest.getMatrix();
        System.out.println(matrixString);

        int threadCount = matrixRequest.getThreads();

        // Извлечение содержимого матрицы из строки
        int startIndex = matrixString.indexOf("{") + 1;
        int endIndex = matrixString.lastIndexOf("}");
        String matrixContent = matrixString.substring(startIndex, endIndex);

        // Разделение строки на строки матрицы
        String[] rows = matrixContent.split("},\\{");

        // Определение размера матрицы
        int numRows = rows.length;
        int numCols = rows[0].split(",").length;

        // Создание двумерного массива для хранения матрицы
        int[][] matrix = new int[numRows][numCols];

        // Заполнение двумерного массива значениями из строки
        for (int i = 0; i < numRows; i++) {
            String[] elements = rows[i].split(",");
            for (int j = 0; j < numCols; j++) {
                matrix[i][j] = Integer.parseInt(elements[j]);
            }
        }

        MatrixOperation operation = new MatrixTranspose();
        int [][] resultMatrix = MatrixLibrary.performMatrixOperationMT(matrix, matrix, threadCount, operation);

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

        Result result = new Result();
        result.setResult(sb.toString());

        return ResponseEntity.ok(result);
    }

    private static class MatrixRequest {
        private String matrix;
        private int threads;

        public String getMatrix() {
            return matrix;
        }

        public int getThreads(){
            return threads;
        }

        public void setMatrix(String matrix) {
            this.matrix = matrix;
        }

        public void setThreads(int threads) {
            this.threads = threads;
        }
    }

    private static class Result {
        private String result;

        public void setResult(String result) {
            this.result = result;
        }

        public String getResult() {
            return result;
        }
    }

}
