package main;

public class BigInt {
    public String val;
    public boolean isPositive;
    public int rad;
    
    public BigInt(String value, boolean positive, int radix){
        this.val = value;
        this.isPositive = positive;
        this.rad = radix;
    }
    
    @Override
    public String toString(){
        if(isPositive){
            return val;
        }else{
            return "-" + val;
        }
    }
}
