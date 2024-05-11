package framexteam.wolframx.calculations.operations.vectors;

public interface VectorLibrary {
    double[] sum(double[] v1, double[] v2);
    double[] sub(double[] v1, double[] v2);
    double scalarMul(double[] v1, double[] v2);
    double[] vectorMul(double[] v1, double[] v2);
    double[] numberMul(double[] v1, double number);
    double[] numberDiv(double[] v1, double number);
    double abs(double[] v1);
    double angle(double[] v1, double[] v2);
}