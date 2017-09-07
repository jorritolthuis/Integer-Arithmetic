package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author s147889
 */
public class InputHandler {
    Scanner scanner;
    String commentStart = "#";
    final String isRadix = "[radix]";
    final String[] operations = {
        "[add]",
        "[subtract]",
        "[multiply]",
        "[karatsuba]"
    };
    final String isX = "[x]";
    final String isY = "[y]";
    final String isAnswer = "[answer]";
    private BigInt input[];
    /**
     * a - add
     * s - subtract
     * m - multiply
     * k - karatsuba
     */
    char operation;
    File file;

    public InputHandler(String[] args) throws FileNotFoundException {
        for (int i = 0; i < args.length; i++) {
            if("-f".equals(args[i])) {
                if(args.length >= i + 2) {
                    file = new File(args[++i]);
                    scanner = new Scanner(file);
                } else {
                    throw new Error("No input file!");
                }
            } else {
                scanner = new Scanner(System.in);
            }
        }
        handle();
    }

    private void handle() {
        String line;
        String bigInt1 = "";
        String bigInt2 = "";
        boolean sign = true;
        int radix = 10;
        
        while(scanner.hasNextLine()) {
            line = scanner.nextLine();
            
            if(line.contains(commentStart)) {
                continue;
            }
            
            Scanner lineScanner = new Scanner(line);
            String firstWord = lineScanner.next();
            
            //If radix then set it
            if(firstWord.equals(isRadix)) {
                radix = lineScanner.nextInt();
            }
            
            //If operation then set it
            for (int i = 0; i < operations.length; i++) {
                if(firstWord.equals(operations[i])) {
                    operation = operationToChar(operations[i]);
                }
            }
            
            //Set x
            if(firstWord.equals(isX)) {
                String integer = lineScanner.next();
                sign = getSign(integer);
                bigInt1 = (!sign) ? removeSign(integer) : integer;
            }
            
            //Set y
            if(firstWord.equals(isY)) {
                String integer = lineScanner.next();
                sign = getSign(integer);
                bigInt2 = (!sign) ? removeSign(integer) : integer;
            }
        }
        
        input = new BigInt[]{
            new BigInt(bigInt1, sign, radix),
            new BigInt(bigInt2, sign, radix)
        };
    }
    
    private boolean getSign(String integer) {
        if(integer.startsWith("-")) {
            return true;
        }
        return false;
    }
    
    private char operationToChar(String operation) {
        char op;
        
        switch(operation) {
            case "[add]": op = 'a';
            break;
            case "[subtract]": op = 's';
            break;
            case "[multiply]": op = 'm';
            break;
            case "[karatsuba]": op = 'k';
            break;
            default: op = 'a';
        }
        
        return op;
    }

    public BigInt[] getInput() {
        return input;
    }

    private String removeSign(String integer) {
        return integer.substring(1);
    }
}
