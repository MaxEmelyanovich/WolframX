package framexteam.wolframx.calculations.operations.calculator;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Stack;
import java.lang.Character;

public class FunctionParser {

    private static boolean letterOrDigit(char c) {
        return Character.isLetterOrDigit(c) || c == '.';
    }

    static int getPrecedence(char ch) {
        if (ch == '+' || ch == '-')
            return 1;
        else if (ch == '*' || ch == '/')
            return 2;
        else if (ch == '^')
            return 3;
        else
            return -1;
    }

    static boolean hasLeftAssociativity(char ch) {
        return (ch == '+' || ch == '-' || ch == '/' || ch == '*');
    }

    public static String[] infixToRpn(String expression) {

        Objects.requireNonNull(expression, "Write your expression");

        Stack<Character> stack = new Stack<>();
        ArrayList<String> output = new ArrayList<>();
        String num = "";

        for (int i = 0; i < expression.length(); ++i) {
            char c = expression.charAt(i);

            if (letterOrDigit(c) || (c == '-' && (i == 0 || expression.charAt(i - 1) == '('))) {
                num += c;
            } else {
                if (!num.isEmpty()) {
                    output.add(num);
                    num = "";
                }
                if (c == '(')
                    stack.push(c);
                else if (c == ')') {
                    while (!stack.isEmpty() && stack.peek() != '(')
                        output.add(Character.toString(stack.pop()));
                    stack.pop();
                } else {
                    while (!stack.isEmpty() && getPrecedence(c) <= getPrecedence(stack.peek()) && hasLeftAssociativity(c)) {
                        output.add(Character.toString(stack.pop()));
                    }
                    stack.push(c);
                }
            }
        }

        if (!num.isEmpty()) {
            output.add(num);
        }

        while (!stack.isEmpty()) {
            if (stack.peek() == '(')
                throw new IllegalArgumentException("This expression is invalid");
            output.add(Character.toString(stack.pop()));
        }

        return output.toArray(new String[0]);
    }
}