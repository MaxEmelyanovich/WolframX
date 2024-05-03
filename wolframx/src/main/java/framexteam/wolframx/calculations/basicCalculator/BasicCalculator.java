package framexteam.wolframx.calculations.basicCalculator;// Java Program to find the
// solution of the arithmetic
// using the stack
import framexteam.wolframx.WolframxApplication;
import framexteam.wolframx.calculations.arithmeticOperations.ArithmeticOperations;
import framexteam.wolframx.calculations.equations.NonlinearEquationSolver;
import framexteam.wolframx.calculations.powerOperations.PowerOperations;
import framexteam.wolframx.calculations.vectors.VectorLibrary;
import framexteam.wolframx.calculations.vectors.VectorLibraryJNI;
import org.springframework.boot.SpringApplication;

import java.util.*;
public class BasicCalculator {
    public static double solve(String[] tokens)
    {
        Objects.requireNonNull(tokens, "Null expression");

        Stack<String> stack = new Stack<String>();
        double x, y;
        String result = "";
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
