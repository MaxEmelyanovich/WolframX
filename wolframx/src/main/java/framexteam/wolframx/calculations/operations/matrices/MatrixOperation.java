package framexteam.wolframx.calculations.operations.matrices;

public interface MatrixOperation {
    void performOperation(int[][] firstMatrix, int[][] secondMatrix, int[][] resultMatrix, int startIndex, int endIndex) throws MatrixOperationException;

    int[][] getResultMatrixSize(int[][] firstMatrix, int[][] secondMatrix) throws MatrixOperationException;
}
