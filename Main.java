import java.util.Map;

public class Main {

    public static void main(String[] args) {
        try {
            System.out.println(calc("VI / III"));
            System.out.println(calc("1 + 2"));
        } catch (Exception e) {
            System.out.println("throws Exception: " + e.getMessage());
        }
    }

    public static String calc(String input) throws Exception {

        if (!input.matches("\\w+ [\\+\\-\\*/] \\w+")) throw new Exception("Неверный формат выражения");


        String[] parts = input.split(" ");
        String operand1 = parts[0];
        String operator = parts[1];
        String operand2 = parts[2];


        boolean isArabic = operand1.matches("\\d+");
        int num1 = isArabic ? Integer.parseInt(operand1) : romanToArabic(operand1);
        int num2 = isArabic ? Integer.parseInt(operand2) : romanToArabic(operand2);


        if (num1 < 1 || num1 > 10 || num2 < 1 || num2 > 10) {
            throw new Exception("Числа должны быть от 1 до 10");
        }


        int result = performOperation(num1, num2, operator);


        return isArabic ? String.valueOf(result) : arabicToRoman(result);
    }

    private static int romanToArabic(String roman) throws Exception {
        Map<Character, Integer> map = Map.of('I', 1, 'V', 5, 'X', 10);
        int total = 0, prev = 0;
        for (int i = roman.length() - 1; i >= 0; i--) {
            int value = map.getOrDefault(roman.charAt(i), -1);
            if (value == -1) throw new Exception("Неверное римское число");
            total += (value < prev) ? -value : value;
            prev = value;
        }
        return total;
    }

    private static String arabicToRoman(int number) {
        int[] values = {10, 9, 5, 4, 1};
        String[] symbols = {"X", "IX", "V", "IV", "I"};
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            while (number >= values[i]) {
                sb.append(symbols[i]);
                number -= values[i];
            }
        }
        return sb.toString();
    }

    private static int performOperation(int a, int b, String op) throws Exception {
        return switch (op) {
            case "+" -> a + b;
            case "-" -> a - b;
            case "*" -> a * b;
            case "/" -> {
                if (b == 0) throw new Exception("Деление на ноль недопустимо");
                yield a / b;
            }
            default -> throw new Exception("Недопустимый оператор");
        };
    }
}
