package framexteam.wolframx.calculations.operations.matrices;

import framexteam.wolframx.calculations.operations.arithmetic.ArithmeticOperations;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MatrixAddition implements MatrixOperation {


    private static final Logger logger = LogManager.getLogger(MatrixAddition.class);

    @Override
    public void performOperation(int[][] firstMatrix, int[][] secondMatrix, int[][] resultMatrix, int startIndex, int endIndex) throws MatrixOperationException {

        String logMessage = String.format("Matrix addition started for coordinates (%d,%d) (%d,%d)",
                startIndex / resultMatrix[0].length, startIndex % resultMatrix[0].length,
                (endIndex - 1) / resultMatrix[0].length, (endIndex - 1) % resultMatrix[0].length);
        logger.info(logMessage);

        for (int index = startIndex; index < endIndex; ++index) {
            final int row = index / resultMatrix[0].length;
            final int col = index % resultMatrix[0].length;
            resultMatrix[row][col] = ArithmeticOperations.add(firstMatrix[row][col], secondMatrix[row][col]);;
        }

        logger.info("Cell addition completed");
    }

    @Override
    public int[][] getResultMatrixSize(int[][] firstMatrix, int[][] secondMatrix) throws MatrixOperationException {

        if (firstMatrix.length != secondMatrix.length || firstMatrix[0].length != secondMatrix[0].length) {
            throw new MatrixOperationException("Matrices must have the same dimensions.");
        }
        return new int[firstMatrix.length][firstMatrix[0].length];
    }
}
