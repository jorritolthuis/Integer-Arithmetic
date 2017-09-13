package main;

import java.io.FileNotFoundException;

public class IntegerArithmetic {

    static Operation[] operation;
    static InputHandler inputHandler;
    static OutputHandler outputHandler;
    public static BigInt input[];
    static BigInt[] results;
    
    public static void main(String[] args) {
        // Get input
        System.out.print("Start program:");
        try{
            System.out.print("starting trying");
            inputHandler = new InputHandler(args);
            System.out.print("tried");
        }catch(FileNotFoundException e){
            System.err.println("ERROR - The input file was not found");
            return;
        }
        input = inputHandler.getInput();
        operation = new Operation[input.length];
        System.out.println("deciding operation;");
        // Decide for operation
        for (int i = 0; i < inputHandler.operation.length && inputHandler.operation[i] != 0; i++) {
            switch(inputHandler.operation[i]){
                case 'a':
                    operation[i] = new Add(input[i*2], input[i*2 + 1]);
                    break;
                case 's':
                    operation[i] = new Subtract(input[i*2], input[i*2 + 1]);
                    break;
                case 'm':
                    operation[i] = new Multiply(input[i*2], input[i*2 + 1]);
                    break;
                case 'k':
                    operation[i] = new Karatsuba(input[i*2], input[i*2 + 1]);
                    break;
                default:
                    assert false;
            }
        }
        
        results = new BigInt[inputHandler.operation.length];
        
        for (int i = 0; i < results.length && operation[i] != null; i++) {
            results[i] = operation[i].compute();
        }
        
        // Execute operation
        
        
        // Give output
        outputHandler = new OutputHandler(results, inputHandler.file);
        outputHandler.giveOutput();
    }
    
}
