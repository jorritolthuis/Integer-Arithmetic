package main;

public class Add extends Operation {

    public Add(BigInt x, BigInt y) {
        super(x, y);
    }

    @Override
    public BigInt compute() {
        boolean sign = x.isPositive == y.isPositive;

        if (!sign) {
            y.isPositive = !y.isPositive;
            return new Subtract(x, y).compute();
        }
        String answer = "0";
        int maxLength = (x.val.length() < y.val.length()) ? y.val.length() : x.val.length();

        for(int i = maxLength - 1; i >= 0; i--) {
            int correctxiIndex = i - (maxLength - x.val.length());
            int correctyiIndex = i - (maxLength - y.val.length());
            int xi = 0;
            int yi = 0;
            if(correctxiIndex >= 0) {
                xi = Integer.parseInt(x.val.substring(correctxiIndex, correctxiIndex+1), rad);
            }
            if(correctyiIndex >= 0) {
                yi = Integer.parseInt(y.val.substring(correctyiIndex, correctyiIndex+1), rad);
            }
            int positionInAnswer = reverseIndex(i, maxLength);
            int value = xi + yi;
            nAdd++;
            int carry = value / rad;

            value = value % rad;
            answer = addSingleDigit(value, reverseIndex(positionInAnswer, answer.length()), answer, false);

            if(carry > 0) {
                answer = addSingleDigit(carry, reverseIndex(positionInAnswer, answer.length()) - 1, answer, true);
            }
        }
        return new BigInt(answer, x.isPositive, rad);
    }
}