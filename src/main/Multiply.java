package main;

public class Multiply extends Operation {

    int rad;
    
    public Multiply(BigInt x, BigInt y) {
        super(x, y);
        this.rad = x.rad;
    }

    @Override
    public BigInt compute(){
        assert x.rad == y.rad;
        boolean sign = x.isPositive == y.isPositive;
        String answer = "0";
        
        
        for (int i = x.val.length() - 1; i >= 0; i--) {
            for (int j = y.val.length() - 1; j >= 0; j--) {
                int currentX = Integer.parseInt(x.val.substring(i, i+1), rad);
                int currentY = Integer.parseInt(y.val.substring(j, j+1), rad);
                int positionInAnswer = reverseIndex(i, x.val.length()) + reverseIndex(j, y.val.length());
                int value = currentX * currentY;
                nMultiply++;
                int carry = value / rad;
                
                value = value % rad;
                if(positionInAnswer >= answer.length()) {
                    answer = Integer.toString(value, rad).concat(answer);
                } else {
                    answer = addSingleDigit(value, reverseIndex(positionInAnswer, answer.length()), answer);
                }
                if(carry > 0) {
                    answer = addSingleDigit(carry, reverseIndex(positionInAnswer, answer.length()) - 1, answer);
                }
            }
        }
        
        return new BigInt(answer, sign, rad);
    }
    
    String addSingleDigit(int carry, int carryPosition, String answer) {
        assert carry < rad;
        if(carry == 0) {
            return answer;
        }
        if(carryPosition == -1) {
            return Integer.toString(carry).concat(answer);
        }
        int value = carry + Integer.parseInt(answer.substring(carryPosition, carryPosition + 1), rad);
        nAdd++;
        int newCarry = value / rad;
        int setAnswer = value % rad;
        
        char[] a = answer.toCharArray();
        a[carryPosition] = Character.forDigit(setAnswer, rad);
        
        return addSingleDigit(newCarry, --carryPosition, String.valueOf(a));
    }
    
    int reverseIndex(int index, int maxLength) {
        return maxLength - index - 1;
    }
}
 