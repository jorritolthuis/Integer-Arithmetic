package main;

import java.io.FileNotFoundException;

public class IntegerArithmetic {

    static Operation operation;
    static InputHandler inputHandler;
    static OutputHandler outputHandler;
    public static BigInt input[];
    static BigInt result;
    
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
        System.out.println("deciding operation;");
        // Decide for operation
        switch(inputHandler.operation){
            case 'a':
                operation = new Add(input[0], input[1]);
                break;
            case 's':
                operation = new Subtract(input[0], input[1]);
                break;
            case 'm':
                operation = new Multiply(input[0], input[1]);
                break;
            case 'k':
                operation = new Karatsuba(input[0], input[1]);
                break;
            default:
                assert false;
        }
        
        // Execute operation
        result = operation.compute();
        
        // Give output
        outputHandler = new OutputHandler(result, inputHandler.file);
        outputHandler.giveOutput();
    }
    
}
