package main;

public class Subtract extends Operation {

    public Subtract(BigInt x, BigInt y) {
        super(x, y);
    }

    public BigInt compute() {
        stripNull(x);
        stripNull(y);
        
        boolean sign = x.isPositive == y.isPositive;
        
        if(!sign) {                         //Check if we are trying to subtract negative from positive or the other way around
            y.isPositive = !y.isPositive;   //switch the sign (pos/neg) of the second number and send both through to Add as it's easier to compute there
            return new Add(x,y).compute();
        }
        
        String answer = "0";
        int maxLength;
        int carry = Integer.parseInt("0", rad);
        String largestNumber;
        String smallerNumber;
        
        int compareNumbers = compare(x.val, y.val);
        
        if (compareNumbers > 0) {               //Case: X is greater than Y
            maxLength = x.val.length();
            largestNumber = x.val;
            smallerNumber = y.val;
            sign = x.isPositive;                //Set the sign (pos/neg) to that of X for the final result
        } else if(compareNumbers < 0) {         //Case: Y is greater than X
            maxLength = y.val.length();
            largestNumber = y.val;
            smallerNumber = x.val;
            sign = !y.isPositive;               //Set the sign (pos/neg) to the inverse of that of Y for the final result (Subtracting a big number from a smaller number)
        } else {
            return new BigInt("0", true, rad);  //Case: equal numbers
        }
        /*
        
        Check both X and Y simulatneously for their last digit (going from last all the way to front)
        If one of both has run out of digits and the other has not, the digit picked for the run out number will be 0 because subtracting must continue until both run out.
        If the calculated digit-value (xi - yi - carry) turns out to be less than 0, the new carry is set to 1 and the value itself is corrected by adding the radix to it.
        After each calcuated digit-value, it will be added to the string that will get returned as the answer.
        
        */
        for (int i = maxLength - 1; i >= 0; i--) {
            int correctxiIndex = i - (maxLength - largestNumber.length());
            int correctyiIndex = i - (maxLength - smallerNumber.length());
            int xi = 0;
            int yi = 0;
            if(correctxiIndex >= 0) {
                xi = Integer.parseInt(largestNumber.substring(correctxiIndex, correctxiIndex+1), rad);
            }
            if(correctyiIndex >= 0) {
                yi = Integer.parseInt(smallerNumber.substring(correctyiIndex, correctyiIndex+1), rad);
            }
            int positionInAnswer = reverseIndex(i, maxLength);
            int value = xi - yi - carry;
            
            if(value < 0) {
                carry = 1;
                value = rad + value;
            } else if(value == 0 && i == 0) {
                break;
            } else {
                carry = 0;
            }

            value = value % rad;
            answer = addSingleDigit(value, reverseIndex(positionInAnswer, answer.length()), answer, false);
        }
        nAdd++;     //Add 1 to the counter for elementary additions/subtractions
        return new BigInt(answer, sign, rad);
    }
}