package framexteam.wolframx.calculations.operations.equations;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class GaussSolver {
    private static final Logger logger = LogManager.getLogger(GaussSolver.class);
    private static long elapsedTime;

    public static double[] solve(double[][] coefficients, double[] constants, int threadCount) throws InterruptedException {
        Objects.requireNonNull(coefficients, "Coefficients cannot be null");
        Objects.requireNonNull(constants, "Constants cannot be null");

        int n = coefficients.length;
        if (n != coefficients[0].length || n != constants.length) {
            throw new IllegalArgumentException("Matrix dimensions are not equal.");
        }

        if (threadCount <= 0) {
            throw new IllegalArgumentException("Thread count must be greater than zero.");
        }

        if (threadCount > Runtime.getRuntime().availableProcessors()) {
            throw new IllegalArgumentException("Thread count exceeded the maximum. Current device has " +
                    Runtime.getRuntime().availableProcessors() + " available processors");
        }

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < n; i++) {
            // Pivoting
            int max = i;
            for (int k = i + 1; k < n; k++) {
                if (Math.abs(coefficients[k][i]) > Math.abs(coefficients[max][i])) {
                    max = k;
                }
            }

            // Swap rows in coefficients and constants
            double[] temp = coefficients[i];
            coefficients[i] = coefficients[max];
            coefficients[max] = temp;

            double t = constants[i];
            constants[i] = constants[max];
            constants[max] = t;

            // Check for singular matrix
            if (Math.abs(coefficients[i][i]) <= 1e-10) {
                throw new IllegalArgumentException("Matrix is singular or nearly singular");
            }

            // Create a thread pool
            ExecutorService executor = Executors.newFixedThreadPool(threadCount);

            // Elimination process
            for (int k = i + 1; k < n; k++) {
                final int row = k;
                int finalI = i;
                executor.execute(() -> {
                    double factor = coefficients[row][finalI] / coefficients[finalI][finalI];
                    constants[row] -= factor * constants[finalI];
                    for (int j = finalI; j < n; j++) {
                        coefficients[row][j] -= factor * coefficients[finalI][j];
                    }
                });
            }

            executor.shutdown();
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
        }

        // Back substitution
        double[] solution = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            double sum = 0.0;
            for (int j = i + 1; j < n; j++) {
                sum += coefficients[i][j] * solution[j];
            }
            solution[i] = (constants[i] - sum) / coefficients[i][i];
        }

        elapsedTime = System.currentTimeMillis() - startTime;
        logger.info("Total elapsed time: {} seconds.", (elapsedTime / 1000.0));
        logger.info("Calculations completed successfully");
        return solution;
    }

    public static long getElapsedTime() {
        return elapsedTime;
    }
}