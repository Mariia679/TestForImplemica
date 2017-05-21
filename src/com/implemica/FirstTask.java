package com.implemica;

import com.implemica.first.ValidBraces;

/**
 *
 * http://informatics.mccme.ru/mod/book/view.php?id=266&chapterid=58
 */

public class FirstTask {

    public static void main(String[] args) {

        ValidBraces calc = new ValidBraces();

        int numValidBracesSequences = calc.calculateCountOfValidBracesSequences();

        System.out.println("Number of valid braces sequences = " + numValidBracesSequences);
    }

}
