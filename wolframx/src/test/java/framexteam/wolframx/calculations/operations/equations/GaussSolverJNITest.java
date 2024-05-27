package framexteam.wolframx.calculations.operations.equations;

import com.google.code.tempusfugit.concurrency.ConcurrentTestRunner;
import com.google.code.tempusfugit.concurrency.annotations.Intermittent;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(ConcurrentTestRunner.class)
@Intermittent(repetition = 10)
class GaussSolverJNITest {

    @Test
    void solveWithSingleThreadJNI() {
        double[][] coefficients = {{1.0, 2.0, 3.0}, {4.0, 5.0, 6.0}, {7.0, 8.0, 9.0}};
        double[] constants = {10.0, 20.0, 30.0};
        double[] expected = {1.0, 2.0, 3.0};

        double[] actual = GaussSolverJNI.solve(coefficients, constants, 1);

        assertArrayEquals(expected, actual, 1e-10);
    }

    @Test
    void solveWithMultiThreadJNI() {
        double[][] coefficients = {{1.0, 2.0, 3.0}, {4.0, 5.0, 6.0}, {7.0, 8.0, 9.0}};
        double[] constants = {10.0, 20.0, 30.0};
        double[] expected = {1.0, 2.0, 3.0};

        double[] actual = GaussSolverJNI.solve(coefficients, constants, 4);

        assertArrayEquals(expected, actual, 1e-10);
    }

    @Test
    void solveWithJNI() {
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

        double[] actual = GaussSolverJNI.solve(coefficients, constants, threadCount);

        assertArrayEquals(expected, actual, 1e-10);
    }

    @Test
    void solveWithSingularMatrixJNI() {
        double[][] coefficients = {{1.0, 2.0, 3.0}, {1.0, 2.0, 3.0}, {4.0, 5.0, 6.0}};
        double[] constants = {10.0, 20.0, 30.0};

        assertThrows(RuntimeException.class, () -> GaussSolverJNI.solve(coefficients, constants, 2));
    }

    @Test
    void solveWithInvalidDimensionsJNI() {
        double[][] coefficients = {{1.0, 2.0, 3.0}, {4.0, 5.0, 6.0}};
        double[] constants = {10.0, 20.0, 30.0};

        assertThrows(RuntimeException.class, () -> GaussSolverJNI.solve(coefficients, constants, 2));
    }

    @Test
    void solveWithInvalidThreadCountJNI() {
        double[][] coefficients = {{1.0, 2.0, 3.0}, {4.0, 5.0, 6.0}, {7.0, 8.0, 9.0}};
        double[] constants = {10.0, 20.0, 30.0};

        assertThrows(RuntimeException.class, () -> GaussSolverJNI.solve(coefficients, constants, 0));
        assertThrows(RuntimeException.class, () -> GaussSolverJNI.solve(coefficients, constants, 100));
    }
}