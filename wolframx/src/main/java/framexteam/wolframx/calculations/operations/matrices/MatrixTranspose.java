package framexteam.wolframx.calculations.operations.matrices;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MatrixTranspose implements MatrixOperation {

    private static final Logger logger = LogManager.getLogger(MatrixTranspose.class);

    @Override
    public void performOperation(int[][] firstMatrix, int[][] secondMatrix, int[][] resultMatrix, int startIndex, int endIndex) throws MatrixOperationException {

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

        return new int[firstMatrix[0].length][firstMatrix.length];
    }
}
