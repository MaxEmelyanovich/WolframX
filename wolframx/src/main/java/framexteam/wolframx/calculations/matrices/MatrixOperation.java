package framexteam.wolframx.calculations.matrices;

public interface MatrixOperation {
    <T extends Number> void performOperation(T[][] firstMatrix, T[][] secondMatrix, T[][] resultMatrix, int startIndex, int endIndex) throws MatrixOperationException;

    <T extends Number> Double[][] getResultMatrixSize(T[][] firstMatrix, T[][] secondMatrix) throws MatrixOperationException;
}
