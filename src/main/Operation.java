/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
     * @return returns answer + digit, the carry is handled as well.
     */
    String addSingleDigit(int digit, int position, String answer) {
        assert digit < rad;
        if(digit == 0) {
            return answer;
        }
        if(position == -1) {
            return Integer.toString(digit).concat(answer);
        }
        int value = digit + Integer.parseInt(answer.substring(position, position + 1), rad);
        nAdd++;
        int newCarry = value / rad;
        int setAnswer = value % rad;
        
        char[] answerAsArray = answer.toCharArray();
        answerAsArray[position] = Character.forDigit(setAnswer, rad);
        
        return addSingleDigit(newCarry, --position, String.valueOf(answerAsArray));
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
}
