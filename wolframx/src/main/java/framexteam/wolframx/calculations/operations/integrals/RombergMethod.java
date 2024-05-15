package framexteam.wolframx.calculations.operations.integrals;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RombergMethod implements IntegralOperation{
    private static final Logger logger = LogManager.getLogger(RombergMethod.class);
    @Override
    public double calculateIntegral(String function, double start, double stop, int n, double tolerance) {
        logger.info("Calculating integral using Romberg method");
        logger.info("Integration bounds: start = {}, stop = {}", start, stop);
        logger.info("Number of intervals: {}", n);
        double[][] result = new double[n + 1][n + 1];

        for (int i = 1; i <= n; i++) {
            double h = (stop - start) / Math.pow(2, i - 1);
            result[i][1] = 0.5 * (result[i - 1][1] + h * sumOfFunction(function, start, stop, h));

            for (int k = 2; k <= i; k++) {
                result[i][k] = result[i][k - 1] + (result[i][k - 1] - result[i - 1][k - 1]) / (Math.pow(4, k - 1) - 1);
            }

            logger.info("Step {}: Integral value = {}", i, result[i][i]);

            if (i > 1 && Math.abs(result[i][i] - result[i - 1][i - 1]) < tolerance) {
                logger.info("Desired tolerance reached. Stopping calculation.");
                return result[i][i];
            }
        }
        logger.info("Integral calculation using Romberg method completed");
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
