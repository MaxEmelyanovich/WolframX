package framexteam.wolframx.calculations.matrices;

public class MatrixTranspose implements MatrixOperation {
    @Override
    public <T extends Number> void performOperation(T[][] firstMatrix, T[][] secondMatrix, T[][] resultMatrix, int startIndex, int endIndex) throws MatrixOperationException {
        if (firstMatrix == null || resultMatrix == null) {
            throw new MatrixOperationException("Matrices cannot be null.");
        }

        if (firstMatrix.length == 0 || resultMatrix.length == 0) {
            throw new MatrixOperationException("Matrices cannot be empty.");
        }

        if (startIndex < 0 || endIndex > resultMatrix.length * resultMatrix[0].length || startIndex >= endIndex) {
            throw new MatrixOperationException("Invalid start index or end index.");
        }

        for (int index = startIndex; index < endIndex; ++index) {
            final int row = index / resultMatrix.length;
            final int col = index % resultMatrix.length;
            Double result = firstMatrix[row][col].doubleValue();
            resultMatrix[col][row] = (T) result;
        }
    }

    @Override
    public <T extends Number> Double[][] getResultMatrixSize(T[][] firstMatrix, T[][] secondMatrix) throws MatrixOperationException {
        if (firstMatrix == null) {
            throw new MatrixOperationException("Matrices cannot be null.");
        }

        if (firstMatrix.length == 0) {
            throw new MatrixOperationException("Matrices cannot be empty.");
        }
        return new Double[firstMatrix[0].length][firstMatrix.length];
    }
}
