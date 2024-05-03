package framexteam.wolframx.calculations.matrices;

    public class MatrixOperationThread extends Thread {
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
