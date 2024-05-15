package framexteam.wolframx.calculations.operations.integrals;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SimpsonMethod implements IntegralOperation{
    private static final Logger logger = LogManager.getLogger(RombergMethod.class);
    @Override
    public double calculateIntegral(String function, double start, double stop, int n, double tolerance){
        logger.info("Calculating integral using Simpson's method");
        logger.info("Integration bounds: start = {}, stop = {}", start, stop);
        logger.info("Number of intervals: {}", n);
        Expression e = new ExpressionBuilder(function)
                .variables("x")
                .build();
        double h = (stop - start) / n;
        double sum = e.setVariable("x",start).evaluate() + e.setVariable("x",stop).evaluate();

        for (int i = 1; i < n; i += 2) {
            double x = start + i * h;
            sum += 4 * e.setVariable("x",x).evaluate();
            logger.info("Step {}: Integral value = {}", i, sum);
        }
        for (int i = 2; i < n - 1; i += 2) {
            double x = start + i * h;
            sum += 2 * e.setVariable("x",x).evaluate();
            logger.info("Step {}: Integral value = {}", i, sum);
        }
        logger.info("Integral calculation using Simpson's method completed");
        return (h / 3) * sum;
    }
}
