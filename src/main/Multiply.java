package main;

public class Multiply extends Operation {
    
    public Multiply(BigInt x, BigInt y) {
        super(x, y);
    }

    @Override
    public BigInt compute(){
        boolean sign = x.isPositive == y.isPositive;
        String answer = "0";
        
        for (int i = x.val.length() - 1; i >= 0; i--) {
            for (int j = y.val.length() - 1; j >= 0; j--) {
                int xi = Integer.parseInt(x.val.substring(i, i+1), rad);
                int yi = Integer.parseInt(y.val.substring(j, j+1), rad);
                int positionInAnswer = reverseIndex(i, x.val.length()) + reverseIndex(j, y.val.length());
                int value = xi * yi;
                nMultiply++;
                int carry = value / rad;
                
                value = value % rad;
                answer = addSingleDigit(value, reverseIndex(positionInAnswer, answer.length()), answer, false);
                
                if(carry > 0) {
                    answer = addSingleDigit(carry, reverseIndex(positionInAnswer, answer.length()) - 1, answer, true);
                }
            }
        }
        
        return new BigInt(answer, sign, rad);
    }
}
 