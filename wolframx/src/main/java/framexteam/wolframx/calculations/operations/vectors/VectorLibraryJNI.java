package framexteam.wolframx.calculations.operations.vectors;


public class VectorLibraryJNI {
    static {
       System.loadLibrary("vector"); // Загрузка библиотеки JNI
    }
    public native static double[] sum(double[] v1, double[] v2);
    public native static double[] sub(double[] v1, double[] v2);
    public native static double scalarMul(double[] v1, double[] v2);
    public native static double[] vectorMul(double[] v1, double[] v2);
    public native static double[] numberMul(double[] v1, double number);
    public native static double[] numberDiv(double[] v1, double number);
    public native static double abs(double[] v1);
    public native static double angle(double[] v1, double[] v2);

}
