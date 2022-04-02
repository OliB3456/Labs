package com.company;

import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class Main {

    private static Pattern singleDigitPattern = Pattern.compile("[0-9]{1,1}");
    private static Pattern operatorPattern = Pattern.compile("[\\+\\-\\*/]{1,1}");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("Generating 4 digits...");
            int[] digits = IntStream.generate(ThreadLocalRandom.current()::nextInt)
                    .filter(i -> 0 <= i && i <= 9)
                    .limit(4)
                    //.peek(i -> System.out.println(""+i))
                    .toArray();
            boolean inputCorrect = false;

            String digitsStr = intArrToString(digits);

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

    // Make generic if possible
    private static String intArrToString(int[] arr) {
        StringBuilder sb = new StringBuilder();
        for (int i: arr) {
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
        int[] digits = Arrays.stream(input.split(operatorPattern.pattern())).mapToInt(d -> Integer.parseInt(d)).toArray();
        String[] operators = (String[]) Arrays.stream(input.split(singleDigitPattern.pattern())).toArray();
        System.out.println("Input digits: " + intArrToString(digits));
        int result = digits[0];
        for(int i = 0; i < digits.length; i++) {

        }
        return false;
    }
}
