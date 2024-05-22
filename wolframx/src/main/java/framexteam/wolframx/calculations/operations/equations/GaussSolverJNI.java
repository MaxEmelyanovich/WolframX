package framexteam.wolframx.calculations.operations.equations;

public class GaussSolverJNI {

    static {
        System.loadLibrary("GaussSolver"); // Загрузка библиотеки JNI
    }
    public native static double[] solve(double[][] coefficients, double[] constants, int threadCount);
    public native static long getExecutionTime();

}