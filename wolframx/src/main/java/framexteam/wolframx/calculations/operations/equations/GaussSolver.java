package framexteam.wolframx.calculations.operations.equations;

//import framexteam.wolframx.calculations.operations.matrices.MatrixOperationException;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class GaussSolver extends Thread {
    private final double[][] coefficients;
    private final double[] constants;
    private final int row;

    public GaussSolver(double[][] coefficients, double[] constants, int row) {
        this.coefficients = coefficients;
        this.constants = constants;
        this.row = row;
    }

    @Override
    public void run() {
        double[] rowCoefficients = coefficients[row];
        double rowConstant = constants[row];
        double pivot = rowCoefficients[row];
        int n = coefficients.length;
        for (int k = row + 1; k < n; k++) {
            double factor = coefficients[k][row] / pivot;
            for (int j = row; j < n; j++) {
                coefficients[k][j] -= factor * rowCoefficients[j];
            }
            constants[k] -= factor * rowConstant;
        }
    }

    public static double[] solve(double[][] coefficients, double[] constants, int threadCount) throws InterruptedException {

        Objects.requireNonNull(coefficients, "Coefficients cannot be null");
        Objects.requireNonNull(constants, "Constants cannot be null");
        Objects.requireNonNull(threadCount, "Number of threads cannot be null");

        int n = coefficients.length;
        if (n != coefficients[0].length || n != constants.length) {
            throw new IllegalArgumentException("Matrix dimensions are not equal.");
        }

        if (threadCount <= 0) {
            throw new IllegalArgumentException("Thread count must be greater than zero.");
        }

        if (threadCount > Runtime.getRuntime().availableProcessors()) {
            throw new IllegalArgumentException("Thread count exceeded the maximum. Current device have " +
                    Runtime.getRuntime().availableProcessors() + " available processors");
        }

        GaussSolver[] solvers = new GaussSolver[n];
        for (int i = 0; i < n; i++) {
            solvers[i] = new GaussSolver(coefficients, constants, i);
        }

        ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        for (int i = 0; i < n; i++) {
            executor.execute(solvers[i]);
        }

        executor.shutdown();
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);

        double[] solution = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            double sum = 0.0;
            for (int j = i + 1; j < n; j++) {
                sum += coefficients[i][j] * solution[j];
            }
                if (coefficients[i][i] == 0){
                    throw new IllegalArgumentException("No solutions, matrix is invertible.");
                }
                else {
                    solution[i] = (constants[i] - sum) / coefficients[i][i];
                }
        }

        return solution;
    }
    }
