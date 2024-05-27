package framexteam.wolframx.calculations.operations.equations;

import com.google.code.tempusfugit.concurrency.ConcurrentTestRunner;
import com.google.code.tempusfugit.concurrency.annotations.Intermittent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;

import java.util.Set;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(ConcurrentTestRunner.class)
@Intermittent(repetition = 10)
class NonlinearEquationSolverTest {

    private double[] coefficients;

    @BeforeEach
    void setUp() {
        coefficients = new double[]{1, -6, 11, -6};
    }

    @Test
    void testFunction() {
        double result = NonlinearEquationSolver.function(coefficients, 1);
        assertEquals(0.0, result, 1e-3);

        result = NonlinearEquationSolver.function(coefficients, 2);
        assertEquals(0.0, result, 1e-3);

        result = NonlinearEquationSolver.function(coefficients, 3);
        assertEquals(0.0, result, 1e-3);
    }

    @Test
    void testDerivative() {
        double result = NonlinearEquationSolver.derivative(coefficients, 1);
        assertEquals(2.0, result, 1e-3);

        result = NonlinearEquationSolver.derivative(coefficients, 2);
        assertEquals(-1.0, result, 1e-3);

        result = NonlinearEquationSolver.derivative(coefficients, 3);
        assertEquals(2.0, result, 1e-3);
    }

    @Test
    void testSolveSingleThread() throws Exception {
        Set<Double> roots = NonlinearEquationSolver.solve(coefficients, 1, 0.01, 100);
        Set<Double> expectedRoots = new HashSet<>();
        expectedRoots.add(1.0);
        expectedRoots.add(2.0);
        expectedRoots.add(3.0);

        assertEquals(expectedRoots.size(), roots.size());

        for (Double root : expectedRoots) {
            boolean found = roots.stream().anyMatch(r -> Math.abs(r - root) < 0.001);
            assertTrue(found, "Корень " + root + " должен быть найден");
        }
    }

    @Test
    void testSolveMultiThread() throws Exception {
        Set<Double> roots = NonlinearEquationSolver.solve(coefficients, 4, 0.01, 100);
        Set<Double> expectedRoots = new HashSet<>();
        expectedRoots.add(1.0);
        expectedRoots.add(2.0);
        expectedRoots.add(3.0);

        assertEquals(expectedRoots.size(), roots.size());

        for (Double root : expectedRoots) {
            boolean found = roots.stream().anyMatch(r -> Math.abs(r - root) < 0.001);
            assertTrue(found, "Корень " + root + " должен быть найден");
        }
    }

    @Test
    void testSolveTenPower() throws Exception {
        double[] coefficient = new double[]{
                1, -55, 1320, -18150, 157773, -902055,
                3416930, -8409500, 12753576, -10628640, 3628800
        };
        Set<Double> roots = NonlinearEquationSolver.solve(coefficient, 4, 0.01, 1000);
        Set<Double> expectedRoots = new HashSet<>();
        for (double i = 1; i <= 10;i++)
        {
        expectedRoots.add(i);
        }

        assertEquals(expectedRoots.size(), roots.size());

        for (Double root : expectedRoots) {
            boolean found = roots.stream().anyMatch(r -> Math.abs(r - root) < 0.001);
            assertTrue(found, "Корень " + root + " должен быть найден");
        }
    }

    @Test
    void testSolveWithNoSolutions() {
        double[] noRootsCoefficients = {1, 0, 1}; // x^2 + 1 = 0
        Exception exception = assertThrows(Exception.class, () -> {
            NonlinearEquationSolver.solve(noRootsCoefficients, 4, 0.01, 100);
        });

        String expectedMessage = "No solutions exists";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testSolveWithInvalidThreadCount() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            NonlinearEquationSolver.solve(coefficients, 0, 0.01, 100);
        });

        String expectedMessage = "Thread count must be greater than zero.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testSolveWithExceedingThreadCount() {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            NonlinearEquationSolver.solve(coefficients, availableProcessors + 1, 0.01, 100);
        });

        String expectedMessage = "Thread count exceeded the maximum. Current device have " + availableProcessors + " available processors";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}