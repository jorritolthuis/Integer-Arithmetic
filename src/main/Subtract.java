package main;

public class Subtract extends Operation {

    public Subtract(BigInt x, BigInt y) {
        super(x, y);
    }

    public BigInt compute() {
        stripNull(x);
        stripNull(y);
        
        boolean sign = x.isPositive == y.isPositive;
        
        if(!sign) {
            y.isPositive = !y.isPositive;
            return new Add(x,y).compute();
        }
        
        String answer = "0";
        int maxLength;
        int carry = Integer.parseInt("0", rad);
        String largestNumber;
        String smallerNumber;
        
        int compareNumbers = compare(x.val, y.val);
        
        if (compareNumbers > 0) {
            maxLength = x.val.length();
            largestNumber = x.val;
            smallerNumber = y.val;
            sign = x.isPositive;
        } else if(compareNumbers < 0) {
            maxLength = y.val.length();
            largestNumber = y.val;
            smallerNumber = x.val;
            sign = !y.isPositive;
        } else {
            return new BigInt("0", true, rad);
        }
        
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
        return new BigInt(answer, sign, rad);
    }
}