package framexteam.wolframx.calculations.numberConverter;

import java.util.Objects;

public class NumberConverter {
    public static String convert(String number, int sourceBase, int targetBase) {

        Objects.requireNonNull(number, "Number cannot be null");
        Objects.requireNonNull(sourceBase, "Source base cannot be null");
        Objects.requireNonNull(targetBase, "Target base cannot be null");

        if (sourceBase < 2 || targetBase < 2 || sourceBase > 36 || targetBase > 36) {
            throw new IllegalArgumentException("Unacceptable bases of number systems");
        }

        String[] parts = number.split("\\.");
        String integerPart = parts[0];
        String fractionalPart = parts.length > 1 ? parts[1] : "";

        Long integerValue = Long.parseLong(integerPart, sourceBase);
        String resultIntegerPart = Long.toString(integerValue, targetBase);

        StringBuilder resultFractionalPart = new StringBuilder();
        if (!fractionalPart.isEmpty()) {
            double fractionalValue = 0;
            double base = 1.0 / sourceBase;
            for (char c : fractionalPart.toCharArray()) {
                int digit = Character.digit(c, sourceBase);
                fractionalValue += digit * base;
                base /= sourceBase;
            }

            StringBuilder targetFractionalPart = new StringBuilder();
            for (int i = 0; i < 16; i++) {
                fractionalValue *= targetBase;
                int digit = (int) fractionalValue;
                targetFractionalPart.append(Integer.toString(digit, targetBase).toUpperCase());
                fractionalValue -= digit;
            }
            resultFractionalPart.append(targetFractionalPart);
        }

        StringBuilder result = new StringBuilder(resultIntegerPart);
        if (resultFractionalPart.length() > 0) {
            result.append(".");
            result.append(resultFractionalPart);
        }

        return result.toString();
    }
}
