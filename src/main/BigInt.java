/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

/**
 *
 * @author s157710
 */
public class BigInt {
    public String val;
    public char sign;
    public int rad;
    
    public BigInt(String value, char sign, int radix){
        this.val = value;
        this.sign = sign;
        this.rad = radix;
    }
}
