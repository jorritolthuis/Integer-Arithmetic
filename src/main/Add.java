package main;

public class Add extends Operation {

    public Add(BigInt x, BigInt y) {
        super(x, y);
        int xLength = x.val.length();
        int yLength = y.val.length();
        int maxLength = Math.max(xLength,yLength);
        int carry = 0;
        int xi =0;
        int yi=0;
        BigInt z = new BigInt("",x.isPositive,x.rad);
        z.rad = x.rad;
        int t;
        for(int i =0; i<maxLength;i++)
        {
            xi = Integer.parseInt((Character.toString(x.val.charAt(xLength-1-i))),x.rad);
            yi = Integer.parseInt((Character.toString(x.val.charAt(yLength-1-i))),x.rad);
            
            t = xi + yi+carry;
            if(t>= x.rad)
            {
                carry = 1;
                t = t - x.rad;
            }
            else
            {
                carry = 0;
            }
            z.val = Integer.toHexString(t)+z.val; 
        }
        if(carry == 1)
        {
            z.val = Integer.toHexString(carry)+z.val;
        }

    }

    @Override
    public BigInt compute(){
        return null;
    }
}
