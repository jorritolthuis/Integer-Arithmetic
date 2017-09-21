package main;

public class Add extends Operation {

    public Add(BigInt x, BigInt y) {
        super(x, y);
    }

    @Override
    public BigInt compute() {
        /**
         * This variable is used to check whether it can be done easily with
         * subtract or not.
         */
        boolean sign = x.isPositive == y.isPositive;
        /**
         * If one of the numbers was negative, then this is actually a
         * subtraction.
         */
        if (!sign) {
            //Switch the sign of the second number because
            // x + y == x -- y != x - y
            return new Subtract(new BigInt(x.val, x.isPositive, rad), new BigInt(y.val, !y.isPositive, rad)).compute();
        }
        //Start with answer 0
        String answer = "0";
        //Get the length of the largest number
        int maxLength = (x.val.length() < y.val.length()) ? y.val.length() : x.val.length();
        
        //Count from least significant digit to most sigificant digit
        for(int i = maxLength - 1; i >= 0; i--) {
            int correctxiIndex = i - (maxLength - x.val.length());
            int correctyiIndex = i - (maxLength - y.val.length());
            int xi = 0;
            int yi = 0;
            if(correctxiIndex >= 0) { //Used to check if the index falls out of
                                      // the smaller number
                xi = Integer.parseInt(x.val.substring(correctxiIndex, correctxiIndex+1), rad);
            }
            if(correctyiIndex >= 0) {
                yi = Integer.parseInt(y.val.substring(correctyiIndex, correctyiIndex+1), rad);
            }
            /**
             * Use the function to get to know where in the answer the digit has
             * to be added.
             */
            int positionInAnswer = reverseIndex(i, maxLength);
            int value = xi + yi;
            nAdd++;
            //Split value in carry and digit to be added to the answer.
            int carry = value / rad;
            value = value % rad;
            //Add the digit at the right index using reverseIndex()
            answer = addSingleDigit(value, reverseIndex(positionInAnswer, answer.length()), answer, false);
            
            if(carry > 0) {
                //Add the carry 1 position to the left, meaning it is 1 more
                // significant than the normal value.
                answer = addSingleDigit(carry, reverseIndex(positionInAnswer, answer.length()) - 1, answer, true);
            }
        }
        return new BigInt(answer, x.isPositive, rad);
    }
}