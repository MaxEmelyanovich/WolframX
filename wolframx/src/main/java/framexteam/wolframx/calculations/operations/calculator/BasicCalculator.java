package framexteam.wolframx.calculations.operations.calculator;

import framexteam.wolframx.calculations.operations.arithmetic.ArithmeticOperations;
import framexteam.wolframx.calculations.operations.power.PowerOperations;

import java.util.*;
public class BasicCalculator {
    public static double solve(String[] tokens)
    {
        Objects.requireNonNull(tokens, "Null expression");

        Stack<String> stack = new Stack<String>();
        double x, y;
        String choice;
        double value = 0;

        for (int i = 0; i < tokens.length; i++) {

            if (!"+".equals(tokens[i]) && !"^".equals(tokens[i]) && !"-".equals(tokens[i])
                    && !"*".equals(tokens[i]) && !"/".equals(tokens[i])) {
                stack.push(tokens[i]);
                continue;
            }
            else {
                choice = tokens[i];
            }

            switch (choice) {
                case "+":

                    x = Double.parseDouble(stack.pop());
                    y = Double.parseDouble(stack.pop());
                    value = ArithmeticOperations.add(x , y);
                    stack.push(Double.toString(value));
                    break;

                case "-":

                    x = Double.parseDouble(stack.pop());
                    y = Double.parseDouble(stack.pop());
                    value = ArithmeticOperations.sub(y , x);
                    stack.push(Double.toString(value));
                    break;

                case "*":

                    x = Double.parseDouble(stack.pop());
                    y = Double.parseDouble(stack.pop());
                    value = ArithmeticOperations.mul(x, y);
                    stack.push(Double.toString(value));
                    break;

                case "/":

                    x = Double.parseDouble(stack.pop());
                    y = Double.parseDouble(stack.pop());
                    value = ArithmeticOperations.div(y , x);
                    stack.push(Double.toString(value));
                    break;

                case "^":

                    x = Double.parseDouble(stack.pop());
                    y = Double.parseDouble(stack.pop());
                    value = PowerOperations.pow(y,(int) x);
                    stack.push(Double.toString(value));
                    break;

                default:
                    continue;
            }
        }

        return Double.parseDouble(stack.pop());
    }

}
