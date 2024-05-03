package framexteam.wolframx.calculations.matrices;

public class MatrixLibrary {

    public static int[][] performMatrixOperationMT(final int[][] firstMatrix,
                                                   final int[][] secondMatrix,
                                                   int threadCount,
                                                   MatrixOperation operation) throws MatrixOperationException {

        if (threadCount <= 0) {
            throw new IllegalArgumentException("Thread count must be greater than zero.");
        }

        if (threadCount > Runtime.getRuntime().availableProcessors()) {
            throw new IllegalArgumentException("Thread count exceeded the maximum. Current device have " +
                    Runtime.getRuntime().availableProcessors() + " available processors");
        }

        final int[][] result = operation.getResultMatrixSize(firstMatrix, secondMatrix);
        final int rowCount = result.length;
        final int colCount = result[0].length;

        final int cellsForThread = (rowCount * colCount) / threadCount;
        int firstIndex = 0;
        final MatrixOperationThread[] operationThreads = new MatrixOperationThread[threadCount];
        MatrixOperationException exception = null;

        for (int threadIndex = threadCount - 1; threadIndex >= 0; --threadIndex) {
            int lastIndex = firstIndex + cellsForThread;
            if (threadIndex == 0) {
                lastIndex = rowCount * colCount;
            }
            operationThreads[threadIndex] = new MatrixOperationThread(firstMatrix, secondMatrix, result, firstIndex, lastIndex, operation);
            operationThreads[threadIndex].start();
            firstIndex = lastIndex;
        }

        try {
            for (final MatrixOperationThread operationThread : operationThreads) {
                operationThread.join();
                if (operationThread.isErrorOccurred()) {
                    exception = operationThread.getException();
                    break;
                }
            }
        } catch (InterruptedException e) {
            throw new MatrixOperationException("Matrix operation interrupted.");
        }

        if (exception != null) {
            throw exception;
        }

        return result;
    }
}
