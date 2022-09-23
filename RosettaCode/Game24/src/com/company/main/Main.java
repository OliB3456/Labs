package com.company.main;

import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {

    private static final Pattern singleDigitPattern = Pattern.compile("[1-9]{1,1}");
    private static final Pattern operatorPattern = Pattern.compile("[\\+\\-\\*/]{1,1}");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("Generating 4 digits...");
            Integer[] digits = IntStream.generate(ThreadLocalRandom.current()::nextInt)
                    .filter(i -> 0 < i && i <= 9)
                    .limit(4)
                    .sorted()
                    //.peek(i -> System.out.println(""+i))
                    .boxed()
                    .toArray(Integer[]::new);
            boolean inputCorrect = false;

            String digitsStr = arrToString(digits);

            do {
                System.out.println("\nPut in a mathematical expression (+-/*) that evaluates to 24 with these 4 digits: " + digitsStr);
                System.out.print("Expression: ");
                String input = scanner.nextLine();
                input = input.replaceAll(" ", "");

                try {
                    if (isExpression(input)) {
                        System.out.println("Input is a valid mathematical expression...");
                        inputCorrect = checkExpression(input, digits);
                    }

                    if (!inputCorrect) {
                        System.out.println("The Expression does not evaluate to 24! RETRY\n");
                    }
                } catch(IllegalArgumentException iae) {
                    System.out.println(iae.getMessage());
                }

            } while(!inputCorrect);

            System.out.println("Do you want to play another round?");
        } while(repeat(scanner.nextLine()));
    }

    private static <G> String arrToString(G[] arr) {
        if (arr.length == 0) {
            return new String();
        }

        StringBuilder sb = new StringBuilder();
        for (G i: arr) {
            sb.append(i).append(", ");
        }
        return sb.substring(0, sb.length()-2);
    }

    private static boolean repeat(String repeat) {
        if(!repeat.isBlank()) {
           return repeat.equalsIgnoreCase("yes") || repeat.equalsIgnoreCase("y");
        }
        return false;
    }

    private static boolean isExpression(String input) {
        Pattern isExpression = Pattern.compile("(" + singleDigitPattern + operatorPattern + ")*" + singleDigitPattern);
        return isExpression.matcher(input).matches();
    }

    private static boolean checkExpression(String input, Integer[] allowedDigits) {
        Integer[] digits = Arrays.stream(input.split(operatorPattern.pattern())).map(d -> Integer.valueOf(d)).toArray(Integer[]::new);
        String[] operators = Arrays.stream(input.split(singleDigitPattern.pattern())).filter(s -> !s.isBlank()).toArray(String[]::new);
        System.out.println(arrToString(digits));
        System.out.println(arrToString(operators));

        // Should never be true but just in case
        if (digits.length != (operators.length + 1)) {
            throw new IllegalArgumentException("Your input must start and end with a digit. Only single digits (1-9) and single operation symbols (+,-,*,/) are allowed! After every single digit a single operation symbol must follow!");
        }

        checkDigitswithStreams(digits, allowedDigits);

        boolean is24 = doesExpressionEvaluateTo24(digits, operators);

        return false;
    }

    /**
     * inputDigits must contain no other digits than found in allowedDigits
     * inputDigits must contain all digits from allowedDigits at least once
     * (or more if digit occurs multiple times in allowedDigits)
     * @param inputDigits Digits put in by user
     * @param allowedDigits Generated Digits
     * throws IllegalArgumentException if input contains other digit than allowed
     * throws IllegalArgumentException if input does not contain a digit from allowed
     * returns if input is valid
     */
    private static void checkDigitswithStreams(Integer[] inputDigits, Integer[] allowedDigits) {
        Integer[] distinctSortedInputDigits = Arrays.stream(inputDigits).sorted().distinct().toArray(Integer[]::new);
        Arrays.stream(distinctSortedInputDigits).forEach(i -> {
            if (contains(allowedDigits, i) == -1) {
                throw new IllegalArgumentException("Input must not contain any other digit than the generated ones! Digit: " + i + " not found in generated ones: " + arrToString(allowedDigits));
            }
        });
        Arrays.stream(allowedDigits).forEach(i -> {
            if (contains(distinctSortedInputDigits, i) == -1) {
                throw new IllegalArgumentException("Input must contain every given digit at leas once! Digit: " + i + " of given digits not found in your input: " + arrToString(inputDigits));
            }
        });
    }

    private static int contains(Integer[] array, Integer a) {
        for(int i = 0; i < array.length; i++) {
            if(array[i].equals(a)) {
                return i;
            }
        }
        return -1;
    }

    // TODO: */ before +-
    private static boolean doesExpressionEvaluateTo24(Integer[] digits, String[] operators) {
        Integer result = digits[0];
        for (int i = 1; i < digits.length; i++) {
            result = performMathematicalOperation(result, digits[i], operators[i-1]);
        }
        System.out.println(result);
        return result.equals(Integer.valueOf(24));
    }

    private static Integer performMathematicalOperation(Integer first, Integer second, String operator) {
        switch (operator) {
            case "+":
                first += second;
                break;
            case "-":
                first -= second;
                break;
            case "*":
                first *= second;
                break;
            case "/":
                first /= second;
                break;
        }
        return first;
    }
}
