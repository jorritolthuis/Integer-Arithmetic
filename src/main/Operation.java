package main;

abstract class Operation {
    BigInt x;
    BigInt y;
    int nMultiply; // Should contains the number of multiplications needed to return
    int nAdd; // Should contains the number of additions/subtractions needed to return
    int rad;
    
    Operation(BigInt x, BigInt y){
        assert x.val.length() == y.val.length();
        assert x.rad == y.rad;
        
        this.x = x;
        this.y = y;
        this.rad = x.rad;
    }
    
    BigInt compute(){
        return null;
    }
    
    /**
     * Return the answer of a long string of numbers plus one single digit.
     * This digit can be added anywhere within the bounds of the answer.
     * Meaning 10 + 1 = 11 with digit being 1 and answer being 10.
     * Or 9900 + 100 = 10000 with digit being  1, position being 1 and
     * answer being 9900.
     * @param digit a single digit that needs to be added to <code>answer</code>.
     * @param position a position of the digit relative to the length of answer.
     * The position corresponds to the index of the array
     * that is created from <code>answer.toCharArray()</code>.
     * @param answer the number to which the digit needs to be added.
     * @param isDigitACarry true if the digit is a carry. This is used to handle
     * leading zeros in the answer.
     * @return returns answer + digit, the carry is handled as well.
     */
    String addSingleDigit(int digit, int position, String answer, boolean isDigitACarry) {
        assert digit < rad;
        if(digit == 0 && isDigitACarry) {
            return answer;
        }
        if(position == -1) {
            return String.valueOf(Character.forDigit(digit, rad)).concat(answer);
        }
        int value = digit + Integer.parseInt(answer.substring(position, position + 1), rad);
        nAdd++;
        int newCarry = value / rad;
        int setAnswer = value % rad;
        
        char[] answerAsArray = answer.toCharArray();
        answerAsArray[position] = Character.forDigit(setAnswer, rad);
        
        return addSingleDigit(newCarry, --position, String.valueOf(answerAsArray), true);
    }
    
    /**
     * Reverses the index. The character array indexes can be confusing.
     * When reversed 0 represents the least significant digit and
     * <code>maxLength</code> the most significant one.
     * When called for the second time the index in reverted to array index
     * once again.
     * @param index index to be converted.
     * @param maxLength the maximum length of the array the index was from.
     * @return returns the reverted index.
     */
    int reverseIndex(int index, int maxLength) {
        return maxLength - index - 1;
    }
    
    int compare(String b1, String b2) {
        if(b1.length() > b2.length()) {
            return 1;
        }
        if(b1.length() < b2.length()) {
            return -1;
        }
        
        for (int i = 0; i < b1.length(); i++) {
            char charB1 = b1.charAt(i);
            char charB2 = b2.charAt(i);
            if(charB1 != charB2) {
                return (charB1 > charB2) ? 1 : -1;
            }
        }
        
        return 0;
    }
    
    /**
     * Strips leading zeros from front of BigInt
     * @param x 
     */
    void stripNull(BigInt x){
        System.err.println("abc " + x);
        while(x.val.length() > 1 && x.val.charAt(0) == '0'){
            x.val = x.val.substring(1);
        }
        
        System.err.println("abc " + x);
    }
}
