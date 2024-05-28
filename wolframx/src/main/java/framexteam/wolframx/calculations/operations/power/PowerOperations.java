package framexteam.wolframx.calculations.operations.power;

import java.util.Objects;

import framexteam.wolframx.calculations.operations.arithmetic.ArithmeticOperations;

public class PowerOperations {
    private static final double EPS = 1E-15;
    public static <T extends Number> double root(T a, int n) {
        Objects.requireNonNull(a, "Number cannot be null");
        Objects.requireNonNull(n, "Root power cannot be null");

        if (a.doubleValue() < 0.0) {
            throw new IllegalArgumentException("Division by zero");
        } else if (a.doubleValue() == 0.0) {
            return 0;
        }

        if (n <= 0) {
            throw new IllegalArgumentException("Root power must be positive");
        }

        double x = a.doubleValue();
        double y = 1.0;

        while (Math.abs(x - y) > EPS) {
            y = x;
            x = ((n - 1) * x + a.doubleValue() / pow(x, n - 1)) / n;
        }

        return x;
    }

    public static <T extends Number> Double pow(T a, int n) {
        Objects.requireNonNull(a, "Number cannot be null");
        Objects.requireNonNull(n, "Power cannot be null");

        if (a.doubleValue() == 0 && n <= 0) {
            throw new IllegalArgumentException("Cannot raise 0 to a non-positive power.");
        }

        Number result = Double.valueOf(1);
        if(n < 0) {
            n = -n;
            while (n != 0) {
                if ((n & 1) != 0)
                    result = ArithmeticOperations.mul(result, a);
                    a = ArithmeticOperations.mul(a, a);
                n >>= 1;
            }
            return ArithmeticOperations.div(1.0, result).doubleValue();
        } else {
            while (n != 0) {
                if ((n & 1) != 0)
                    result = ArithmeticOperations.mul(result, a);
                    a = ArithmeticOperations.mul(a, a);
                n >>= 1;
            }
            return result.doubleValue();
        }
    }
}
