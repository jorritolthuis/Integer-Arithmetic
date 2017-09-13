package main;


public class Subtract extends Operation {

    public Subtract(BigInt x, BigInt y) {
        super(x, y);
    }

    public BigInt compute() {
        /*
        
        xpos - ypos = normal
        xpos - yneg = xpos + ypos >> to add
        xneg - yneg = xneg + ypos = ypos - xpos >> switch
        xneg - ypos = xneg + yneg >> to add
        
        */
        if (!(x.isPositive ) && !(y.isPositive)){
            BigInt temp = x;
            x = y;
            y = temp;
            x.isPositive = true;
            y.isPositive = true;
        }

        if ((x.isPositive && y.isPositive)) {    
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
                xi = Integer.parseInt((Character.toString(x.val.charAt(xLength - 1 - i))), x.rad);
                yi = Integer.parseInt((Character.toString(y.val.charAt(yLength - 1 - i))), x.rad);
                //
                t = xi - yi - carry;    //carry also needs to get deducted from xi
                if (t < 0){
                    t = x.rad + t;  //t < 0 so t will always be at most x.rad
                    carry = 1;
                 /* while (t < 0){          niet zeker of dit nodig is, waarschijnlijk toch niet gezien t niet meer dan 1x x.rad kan zijn
                        t = x.rad + t;
                        carry++;
                    }*/ 
                } else {
                    carry = 0;
                }
                z.val = Integer.toHexString(t) + z.val;
            }
            if (carry == 1 && xLength != minLength) {       
                z.val = Integer.toHexString(Integer.parseInt(Character.toString(x.val.charAt(xLength - yLength - 1))) - carry) + z.val; //setting number right with carry
                if (xLength - yLength > 1) //checking wheter part of number just have to be copied
                {
                    z.val = x.val.substring(0, xLength - yLength - 1) + z.val;
                }
            } else if (carry == 1 && yLength != minLength) {
                z.val = Integer.toHexString(Integer.parseInt(Character.toString(y.val.charAt(yLength - xLength - 1))) - carry) + z.val; //setting number right with carry
                if (yLength - xLength > 1) //checking wheter part of number just have to be copied
                {
                    z.val = y.val.substring(0, yLength - xLength - 1) + z.val;
                }
            } else if (carry == 1 && yLength == xLength) {
                z.val = Integer.toHexString(carry) + z.val;
            }
            return z;                       //waar wordt " - " gedaan?
        }

    
        return null;
    }
}
