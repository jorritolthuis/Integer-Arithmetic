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

        if (!(x.isPositive) && !(y.isPositive)) {   // Subtracting two negative numbers is the same as turning both positive and switching the subtract order.
            BigInt temp = x;
            x = y;
            y = temp;
            x.isPositive = true;
            y.isPositive = true;
        }

        if ((x.isPositive && y.isPositive)) {
            boolean check = false;
            int xLength = x.val.length();
            int yLength = y.val.length();
            int minLength = Math.min(xLength, yLength);
            int carry = 0;
            int xi = 0;
            int yi = 0;
            int nTimes = 0;
            BigInt z = new BigInt("", x.isPositive, x.rad);
            z.rad = x.rad;
            int zLength = z.val.length();
            int t;
            
            //Cut off leading zeros X
            if (Integer.parseInt((Character.toString(x.val.charAt(0))), x.rad) == 0){

                while (Integer.parseInt((Character.toString(x.val.charAt(0))), x.rad) == 0){    //Starting character still a 0
                    x.val = x.val.substring(x.val.charAt(1), x.val.charAt(xLength-1) );         //Defining new string to start 1 character later
                }
            }
            
            //Cut off leading zeros Y
            if (Integer.parseInt((Character.toString(y.val.charAt(0))), x.rad) == 0){

                while (Integer.parseInt((Character.toString(y.val.charAt(0))), x.rad) == 0){    //Starting character still a 0
                    y.val = y.val.substring(y.val.charAt(1), y.val.charAt(xLength-1) );         //Defining new string to start 1 character later
                }
            }
            
            
            

            if (yLength > xLength) {
                BigInt temp = x;
                x = y;
                y = temp;
                check = true;       //checking if we are doing -(B-A) and thus have to make the result of B-A negative.
            }

            if (xLength == yLength) {   //on equal size, check which is the bigger number and subtract the smaller from the bigger A-B <=> -(B-A)
                for (int j = 0; j < xLength - 1; j++) {
                    xi = Integer.parseInt((Character.toString(x.val.charAt(j))), x.rad);
                    yi = Integer.parseInt((Character.toString(y.val.charAt(j))), x.rad);
                    if (xi > yi) {  //checking from the first digit onward, once the x digit is greater than its y counterpart, x is bigger 
                        break;
                    } else if (xi < yi) {//checking from the first digit onward, once the y digit is greater than its x counterpart, y is bigger
                        //xi = yi shows us nothing 
                        BigInt temp = x;
                        x = y;
                        y = temp;
                        check = true;       //checking if we are doing -(B-A) and thus have to make the result of B-A negative.
                        break;
                    }
                }

            }

            for (int i = 0; i < minLength; i++) {
                xi = Integer.parseInt((Character.toString(x.val.charAt(xLength - 1 - i))), x.rad);
                yi = Integer.parseInt((Character.toString(y.val.charAt(yLength - 1 - i))), x.rad);
                //
                t = xi - yi - carry;    //carry also needs to get deducted from xi
                if (t < 0) {
                    t = x.rad + t;  //t < 0 so t will always be at most x.rad
                    carry = 1;

                } else {
                    carry = 0;
                }
                z.val = Integer.toHexString(t) + z.val; //add number to string and copy already calculated part of the string
            }

            while (carry == 1 && (xLength > (yLength + nTimes))) {

                t = Integer.parseInt(Character.toString(x.val.charAt(xLength - yLength - 1 - nTimes))) - carry;
                if (t < 0) {
                    t = x.rad + t;
                    carry = 1;
                } else {
                    carry = 0;
                }
                nTimes++;
                z.val = Integer.toHexString(t) + z.val; //add number to string and copy already calculated part of the string
            }

            while (carry == 1 && (yLength > (xLength + nTimes))) {

                t = Integer.parseInt(Character.toString(y.val.charAt(yLength - xLength - 1 - nTimes))) - carry;
                if (t < 0) {
                    t = x.rad + t;
                    carry = 1;
                } else {
                    carry = 0;
                }
                nTimes++;
                z.val = Integer.toHexString(t) + z.val;

            }

            //Onderstaande same Length & yLength > xLength code waarschijnlijk niet meer nodig vanwege if-statements bovenaan
            if (carry == 1) { //Same length

                z.val = Integer.toHexString(carry) + z.val;
            } else if (xLength > (yLength + nTimes)) // copying the rest of the string from x because x is a longer number
            {
                z.val = x.val.substring(0, xLength - yLength - nTimes) + z.val;
            } else if (yLength > (xLength + nTimes))// copying the rest of the string from y because y is a longer number
            {
                z.val = y.val.substring(0, yLength - xLength - nTimes) + z.val;
            }

            /*      
            
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
                z.isPositive = false;
            }

            if (yLength < xLength) {
                z.isPositive = false;
            }*/
 /*  if ((xLength < yLength) || (zLength > xLength)) {
                z.isPositive = false;
            }*/
            if (check == true) {
                z.isPositive = false;
            }

            return z;

        } else if ((x.isPositive) && !(y.isPositive)) { //Subtracting a negative from a positive number is the same as turning the negative to positive and adding them.
            y.isPositive = true;
            Operation SubPosNeg = new Add(y, x);
            return SubPosNeg.compute();

        } else if (!(x.isPositive) && (y.isPositive)) { //Subtracting a positive from a negative number is the same as turning the positive to negative and adding them.
            y.isPositive = false;
            Operation SubNegPos = new Add(x, y);
            return SubNegPos.compute();
        }

        return null;
    }
}
