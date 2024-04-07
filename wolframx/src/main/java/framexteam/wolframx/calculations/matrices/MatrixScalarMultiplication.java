package framexteam.wolframx.calculations.matrices;

public class MatrixScalarMultiplication implements MatrixOperation {
    @Override
    public void performOperation(int[][] firstMatrix, int[][] secondMatrix, int[][] resultMatrix, int startIndex, int endIndex) throws MatrixOperationException {
        if (firstMatrix == null || secondMatrix == null || resultMatrix == null) {
            throw new MatrixOperationException("Matrices cannot be null.");
        }

        if (firstMatrix.length == 0 || secondMatrix.length == 0 || resultMatrix.length == 0) {
            throw new MatrixOperationException("Matrices cannot be empty.");
        }

        if (startIndex < 0 || endIndex > resultMatrix.length * resultMatrix[0].length || startIndex >= endIndex) {
            throw new MatrixOperationException("Invalid start index or end index.");
        }

        for (int index = startIndex; index < endIndex; ++index) {
            final int row = index / resultMatrix[0].length;
            final int col = index % resultMatrix[0].length;
            resultMatrix[row][col] = secondMatrix[0][0] * firstMatrix[row][col];
        }
    }

    @Override
    public int[][] getResultMatrixSize(int[][] firstMatrix, int[][] secondMatrix) throws MatrixOperationException {
        if (firstMatrix == null || secondMatrix == null) {
            throw new MatrixOperationException("Matrices cannot be null.");
        }

        if (firstMatrix.length == 0 || secondMatrix.length == 0) {
            throw new MatrixOperationException("Matrices cannot be empty.");
        }
        return new int[firstMatrix.length][firstMatrix[0].length];
    }
}
