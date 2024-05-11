package framexteam.wolframx.calculations.operations.integrals;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class TrapezoidalMethod implements IntegralOperation{
    @Override
    public double calculateIntegral(String function, double start, double stop, int n, double tolerance){
        Expression e = new ExpressionBuilder(function)
                .variables("x")
                .build();
        double h = (stop - start) / n;
        double sum = 0.5 * (e.setVariable("x",start).evaluate() + e.setVariable("x",stop).evaluate());

        for (int i = 1; i < n; i++) {
            double x = start + i * h;
            sum += e.setVariable("x",x).evaluate();
        }

        return h * sum;
    }


}
