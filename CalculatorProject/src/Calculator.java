
import java.util.ArrayList;

public class Calculator {
    Calculator() {
    }

    double calculate(String input) {
        ArrayList<Double> numbers = new ArrayList<>();
        ArrayList<Character> operations = new ArrayList<>();
        for (int i = 0; i < input.length(); i++) {
            if (Character.isDigit(input.charAt(i))) {
                StringBuilder num = new StringBuilder();
                while (i < input.length() && (Character.isDigit(input.charAt(i)) || input.charAt(i) == '.')) {
                    num.append(input.charAt(i));
                    i++;
                }
                numbers.add(Double.parseDouble(num.toString()));
                i--;
            } else if ("+-x/".indexOf(input.charAt(i)) != -1) {
                operations.add(input.charAt(i));
            }
        }

        for (int i = 0; i < operations.size(); i++) {
            if (operations.get(i) == 'x' || operations.get(i) == '/') {
                double a = numbers.get(i);
                double b = numbers.get(i + 1);
                double result = operations.get(i) == 'x' ? a * b : a / b;
                numbers.set(i, result);
                numbers.remove(i + 1);
                operations.remove(i);
                i--;
            }
        }

        for (int i = 0; i < operations.size(); i++) {
            double a = numbers.get(i);
            double b = numbers.get(i + 1);
            double result = operations.get(i) == '+' ? a + b : a - b;
            numbers.set(i, result);
            numbers.remove(i + 1);
        }

        return numbers.get(0);
    }

}

