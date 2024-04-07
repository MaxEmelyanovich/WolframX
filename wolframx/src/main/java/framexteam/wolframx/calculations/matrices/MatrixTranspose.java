package framexteam.wolframx.calculations.matrices;

public class MatrixTranspose implements MatrixOperation {
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

        for (int index = startIndex; index < endIndex; ++index) {
            final int row = index / resultMatrix.length;
            final int col = index % resultMatrix.length;
            resultMatrix[col][row] = firstMatrix[row][col];
        }
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
