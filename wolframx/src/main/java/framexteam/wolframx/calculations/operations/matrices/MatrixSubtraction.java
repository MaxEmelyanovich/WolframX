package framexteam.wolframx.calculations.operations.matrices;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import framexteam.wolframx.calculations.operations.arithmetic.ArithmeticOperations;

public class MatrixSubtraction implements MatrixOperation {

    private static final Logger logger = LogManager.getLogger(MatrixSubtraction.class);

    @Override
    public void performOperation(int[][] firstMatrix, int[][] secondMatrix, int[][] resultMatrix, int startIndex, int endIndex) throws MatrixOperationException {

        String logMessage = String.format("Matrix subtraction started for coordinates (%d,%d) (%d,%d)",
                startIndex / resultMatrix[0].length, startIndex % resultMatrix[0].length,
                (endIndex - 1) / resultMatrix[0].length, (endIndex - 1) % resultMatrix[0].length);
        logger.info(logMessage);

        for (int index = startIndex; index < endIndex; ++index) {
            final int row = index / resultMatrix[0].length;
            final int col = index % resultMatrix[0].length;
            resultMatrix[row][col] = ArithmeticOperations.sub(firstMatrix[row][col], secondMatrix[row][col]);
        }

        logger.info("Cell subtraction completed");
    }

    @Override
    public int[][] getResultMatrixSize(int[][] firstMatrix, int[][] secondMatrix) throws MatrixOperationException {

        if (firstMatrix.length != secondMatrix.length || firstMatrix[0].length != secondMatrix[0].length) {
            throw new MatrixOperationException("Matrices must have the same dimensions.");
        }
        return new int[firstMatrix.length][secondMatrix[0].length];
    }
}
