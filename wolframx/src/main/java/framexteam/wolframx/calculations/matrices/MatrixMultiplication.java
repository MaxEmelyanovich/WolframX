package framexteam.wolframx.calculations.matrices;

import framexteam.wolframx.calculations.arithmeticOperations.ArithmeticOperations;

public class MatrixMultiplication implements MatrixOperation {
    @Override
    public <T extends Number> void performOperation(T[][] firstMatrix, T[][] secondMatrix, T[][] resultMatrix, int startIndex, int endIndex) throws MatrixOperationException {
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

        for (int index = startIndex; index < endIndex; ++index) {
            final int row = index / secondMatrix[0].length;
            final int col = index % secondMatrix[0].length;
            Double value = 0.0;
            for (int i = 0; i < secondMatrix.length; ++i) {
                value = ArithmeticOperations.add(value, ArithmeticOperations.mul(firstMatrix[row][i], secondMatrix[i][col])).doubleValue();
            }
            resultMatrix[row][col] = (T) value;
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

        if (firstMatrix[0].length != secondMatrix.length) {
            throw new MatrixOperationException("Invalid matrix dimensions for multiplication operation.");
        }
        return new Double[firstMatrix.length][secondMatrix[0].length];
    }
}