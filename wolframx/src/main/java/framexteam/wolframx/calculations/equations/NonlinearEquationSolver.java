package framexteam.wolframx.calculations.equations;

import framexteam.wolframx.WolframxApplication;
import org.springframework.boot.SpringApplication;

import java.util.HashSet;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

public class NonlinearEquationSolver extends Thread {

    private static final double epsilon = 0.0001;
    private static final int maxIterations = 100;
    private static final int TOTAL_ITERATIONS = 1000;

    private static final Object lock = new Object();
    private static Set<Double> roots = new HashSet<>();
    private final int numThreads;

    private final double[] coefficients;

    public NonlinearEquationSolver(double[] coefficients, int numThreads) {
        this.numThreads = numThreads;
        this.coefficients = coefficients;
    }

    public static double function(double[] coefficients, double x) {

        double result = 0.0;
        int len = coefficients.length - 1;
        for (int i = 0; i < coefficients.length; i++) {
            result += coefficients[i] * Math.pow(x, len - i);
        }
        return result;
    }

    public static double derivative(double[] coefficients, double x) {
        double h = 1e-6;
        return (function(coefficients, x + h) - function(coefficients, x)) / h;
    }


    public void run() {
        Random random = new Random();

        for (int j = 0; j < TOTAL_ITERATIONS / numThreads; j++) {
            double x0 = -100000000 + random.nextDouble() * 100000000;
            double x = x0;
            for (int i = 0; i < maxIterations; i++) {
                double fx = function(coefficients, x);
                double fpx = derivative(coefficients, x);
                double newX = x - fx / fpx;

                if (Math.abs(newX - x) < epsilon) {
                    boolean isUnique = true;
                    synchronized (lock) {
                        for (double root : roots) {
                            if (Math.abs(newX - root) < epsilon) {
                                isUnique = false;
                                break;
                            }
                        }
                        if (isUnique) {
                            roots.add(newX);
                        }
                    }
                    break;
                }

                x = newX;
            }
        }
    }

    public static Set<Double> solve(double[] coefficients, int threadCount) {
        Objects.requireNonNull(coefficients, "Coefficients cannot be null");
        Objects.requireNonNull(threadCount, "Number of threads cannot be null");

        if (threadCount <= 0) {
            throw new IllegalArgumentException("Thread count must be greater than zero.");
        }

        if (threadCount > Runtime.getRuntime().availableProcessors()) {
            throw new IllegalArgumentException("Thread count exceeded the maximum. Current device have " +
                    Runtime.getRuntime().availableProcessors() + " available processors");
        }

        NonlinearEquationSolver[] solvers = new NonlinearEquationSolver[threadCount];
        for (int i = 0; i < threadCount; i++) {
            solvers[i] = new NonlinearEquationSolver(coefficients, threadCount);
            solvers[i].start();
        }

        for (NonlinearEquationSolver solver : solvers) {
            try {
                solver.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return roots;
    }
}
