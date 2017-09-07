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
                    handleFile();
                } else {
                    throw new Error("No input file!");
                }
            } else {
                scanner = new Scanner(System.in);
                handleSystemIn();
            }
        }
    }

    private void handleFile() {
        String line;
        String bigInt1 = "";
        String bigInt2 = "";
        char sign;
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
            
            if(firstWord.equals(isX)) {
                lineScanner.next();
            }
            
            if(firstWord.equals(isY)) {
                lineScanner.next();
            }
        }
        
        input = new BigInt[]{
            new BigInt(bigInt1, operation, radix),
            new BigInt(bigInt2, operation, radix)
        };
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

    private void handleSystemIn() {
        
    }

    public BigInt[] getInput() {
        return input;
    }
}
