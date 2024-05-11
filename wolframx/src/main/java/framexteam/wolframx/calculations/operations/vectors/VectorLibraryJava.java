package framexteam.wolframx.calculations.operations.vectors;

import framexteam.wolframx.calculations.operations.power.PowerOperations;

public class VectorLibraryJava implements VectorLibrary {

    @Override
    public double[] sum(double[] v1, double[] v2) {
        if (v1.length != v2.length) {
            throw new IllegalArgumentException("Vectors must have the same dimensions for addition.");
        }

        double[] result = new double[v1.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = v1[i] + v2[i];
        }
        return result;
    }

    @Override
    public double[] sub(double[] v1, double[] v2) {
        if (v1.length != v2.length) {
            throw new IllegalArgumentException("Vectors must have the same dimensions for subtraction.");
        }

        double[] result = new double[v1.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = v1[i] - v2[i];
        }
        return result;
    }

    @Override
    public double scalarMul(double[] v1, double[] v2) {
        if (v1.length != v2.length) {
            throw new IllegalArgumentException("Vectors must have the same dimensions for scalar multiplication.");
        }

        double result = 0;
        for (int i = 0; i < v1.length; i++) {
            result += v1[i] * v2[i];
        }
        return result;
    }

    @Override
    public double[] vectorMul(double[] v1, double[] v2) {
        if (v1.length != 3 || v2.length != 3) {
            throw new IllegalArgumentException("Vectors must be 3-dimensional for cross product.");
        }

        return new double[] {
                v1[1] * v2[2] - v1[2] * v2[1],
                v1[2] * v2[0] - v1[0] * v2[2],
                v1[0] * v2[1] - v1[1] * v2[0]};
    }

    @Override
    public double[] numberMul(double[] v1, double number) {
        double[] result = new double[v1.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = v1[i] * number;
        }
        return result;
    }

    @Override
    public double[] numberDiv(double[] v1, double number) {
        if (number == 0) {
            throw new IllegalArgumentException("Division by zero is not allowed.");
        }

        double[] result = new double[v1.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = v1[i] / number;
        }
        return result;
    }

    @Override
    public double abs(double[] v1) {
        double sumOfSquares = 0;
        for (double v : v1) {
            sumOfSquares += v * v;
        }
        return PowerOperations.root(sumOfSquares, 2);
    }

    @Override
    public double angle(double[] v1, double[] v2) {
        if (v1.length != v2.length) {
            throw new IllegalArgumentException("Vectors must have the same dimensions to calculate the angle.");
        }
        return scalarMul(v1, v2) / (abs(v1) * abs(v2));
    }
}