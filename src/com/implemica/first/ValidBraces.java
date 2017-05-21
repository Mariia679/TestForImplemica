package com.implemica.first;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Valid braces sequence is:
 * 1) Empty sequence (if doesn't contain any brace)
 * 2) If "A" is a valid braces sequence, than "(A)" is a sequence inside braces
 * 3) If "A" and "B" is a valid braces sequences than "AB" is a a valid braces sequence too.
 * Some valid braces sequence is (A)B where "A" and "B" is a valid braces sequences
 * Count of valid braces sequences from 2n (where n - open brackets and n - close brackets) ==  Catalan number.
 */
public class ValidBraces {

    // List<Integer> arrayList - for storage of Catalan numbers
    private List<Integer> arrayList;

    private int countBraces;

    public ValidBraces() {
        arrayList = new ArrayList<>();

        // initialisation field countBraces
        readFromKeyboard();
    }

    /**
     * For realization task use the Catalan's recurrence relation.
     * Cn = C0Cn - 1 + C1Cn - 2 + C2Cn - 3 +...+ Cn - 2C1 + Cn - 1C0.
     *
     * @return count of valid braces sequences
     */
    public int calculateCountOfValidBracesSequences() {

        // The correct bracket sequence of length 0 (countBraces) is exactly one - empty
        if (countBraces == 0) {
            return 1;
        } else arrayList.add(1);

        int countSequences;

        for (int i = 1; i <= countBraces; ++i) {
            countSequences = 0;
            for (int j = 0; j < i; ++j) {
                countSequences += arrayList.get(j) * arrayList.get(i - 1 - j);
            }
            arrayList.add(countSequences);
        }
        return arrayList.get(countBraces);
    }

    /**
     * Method reading data from keyboard.
     * User must entered only a positive integer number.
     * When user entered the correct data, method save positive number in field countBraces for further use
     */
    private void readFromKeyboard() {

        int enteredNumber;
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the count of braces(only a positive integer number): ");
        while (true) {
            try {
                enteredNumber = Integer.parseInt(scanner.nextLine());
                if (enteredNumber < 0) throw new IOException();
                break;
            } catch (NumberFormatException ex) {
                System.out.print("Entered symbols isn't integer number, please enter number again: ");
                continue;
            } catch (IOException ex) {
                System.out.print("Number must be a positive, please enter number again: ");
                continue;
            }
        }
        countBraces = enteredNumber;
    }
}