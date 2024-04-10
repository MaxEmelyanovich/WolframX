package framexteam.wolframx.calculations.vectors;

import framexteam.wolframx.calculations.powerOperations.PowerOperations;

public class VectorLibrary {

    public static double[] sum(double[] v1, double[] v2) {

        double[] result = new double[v1.length];

        for (int i = 0; i < result.length; i++) {
            result[i] = v1[i] + v2[i];
        }

        return result;
    }

    public static double[] sub(double[] v1, double[] v2) {

        double[] result = new double[v1.length];

        for (int i = 0; i < result.length; i++) {
            result[i] = v1[i] - v2[i];
        }

        return result;
    }

    public static double scalarMul(double[] v1, double[] v2) {

        double result = 0;

        for (int i = 0; i < v1.length; i++) {
            result += v1[i] * v2[i];
        }

        return result;
    }

    public static double[] vectorMul(double[] v1, double[] v2) {


        double[] result = new double[] {
                v1[1] * v2[2] - v1[2] * v2[1],
                v1[2] * v2[0] - v1[0] * v2[2],
                v1[0] * v2[1] - v1[1] * v2[0]};


        return result;
    }

    public static double[] numberMul(double[] v1, double number) {

        double[] result = new double[v1.length];

        for (int i = 0; i < result.length; i++) {
            result[i] = v1[i] * number;
        }

        return result;
    }

    public static double[] numberDiv(double[] v1, double number) {

        double[] result = new double[v1.length];

        for (int i = 0; i < result.length; i++) {
            result[i] = v1[i] / number;
        }

        return result;
    }

    public static double abs(double[] v1) {

        double abs = 0;

        for (int i = 0; i < v1.length; i++) {
            abs += v1[i] * v1[i] ;
        }

        return PowerOperations.root(abs,2);
    }

    public static double angle(double[] v1, double[] v2) {

        return scalarMul(v1,v2) / (abs(v1) * abs(v2));
    }
}
