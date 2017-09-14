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
    final String commentStart = "#";
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
    final String consoleEndWord = "end";
    final int maxInputs = 10;
    /**
     * every even index is the [x]
     * and every uneven index is the [y]
     */
    private BigInt input[];
    /**
     * a - add
     * s - subtract
     * m - multiply
     * k - karatsuba
     */
    char[] operation;
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
        int inputCounter = -1;
        String[] bigInt1 = new String[maxInputs];
        String[] bigInt2 = new String[maxInputs];
        int[] maxLength = new int[maxInputs];
        boolean[] signs = new boolean[maxInputs*2];
        int[] radix = new int[10];
        String[] answer = new String[maxInputs];
        operation = new char[maxInputs];
        
        while(scanner.hasNextLine()) {
            line = scanner.nextLine();
            //Filter comments
            if(line.contains(commentStart)) {
                continue;
            }
            
            String firstWord;
            //Filter empty lines
            Scanner lineScanner = new Scanner(line);
            if(lineScanner.hasNext()) {
                firstWord = lineScanner.next();
            } else {
                continue;
            }
            
            //If radix then set it
            if(firstWord.equals(isRadix)) {
                inputCounter++;
                if(inputCounter >= maxInputs) {
                    System.err.println("Too many inputs at once");
                    return;
                }
                radix[inputCounter] = lineScanner.nextInt();
            }
            
            //If operation then set it
            //The second index (1) is the char that corresponds with the
            // operation.
            for (String op : operations) {
                if (firstWord.equals(op)) {
                    operation[inputCounter] = op.charAt(1);
                }
            }
            
            //Set x
            if(firstWord.equals(isX)) {
                String integer = lineScanner.next();
                signs[inputCounter*2] = getSign(integer);
                bigInt1[inputCounter] = removeSign(integer);
                
                if(maxLength[inputCounter] < bigInt1[inputCounter].length()) {
                    maxLength[inputCounter] = bigInt1[inputCounter].length();
                }
            }
            
            //Set y
            if(firstWord.equals(isY)) {
                String integer = lineScanner.next();
                signs[inputCounter*2 + 1] = getSign(integer);
                bigInt2[inputCounter] = removeSign(integer);
                
                //Ensure both number are the same length
                if(maxLength[inputCounter] < bigInt2[inputCounter].length()) {
                    maxLength[inputCounter] = bigInt2[inputCounter].length();
                }
            }
            
            //Set answer
            if(firstWord.equals(isAnswer)) {
                answer[inputCounter] = lineScanner.next();
            }
            
            //If end is reached of the System.in
            if(firstWord.equals(consoleEndWord)) {
                break;
            }
        }
        
        //Only use the same length when multiplication is used
        // to save time on the other operations.
        for (int i = 0; i < maxInputs; i++) {
            for (int j = 0; j < operation.length && operation[i] != 0; j++) {
                if(operation[j] == 'm') {
                    bigInt1[i] = setLeadingZeros(bigInt1[i], maxLength[i]);
                    bigInt2[i] = setLeadingZeros(bigInt2[i], maxLength[i]);
                }
            }
        }
        
        input = new BigInt[2*maxInputs];
        for (int i = 0; i < input.length; i += 2) {
            input[i] = new BigInt(bigInt1[i/2], signs[i], radix[i/2]);
            input[i + 1] = new BigInt(bigInt2[i/2], signs[i + 1], radix[i/2]); 
        }
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
        if(integer.startsWith("-")) {
            System.out.println(integer.substring(1));
            return integer.substring(1);
        }
        System.out.println(integer);
        return integer;
    }
}
