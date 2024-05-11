package framexteam.wolframx.calculations.operations.integrals;


import java.util.Objects;

public class IntegralLibrary {
    private static final double tolerance = 1e-6;
    private static final int N = 100;

    public static double performIntegralOperation(String function, double start, double stop, IntegralOperation operation){
        Objects.requireNonNull(function, "Function cannot be null");
        Objects.requireNonNull(start, "Lower bound cannot be null");
        Objects.requireNonNull(stop, "Upper bound cannot be null");

        return operation.calculateIntegral(function, start, stop, N, tolerance);
    }

}
