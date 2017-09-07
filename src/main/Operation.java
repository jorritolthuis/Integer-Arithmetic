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
    
    Operation(BigInt x, BigInt y){
        assert x.val.length() == y.val.length();
        
        this.x = x;
        this.y = y;
    }
    
    BigInt compute(){
        return null;
    }
}
