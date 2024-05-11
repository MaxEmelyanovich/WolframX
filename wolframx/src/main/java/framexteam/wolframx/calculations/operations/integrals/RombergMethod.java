package framexteam.wolframx.calculations.operations.integrals;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class RombergMethod implements IntegralOperation{

    @Override
    public double calculateIntegral(String function, double a, double b, int n, double tolerance) {

        double[][] result = new double[n + 1][n + 1];

        for (int i = 1; i <= n; i++) {
            double h = (b - a) / Math.pow(2, i - 1);
            result[i][1] = 0.5 * (result[i - 1][1] + h * sumOfFunction(function, a, b, h));

            for (int k = 2; k <= i; k++) {
                result[i][k] = result[i][k - 1] + (result[i][k - 1] - result[i - 1][k - 1]) / (Math.pow(4, k - 1) - 1);
            }

            if (i > 1 && Math.abs(result[i][i] - result[i - 1][i - 1]) < tolerance) {
                return result[i][i];
            }
        }

        return result[n][n];
    }

    private static double sumOfFunction(String function, double a, double b, double h) {
        Expression e = new ExpressionBuilder(function)
                .variables("x")
                .build();

        double sum = 0.0;
        for (double x = a + h; x < b; x += h) {
            sum += e.setVariable("x",x).evaluate();
        }
        return sum;
    }

}
