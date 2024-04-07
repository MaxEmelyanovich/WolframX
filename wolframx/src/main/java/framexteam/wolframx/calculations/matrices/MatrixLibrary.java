package framexteam.wolframx.calculations.matrices;

public class MatrixLibrary {

    public static int[][] performMatrixOperationMT(final int[][] firstMatrix,
                                                   final int[][] secondMatrix,
                                                   int threadCount,
                                                   MatrixOperation operation) throws MatrixOperationException {

        if (threadCount <= 0) {
            throw new MatrixOperationException("Thread count must be greater than zero.");
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

    private static class MatrixOperationThread extends Thread {
        private final int[][] firstMatrix;
        private final int[][] secondMatrix;
        private final int[][] resultMatrix;
        private final int firstIndex;
        private final int lastIndex;
        private final MatrixOperation operation;
        private MatrixOperationException exception;

        private volatile boolean errorOccurred = false;

        public MatrixOperationThread(final int[][] firstMatrix,
                                     final int[][] secondMatrix,
                                     final int[][] resultMatrix,
                                     final int firstIndex,
                                     final int lastIndex,
                                     final MatrixOperation operation) {
            this.firstMatrix = firstMatrix;
            this.secondMatrix = secondMatrix;
            this.resultMatrix = resultMatrix;
            this.firstIndex = firstIndex;
            this.lastIndex = lastIndex;
            this.operation = operation;
        }

        public boolean isErrorOccurred() {
            return errorOccurred;
        }

        public MatrixOperationException getException() {
            return exception;
        }

        @Override
        public void run() {
            try {
                operation.performOperation(firstMatrix, secondMatrix, resultMatrix, firstIndex, lastIndex);
            } catch (MatrixOperationException e) {
                errorOccurred = true;
                exception = e;
            }
        }
    }
}
