package framexteam.wolframx.calculations.operations.integrals;

public interface IntegralOperation {
    double calculateIntegral(String function, double start, double stop, int n, double tolerance);
}
