package com.implemica;


import java.math.BigInteger;

/**
 * TASK:
 * Find the sum of the digits in the number 100! (i.e. 100 factorial)
 * {Correct answer: 648}
 */

public class ThirdTask {

    public static void main(String[] args) {
        System.out.println(getSum(100));
    }

    /**
     * BigInteger - Immutable arbitrary-precision integers.
     *
     * @param num - Value for finding the sum of the digits of the factorial
     * @return sum of the digits
     */
    private static long getSum(int num) {
        // Consider the factorial of a number and write it into a variable fact
        BigInteger factorial = fact(new BigInteger("" + num));
        long sum = 0;

        // factorial.compareTo(BigInteger.ZERO) compares this BigInteger with constant ZERO
        // return 0 if factorial == 0
        // return -1 if factorial < 0
        // return 1 if factorial > 0
        while (factorial.compareTo(BigInteger.ZERO) > 0) {
            // fact%10 == fact.mod(BigInteger.TEN) - Looks for the remainder of the division
            // Then convert to long type and write to variable sum
            sum += factorial.mod(BigInteger.TEN).longValue();

            // fact/10 == fact.divide(BigInteger.TEN)
            factorial = factorial.divide(BigInteger.TEN);
        }
        return sum;
    }

    private static BigInteger fact(BigInteger num) {
        if (num.equals(BigInteger.ONE)) return BigInteger.ONE;
        return num.multiply(fact(num.subtract(BigInteger.ONE)));
    }
}
