package framexteam.wolframx.calculations.matrices;

import framexteam.wolframx.calculations.arithmeticOperations.ArithmeticOperations;

public class MatrixScalarMultiplication implements MatrixOperation {
    @Override
    public <T extends Number> void performOperation(T[][] firstMatrix, T[][] secondMatrix, T[][] resultMatrix, int startIndex, int endIndex) throws MatrixOperationException {
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
            Double result = ArithmeticOperations.mul(secondMatrix[0][0], firstMatrix[row][col]).doubleValue();
            resultMatrix[row][col] = (T) result;
        }
    }

    @Override
    public <T extends Number> Double[][] getResultMatrixSize(T[][] firstMatrix, T[][] secondMatrix) throws MatrixOperationException {
        if (firstMatrix == null || secondMatrix == null) {
            throw new MatrixOperationException("Matrices cannot be null.");
        }

        if (firstMatrix.length == 0 || secondMatrix.length == 0) {
            throw new MatrixOperationException("Matrices cannot be empty.");
        }
        return new Double[firstMatrix.length][firstMatrix[0].length];
    }
}
