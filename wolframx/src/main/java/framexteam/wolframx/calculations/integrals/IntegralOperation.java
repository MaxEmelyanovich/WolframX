package framexteam.wolframx.calculations.integrals;

public interface IntegralOperation {
    double calculateIntegral(String function, double start, double stop, int n, double tolerance);
}
