package main;

public class Karatsuba extends Operation {

    public Karatsuba(BigInt x, BigInt y) {
        super(x, y);
    }

    @Override
    public BigInt compute(){
        fixLength();
        BigInt result = new BigInt("", true, x.rad);
        if(x.val.length() <= 2 || y.val.length() <= 2){ // If n==1 do simple multiplication
            Operation op = new Multiply(x, y);
            result = op.compute();
            super.nAdd = op.nAdd;
            super.nMultiply = op.nMultiply;
        }else{ // Otherwise do step (i.e. split)
            //split
            int n = x.val.length();
            String xhi = x.val.substring(0, n/2);
            String xlo = x.val.substring(n/2);
            String yhi = y.val.substring(0, n/2);
            String ylo = y.val.substring(n/2);
            
            //calculate multiplications (recurse)
            Operation opxyhi = new Karatsuba(new BigInt(xhi, x.isPositive, x.rad), new BigInt(yhi, y.isPositive, y.rad));
            Operation opxylo = new Karatsuba(new BigInt(xlo, x.isPositive, x.rad), new BigInt(ylo, y.isPositive, y.rad));
            BigInt xyhi = opxyhi.compute();
            BigInt xylo = opxylo.compute();

            // Calculate (xhi+xlo)(yhi+ylo) == cross
            Operation opxsum = new Add(new BigInt(xhi, x.isPositive, x.rad), new BigInt(xlo, x.isPositive, x.rad));
            Operation opysum = new Add(new BigInt(yhi, y.isPositive, y.rad), new BigInt(ylo, y.isPositive, y.rad));
            BigInt xsum = opxsum.compute();
            BigInt ysum = opysum.compute();
            Operation opcross = new Karatsuba(xsum, ysum);
            BigInt cross = opcross.compute();
            
            // Calculate 'middle' term
            Operation opMiddleA = new Subtract(cross, xyhi);
            Operation opMiddleB = new Subtract(opMiddleA.compute(), xylo);
            BigInt middle = opMiddleB.compute();
            
            // Get final result (first append 0's, i.e. multiply by b^x)
            xyhi.val = xyhi.val + appender(n);
            middle.val = middle.val + appender(n/2);
            Operation opAddA = new Add(xyhi, middle);
            Operation opAddB = new Add(opAddA.compute(), xylo);
            
            result = opAddB.compute();
            
            //get result (with sign)
            if(x.isPositive && !y.isPositive || !x.isPositive && y.isPositive){
                // Result should be negative
                result.isPositive = false;
            }else{
                result.isPositive = true;
            }
            
            super.nAdd = opxyhi.nAdd + opxylo.nAdd + opxsum.nAdd + opysum.nAdd + opcross.nAdd + 
                    opMiddleA.nAdd + opMiddleB.nAdd + opAddA.nAdd + opAddB.nAdd;
            super.nMultiply = opxyhi.nMultiply + opxylo.nMultiply + opxsum.nMultiply + 
                    opysum.nMultiply + opcross.nMultiply + opMiddleA.nMultiply + opMiddleB.nMultiply + 
                    opAddA.nMultiply + opAddB.nMultiply;
        }
        
        return result;
    }
    
    // Generates a string with n 0's
    private String appender(int n){
        String result = "";
        for(int i=0; i<n; ++i) result = result + "0";
        return result;
    }
    
    private void fixLength(){
        // Make both the same length
        if(x.val.length()<y.val.length()){
            x.val = appender(y.val.length()-x.val.length()) + x.val;
        }else if(x.val.length()>y.val.length()){
            y.val = appender(x.val.length()-y.val.length()) + y.val;
        }
        
        // If they have an odd number of characters, make it even
        if(x.val.length()%2 == 1){
            x.val = appender(1) + x.val;
            y.val = appender(1) + y.val;
        }
    }
}
