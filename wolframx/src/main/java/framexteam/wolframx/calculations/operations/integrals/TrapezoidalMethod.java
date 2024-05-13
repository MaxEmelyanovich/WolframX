package framexteam.wolframx.calculations.operations.integrals;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TrapezoidalMethod implements IntegralOperation{
    private static final Logger logger = LogManager.getLogger(RombergMethod.class);
    @Override
    public double calculateIntegral(String function, double start, double stop, int n, double tolerance){
        logger.info("Calculating integral using Trapezoidal method");
        logger.info("Integration bounds: start = {}, stop = {}", start, stop);
        logger.info("Number of intervals and tolerance: {} {}", n, tolerance);
        Expression e = new ExpressionBuilder(function)
                .variables("x")
                .build();
        double h = (stop - start) / n;
        double sum = 0.5 * (e.setVariable("x",start).evaluate() + e.setVariable("x",stop).evaluate());

        for (int i = 1; i < n; i++) {
            double x = start + i * h;
            sum += e.setVariable("x",x).evaluate();
            logger.info("Step {}: Integral value = {}", i, sum);
        }

        logger.info("Integral calculation using Trapezoidal method completed");
        return h * sum;
    }


}
