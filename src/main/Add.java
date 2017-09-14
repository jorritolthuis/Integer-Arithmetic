package main;

public class Add extends Operation {

    public Add(BigInt x, BigInt y) {
        super(x, y);
    }

    @Override
    public BigInt compute() {

        if ((x.isPositive && y.isPositive) || (!x.isPositive && !y.isPositive)) {
            int xLength = x.val.length();
            int yLength = y.val.length();
            int minLength = Math.min(xLength, yLength);
            int carry = 0;
            int xi = 0;
            int yi = 0;
            BigInt z = new BigInt("", x.isPositive, x.rad);
            z.rad = x.rad;
            int t;
            for (int i = 0; i < minLength; i++) {
                xi = Integer.parseInt((Character.toString(x.val.charAt(xLength - 1 - i))), x.rad); //get the last digits to add
                yi = Integer.parseInt((Character.toString(y.val.charAt(yLength - 1 - i))), x.rad);
                t = xi + yi + carry; 
                if (t >= x.rad) { //if number is greater than the base: set carry and substract base from number 
                    carry = 1;
                    t = t - x.rad;
                } else {
                    carry = 0;
                }
                z.val = Integer.toHexString(t) + z.val; //add number to string and copy already calculated part of the string
            }
            if (carry == 1 && xLength != minLength) {
                z.val = Integer.toHexString(Integer.parseInt(Character.toString(x.val.charAt(xLength - yLength - 1))) + carry) + z.val; //setting number right with carry
                if (xLength - yLength > 1) //checking wheter part of number just have to be copied
                {
                    z.val = x.val.substring(0, xLength - yLength - 1) + z.val;
                }
            } else if (carry == 1 && yLength != minLength) {
                z.val = Integer.toHexString(Integer.parseInt(Character.toString(y.val.charAt(yLength - xLength - 1))) + carry) + z.val; //setting number right with carry
                if (yLength - xLength > 1) //checking wheter part of number just have to be copied
                {
                    z.val = y.val.substring(0, yLength - xLength - 1) + z.val;
                }
            } else if (carry == 1 && yLength == xLength) { //add another integer in the beginning of the string if carry is set
                z.val = Integer.toHexString(carry) + z.val;
            }
            return z;
        } else if (x.isPositive && !y.isPositive) {
            y.isPositive = true;
            Operation AddPosNeg = new Subtract(x, y);
            return AddPosNeg.compute();

        } else if (!x.isPositive && y.isPositive) {
            x.isPositive = true;
            Operation AddPosNeg = new Subtract(y, x);
            return AddPosNeg.compute();
        }
        return null;
    }
}
