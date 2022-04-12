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
                    inputCorrect = checkExpression(input, digits);
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

    private static boolean checkExpression(String input, Integer[] allowedDigits) {
        Integer[] digits = Arrays.stream(input.split(operatorPattern.pattern())).map(d -> Integer.valueOf(d)).toArray(Integer[]::new);
        String[] operators = Arrays.stream(input.split(singleDigitPattern.pattern())).filter(s -> s.isBlank()).toArray(String[]::new);
        System.out.println(arrToString(digits));
        System.out.println(arrToString(operators));
        checkDigits(digits, allowedDigits);
        int result = digits[0];
        for (int i = 0; i < digits.length; i++) {

        }
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
    private static void checkDigits(Integer[] inputDigits, Integer[] allowedDigits) {
        //input: 1 1 2 3 4 4
        //allowed: 1 2 3 4

        //input: 1 2 2 4 4
        //allowed: 1 2 4 4

        //input: 1 1 2 1
        //allowed: 1 1 1 2

        String input = arrToString(inputDigits);
        String allowed = arrToString(allowedDigits);
        // for -> split; if allowed contains i

        Arrays.sort(inputDigits);
        Arrays.sort(allowedDigits);
        //new inputDigits; foreach allowedDigits find allowed in input and delete; if not found => error
        Integer[] newInput = inputDigits.clone();
        for (Integer a: allowedDigits) {
            //contains
            int index = contains(newInput, a);
            if(index==-1) {
                // error: inputDigits misses a digit from allowedDigits.
            } else {
                // delete digit from inputDigits with index "index" (multiple occurrences of same digit in allowedDigits)
                // => allowedDigits: 1 1 1 2
                // X inputDigits: 1 2 2 2
            }
        }
    }

    private static int contains(Integer[] array, Integer a) {
        for(int i = 0; i < array.length; i++) {
            if(array[i].equals(a)) {
                return i;
            }
        }
        return -1;
    }
}
