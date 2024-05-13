package framexteam.wolframx.calculations.operations.matrices;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import framexteam.wolframx.calculations.operations.arithmetic.ArithmeticOperations;

public class MatrixMultiplication implements MatrixOperation {

    private static final Logger logger = LogManager.getLogger(MatrixMultiplication.class);
    @Override
    public void performOperation(int[][] firstMatrix, int[][] secondMatrix, int[][] resultMatrix, int startIndex, int endIndex) throws MatrixOperationException {
        if (firstMatrix == null || secondMatrix == null || resultMatrix == null) {
            throw new MatrixOperationException("Matrices cannot be null.");
        }

        if (firstMatrix.length == 0 || secondMatrix.length == 0 || resultMatrix.length == 0) {
            throw new MatrixOperationException("Matrices cannot be empty.");
        }

        if (firstMatrix[0].length != secondMatrix.length) {
            throw new MatrixOperationException("Invalid matrix dimensions for multiplication operation.");
        }

        if (startIndex < 0 || endIndex > resultMatrix.length * resultMatrix[0].length || startIndex >= endIndex) {
            throw new MatrixOperationException("Invalid start index or end index.");
        }

        String logMessage = String.format("Matrix multiplication started for rows (%d-%d) and columns (%d-%d)",
                startIndex / secondMatrix[0].length, (endIndex - 1) / secondMatrix[0].length,
                startIndex % secondMatrix[0].length, (endIndex - 1) % secondMatrix[0].length);
        logger.info(logMessage);

        for (int index = startIndex; index < endIndex; ++index) {
            final int row = index / secondMatrix[0].length;
            final int col = index % secondMatrix[0].length;
            int value = 0;
            for (int i = 0; i < secondMatrix.length; ++i) {
                value = ArithmeticOperations.add(value, ArithmeticOperations.mul(firstMatrix[row][i], secondMatrix[i][col]));
            }
            resultMatrix[row][col] = value;
        }

        logger.info("Cell multiplication completed");
    }

    @Override
    public int[][] getResultMatrixSize(int[][] firstMatrix, int[][] secondMatrix) throws MatrixOperationException {
        if (firstMatrix == null || secondMatrix == null) {
            throw new MatrixOperationException("Matrices cannot be null.");
        }

        if (firstMatrix.length == 0 || secondMatrix.length == 0) {
            throw new MatrixOperationException("Matrices cannot be empty.");
        }

        if (firstMatrix[0].length != secondMatrix.length) {
            throw new MatrixOperationException("Invalid matrix dimensions for multiplication operation.");
        }
        return new int[firstMatrix.length][secondMatrix[0].length];
    }
}