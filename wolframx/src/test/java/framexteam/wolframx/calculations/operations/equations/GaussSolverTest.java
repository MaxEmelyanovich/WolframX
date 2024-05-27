package framexteam.wolframx.calculations.operations.equations;

import com.google.code.tempusfugit.concurrency.ConcurrentTestRunner;
import com.google.code.tempusfugit.concurrency.annotations.Intermittent;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(ConcurrentTestRunner.class)
@Intermittent(repetition = 10)
class GaussSolverTest {

    @Test
    void solveWithValidInputSingleThread() throws InterruptedException {
        double[][] coefficients = {
                {2, -1, 0},
                {-1, 2, -1},
                {0, -1, 2}
        };
        double[] constants = {1, 0, 1};
        int threadCount = 1;

        double[] expected = {1, 1, 1};

        double[] actual = GaussSolver.solve(coefficients, constants, threadCount);

        assertArrayEquals(expected, actual, 1e-10);
    }

    @Test
    void solveWithValidInputMultiThread() throws InterruptedException {
        double[][] coefficients = {
                {2, -1, 0},
                {-1, 2, -1},
                {0, -1, 2}
        };
        double[] constants = {1, 0, 1};
        int threadCount = 4;

        double[] expected = {1, 1, 1};

        double[] actual = GaussSolver.solve(coefficients, constants, threadCount);

        assertArrayEquals(expected, actual, 1e-10);
    }

    @Test
    void solveWith100x100Matrix() throws InterruptedException {
        double[][] coefficients = new double[20][20];
        double[] constants = new double[20];
        double[] expected = new double[20];

        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                coefficients[i][j] = (i == j) ? 1 : 0;
            }
            constants[i] = i;
            expected[i] = i;
        }

        int threadCount = 8;

        double[] actual = GaussSolver.solve(coefficients, constants, threadCount);

        assertArrayEquals(expected, actual, 1e-10);
    }

    @Test
    void solveWithSingularMatrix() {
        double[][] coefficients = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        double[] constants = {1, 0, 0};
        int threadCount = 2;

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                GaussSolver.solve(coefficients, constants, threadCount)
        );

        assertEquals("Matrix is singular or nearly singular", exception.getMessage());
    }

    @Test
    void solveWithNullCoefficients() {
        double[][] coefficients = null;
        double[] constants = {1, 0, 1};
        int threadCount = 2;

        Exception exception = assertThrows(NullPointerException.class, () ->
                GaussSolver.solve(coefficients, constants, threadCount)
        );

        assertEquals("Coefficients cannot be null", exception.getMessage());
    }

    @Test
    void solveWithNullConstants() {
        double[][] coefficients = {
                {2, -1, 0},
                {-1, 2, -1},
                {0, -1, 2}
        };
        double[] constants = null;
        int threadCount = 2;

        Exception exception = assertThrows(NullPointerException.class, () ->
                GaussSolver.solve(coefficients, constants, threadCount)
        );

        assertEquals("Constants cannot be null", exception.getMessage());
    }

    @Test
    void solveWithInvalidDimensions() {
        double[][] coefficients = {
                {2, -1},
                {-1, 2},
                {0, -1}
        };
        double[] constants = {1, 0, 1};
        int threadCount = 2;

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                GaussSolver.solve(coefficients, constants, threadCount)
        );

        assertEquals("Matrix dimensions are not equal.", exception.getMessage());
    }

    @Test
    void solveWithZeroThreadCount() {
        double[][] coefficients = {
                {2, -1, 0},
                {-1, 2, -1},
                {0, -1, 2}
        };
        double[] constants = {1, 0, 1};
        int threadCount = 0;

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                GaussSolver.solve(coefficients, constants, threadCount)
        );

        assertEquals("Thread count must be greater than zero.", exception.getMessage());
    }

    @Test
    void solveWithExcessiveThreadCount() {
        double[][] coefficients = {
                {2, -1, 0},
                {-1, 2, -1},
                {0, -1, 2}
        };
        double[] constants = {1, 0, 1};
        int threadCount = Runtime.getRuntime().availableProcessors() + 1;

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                GaussSolver.solve(coefficients, constants, threadCount)
        );

        assertEquals("Thread count exceeded the maximum. Current device has " +
                Runtime.getRuntime().availableProcessors() + " available processors", exception.getMessage());
    }
}