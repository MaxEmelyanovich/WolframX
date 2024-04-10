package framexteam.wolframx.calculations.integrals;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class SimpsonMethod implements IntegralOperation{

    @Override
    public double calculateIntegral(String function, double start, double stop, int n, double tolerance){
        Expression e = new ExpressionBuilder(function)
                .variables("x")
                .build();
        double h = (stop - start) / n;
        double sum = e.setVariable("x",start).evaluate() + e.setVariable("x",stop).evaluate();

        for (int i = 1; i < n; i += 2) {
            double x = start + i * h;
            sum += 4 * e.setVariable("x",x).evaluate();
        }
        for (int i = 2; i < n - 1; i += 2) {
            double x = start + i * h;
            sum += 2 * e.setVariable("x",x).evaluate();
        }

        return (h / 3) * sum;
    }
}
