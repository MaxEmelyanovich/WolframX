package framexteam.wolframx.calculations.operations.equations;

import java.util.Set;

public class NonlinearEquationSolverJNI {
    static {
        System.loadLibrary("NonlinearEquationSolver"); // Загрузка библиотеки JNI
    }
    public native static double[] solve(double[] coefficients, int threadCount, double epsilon, int maxIterations);

}