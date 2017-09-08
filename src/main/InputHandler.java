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
            }
        }
        if(args.length == 0) {
            scanner = new Scanner(System.in);
        }
        handle();
    }

    private void handle() {
        String line;
        String bigInt1 = "";
        String bigInt2 = "";
        int currentLength = 0;
        boolean sign = true;
        int radix = 10;
        String answer = "";
        
        while(scanner.hasNextLine()) {
            line = scanner.nextLine();
            
            if(line.contains(commentStart)) {
                continue;
            }
            
            String firstWord;
            
            Scanner lineScanner = new Scanner(line);
            if(lineScanner.hasNext()) {
                firstWord = lineScanner.next();
            } else {
                continue;
            }
            
            //If radix then set it
            if(firstWord.equals(isRadix)) {
                radix = lineScanner.nextInt();
            }
            
            //If operation then set it
            for (String op : operations) {
                if (firstWord.equals(op)) {
                    operation = operationToChar(op);
                }
            }
            
            //Set x
            if(firstWord.equals(isX)) {
                String integer = lineScanner.next();
                sign = getSign(integer);
                bigInt1 = (!sign) ? removeSign(integer) : integer;
                
                //Ensure both number are the same length
                if(currentLength < bigInt1.length()) {
                    currentLength = bigInt1.length();
                }
            }
            
            //Set y
            if(firstWord.equals(isY)) {
                String integer = lineScanner.next();
                sign = getSign(integer);
                bigInt2 = (!sign) ? removeSign(integer) : integer;
                
                //Ensure both number are the same length
                if(currentLength < bigInt2.length()) {
                    currentLength = bigInt2.length();
                    bigInt2 = setLeadingZeros(bigInt2, bigInt2.length());
                }
            }
            
            //Set answer
            if(firstWord.equals(isAnswer)) {
                answer = lineScanner.next();
                break;
            }
        }
        
        //Only use the same length when multiplication is used
        // to save time on the other operations.
        if(operation == 'm') {
            bigInt1 = setLeadingZeros(bigInt1, currentLength);
            bigInt2 = setLeadingZeros(bigInt2, currentLength);
        }
        
        input = new BigInt[]{
            new BigInt(bigInt1, sign, radix),
            new BigInt(bigInt2, sign, radix)
        };
    }
    
    private String setLeadingZeros(String integer, int length) {
        if(integer.length() == length) {
            return integer;
        }
        
        String zeros = "";
        
        for (int i = 0; i < length - integer.length(); i++) {
            zeros += "0";
        }
        
        return zeros.concat(integer);
    }
    
    private boolean getSign(String integer) {
        if(integer.startsWith("-")) {
            return false;
        }
        return true;
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
        System.out.println(integer.substring(1));
        return integer.substring(1);
    }
}
