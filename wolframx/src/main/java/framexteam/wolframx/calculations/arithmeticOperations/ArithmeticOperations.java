package framexteam.wolframx.calculations.arithmeticOperations;

import java.util.Objects;

public class ArithmeticOperations {

    public static <T extends Number> T add(T a, T b) {
        Objects.requireNonNull(a, "Parameter 'a' cannot be null");
        Objects.requireNonNull(b, "Parameter 'b' cannot be null");

        if (a instanceof Integer) {
            return (T) Integer.valueOf(a.intValue() + b.intValue());
        } else if (a instanceof Double) {
            return (T) Double.valueOf(a.doubleValue() + b.doubleValue());
        } else if (a instanceof Float) {
            return (T) Float.valueOf(a.floatValue() + b.floatValue());
        } else if (a instanceof Long) {
            return (T) Long.valueOf(a.longValue() + b.longValue());
        } else {
            throw new IllegalArgumentException("Unsupported number type");
        }
    }

    public static <T extends Number> T sub(T a, T b) {
        Objects.requireNonNull(a, "Parameter 'a' cannot be null");
        Objects.requireNonNull(b, "Parameter 'b' cannot be null");

        if (a instanceof Integer) {
            return (T) Integer.valueOf(a.intValue() - b.intValue());
        } else if (a instanceof Double) {
            return (T) Double.valueOf(a.doubleValue() - b.doubleValue());
        } else if (a instanceof Float) {
            return (T) Float.valueOf(a.floatValue() - b.floatValue());
        } else if (a instanceof Long) {
            return (T) Long.valueOf(a.longValue() - b.longValue());
        } else {
            throw new IllegalArgumentException("Unsupported number type");
        }
    }

    public static <T extends Number> T mul(T a, T b) {
        Objects.requireNonNull(a, "Parameter 'a' cannot be null");
        Objects.requireNonNull(b, "Parameter 'b' cannot be null");

        if (a instanceof Integer) {
            return (T) Integer.valueOf(a.intValue() * b.intValue());
        } else if (a instanceof Double) {
            return (T) Double.valueOf(a.doubleValue() * b.doubleValue());
        } else if (a instanceof Float) {
            return (T) Float.valueOf(a.floatValue() * b.floatValue());
        } else if (a instanceof Long) {
            return (T) Long.valueOf(a.longValue() * b.longValue());
        } else {
            throw new IllegalArgumentException("Unsupported number type");
        }
    }

    public static <T extends Number> T div(T a, T b) {
        Objects.requireNonNull(a, "Parameter 'a' cannot be null");
        Objects.requireNonNull(b, "Parameter 'b' cannot be null");

        if (b.doubleValue() == 0.0) {
            throw new IllegalArgumentException("Division by zero");
        }

        if (a instanceof Integer) {
            return (T) Integer.valueOf(a.intValue() / b.intValue());
        } else if (a instanceof Double) {
            return (T) Double.valueOf(a.doubleValue() / b.doubleValue());
        } else if (a instanceof Float) {
            return (T) Float.valueOf(a.floatValue() / b.floatValue());
        } else if (a instanceof Long) {
            return (T) Long.valueOf(a.longValue() / b.longValue());
        } else {
            throw new IllegalArgumentException("Unsupported number type");
        }
    }

    public static <T extends Number> T mod(T a, T b) {
        Objects.requireNonNull(a, "Parameter 'a' cannot be null");
        Objects.requireNonNull(b, "Parameter 'b' cannot be null");

        if (b.doubleValue() == 0.0) {
            throw new IllegalArgumentException("Division by zero");
        }

        if (a instanceof Integer) {
            return (T) Integer.valueOf(a.intValue() % b.intValue());
        } else if (a instanceof Double) {
            return (T) Double.valueOf(a.doubleValue() % b.doubleValue());
        } else if (a instanceof Float) {
            return (T) Float.valueOf(a.floatValue() % b.floatValue());
        } else if (a instanceof Long) {
            return (T) Long.valueOf(a.longValue() % b.longValue());
        } else {
            throw new IllegalArgumentException("Unsupported number type");
        }
    }

}
