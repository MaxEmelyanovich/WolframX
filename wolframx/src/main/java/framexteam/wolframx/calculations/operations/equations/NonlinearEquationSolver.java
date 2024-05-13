package framexteam.wolframx.calculations.operations.equations;

import java.util.HashSet;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

public class NonlinearEquationSolver extends Thread {

    private static double epsilon = 0.000001;
    private static final double answerThreshold = 0.001;
    private static int maxIterations = 100;
    private static final int TOTAL_ITERATIONS = 1000000;

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
            double x0 = random.nextDouble() * 20000 - 10000;
            System.out.println(x0);
            double x = x0;
            for (int i = 0; i < maxIterations; i++) {
                double fx = function(coefficients, x);
                double fpx = derivative(coefficients, x);
                double newX = x - fx / fpx;

                if (Math.abs(newX - x) < epsilon) {
                    boolean isUnique = true;
                    synchronized (lock) {
                        for (double root : roots) {
                            if (Math.abs(newX - root) < answerThreshold) {
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

    public static Set<Double> solve(double[] coefficients, int threadCount, double epsilon, int maxIterations) throws Exception {
        Objects.requireNonNull(coefficients, "Coefficients cannot be null");
        Objects.requireNonNull(threadCount, "Number of threads cannot be null");

        NonlinearEquationSolver.epsilon = epsilon;
        NonlinearEquationSolver.maxIterations = maxIterations;

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
        if (roots.isEmpty()){
            throw new Exception("No solutions exists");
        }
        return roots;
    }
}