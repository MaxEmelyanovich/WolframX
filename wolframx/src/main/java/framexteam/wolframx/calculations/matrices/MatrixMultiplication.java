package framexteam.wolframx.calculations.matrices;

import framexteam.wolframx.calculations.arithmeticOperations.ArithmeticOperations;

public class MatrixMultiplication implements MatrixOperation {
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

        for (int index = startIndex; index < endIndex; ++index) {
            final int row = index / secondMatrix[0].length;
            final int col = index % secondMatrix[0].length;
            int value = 0;
            for (int i = 0; i < secondMatrix.length; ++i) {
                value = ArithmeticOperations.add(value, ArithmeticOperations.mul(firstMatrix[row][i], secondMatrix[i][col]));
            }
            resultMatrix[row][col] = value;
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

        if (firstMatrix[0].length != secondMatrix.length) {
            throw new MatrixOperationException("Invalid matrix dimensions for multiplication operation.");
        }
        return new int[firstMatrix.length][secondMatrix[0].length];
    }
}