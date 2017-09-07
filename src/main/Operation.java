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
abstract class Operation {
    BigInt x;
    BigInt y;
    
    Operation(BigInt x, BigInt y){
        this.x = x;
        this.y = y;
    }
    
    BigInt compute(){
        return null;
    }
}
