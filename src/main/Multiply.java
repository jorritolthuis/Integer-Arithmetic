package main;

public class Multiply extends Operation {
    
    public Multiply(BigInt x, BigInt y) {
        super(x, y);
    }

    @Override
    public BigInt compute(){
        boolean sign = x.isPositive == y.isPositive;
        String answer = "0";
        /**
         * Numbers do not have to be of the same length because 2 for loops are
         * used to handle the indexes of the numbers. Go from least significant
         * digit to most significant digit.
         */
        for (int i = x.val.length() - 1; i >= 0; i--) {
            for (int j = y.val.length() - 1; j >= 0; j--) {
                //Get the digits at the indexes.
                int xi = Integer.parseInt(x.val.substring(i, i+1), rad);
                int yi = Integer.parseInt(y.val.substring(j, j+1), rad);
                //Calculate the index in the answer the value needs to be added
                // to.
                int positionInAnswer = reverseIndex(i, x.val.length()) + reverseIndex(j, y.val.length());
                int value = xi * yi;
                nMultiply++;
                int carry = value / rad;
                value = value % rad;
                //Add the value at the correct place
                answer = addSingleDigit(value, reverseIndex(positionInAnswer, answer.length()), answer, false);
                //Add the carry at an index 1 more significant than the value.
                if(carry > 0) {
                    answer = addSingleDigit(carry, reverseIndex(positionInAnswer, answer.length()) - 1, answer, true);
                }
            }
        }
//        nMultiply++;       //Add 1 to the counter for elementary multiplications
        return new BigInt(answer, sign, rad);
    }
}
 