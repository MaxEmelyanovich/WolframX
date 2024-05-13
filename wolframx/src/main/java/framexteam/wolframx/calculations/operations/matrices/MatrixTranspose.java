package framexteam.wolframx.calculations.operations.matrices;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MatrixTranspose implements MatrixOperation {

    private static final Logger logger = LogManager.getLogger(MatrixTranspose.class);

    @Override
    public void performOperation(int[][] firstMatrix, int[][] secondMatrix, int[][] resultMatrix, int startIndex, int endIndex) throws MatrixOperationException {
        if (firstMatrix == null || resultMatrix == null) {
            throw new MatrixOperationException("Matrices cannot be null.");
        }

        if (firstMatrix.length == 0 || resultMatrix.length == 0) {
            throw new MatrixOperationException("Matrices cannot be empty.");
        }

        if (startIndex < 0 || endIndex > resultMatrix.length * resultMatrix[0].length || startIndex >= endIndex) {
            throw new MatrixOperationException("Invalid start index or end index.");
        }

        String logMessage = String.format("Matrix transpose started for coordinates (%d,%d) (%d,%d)",
                startIndex / resultMatrix[0].length, startIndex % resultMatrix[0].length,
                (endIndex - 1) / resultMatrix[0].length, (endIndex - 1) % resultMatrix[0].length);
        logger.info(logMessage);

        for (int index = startIndex; index < endIndex; ++index) {
            final int row = index / resultMatrix.length;
            final int col = index % resultMatrix.length;
            resultMatrix[col][row] = firstMatrix[row][col];
        }

        logger.info("Cell transpose completed");
    }

    @Override
    public int[][] getResultMatrixSize(int[][] firstMatrix, int[][] secondMatrix) throws MatrixOperationException {
        if (firstMatrix == null) {
            throw new MatrixOperationException("Matrices cannot be null.");
        }

        if (firstMatrix.length == 0) {
            throw new MatrixOperationException("Matrices cannot be empty.");
        }
        return new int[firstMatrix[0].length][firstMatrix.length];
    }
}
