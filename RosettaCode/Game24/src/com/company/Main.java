package com.company;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {
        System.out.println("Generating 4 digits...");
        int[] digits = IntStream.generate(ThreadLocalRandom.current()::nextInt)
                .filter(i -> 0 <= i && i <= 9)
                .limit(4)
                //.peek(i -> System.out.println(""+i))
                .toArray();
        System.out.print("Put in a mathematical expression (+-/*) that evaluates to 24 with these 4 digits: " + intArrToString(digits));
    }

    private static String intArrToString(int[] arr) {
        String s = "";
        for (int i: arr) {
            s += i + ", ";
            //s.concat(i + ", ");
        }
        s = s.substring(0, s.length()-2);
        return s;
    }
}
