package framexteam.wolframx.calculations.operations.equations;

public class NonlinearEquationSolverJNI {
    static {
        System.loadLibrary("NonlinearEquationSolver"); // Загрузка библиотеки JNI
    }
    public native static double[] solve(double[] coefficients, int threadCount, double epsilon, int maxIterations);

}