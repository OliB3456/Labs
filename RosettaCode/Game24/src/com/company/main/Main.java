package com.company.main;

import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {

    private static final Pattern singleDigitPattern = Pattern.compile("[0-9]{1,1}");
    private static final Pattern operatorPattern = Pattern.compile("[\\+\\-\\*/]{1,1}");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("Generating 4 digits...");
            Integer[] digits = IntStream.generate(ThreadLocalRandom.current()::nextInt)
                    .filter(i -> 0 <= i && i <= 9)
                    .limit(4)
                    //.peek(i -> System.out.println(""+i))
                    .boxed()
                    .toArray(Integer[]::new);
            boolean inputCorrect = false;

            String digitsStr = arrToString(digits);

            do {
                System.out.println("Put in a mathematical expression (+-/*) that evaluates to 24 with these 4 digits: " + digitsStr);
                System.out.print("Expression: ");
                String input = scanner.nextLine();
                input = input.replaceAll(" ", "");

                if(isExpression(input)) {
                    System.out.println("Input is a valid mathematical expression...");
                    inputCorrect = checkExpression(input);
                }

                if(!inputCorrect) {
                    System.out.println("The Expression does not evaluate to 24! RETRY\n");
                }
            } while(!inputCorrect);

            System.out.println("Do you want to play another round?");
        } while(repeat(scanner.nextLine()));
    }

    private static <G> String arrToString(G[] arr) {
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

    private static boolean checkExpression(String input) {
        Integer[] digits = Arrays.stream(input.split(operatorPattern.pattern())).map(d -> Integer.valueOf(d)).toArray(Integer[]::new);
        String[] operators = (String[]) Arrays.stream(input.split(singleDigitPattern.pattern())).toArray();
        System.out.println("Input digits: " + arrToString(digits));
        int result = digits[0];
        for (int i = 0; i < digits.length; i++) {

        }
        return false;
    }
}
