package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

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
    ArrayList<Character> operation;
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
        ArrayList<BigInt> bigInt1 = new ArrayList<>();
        ArrayList<BigInt> bigInt2 = new ArrayList<>();
        ArrayList<String> answer = new ArrayList<>();
        ArrayList<Character> operation = new ArrayList<>();
        
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
                int rad = lineScanner.nextInt();
                bigInt1.add(new BigInt("", true, rad)); // Create two new BigInts
                bigInt2.add(new BigInt("", true, rad));
                answer.add("");
                operation.add('@'); // Placeholder
            }
            
            //If operation then set it
            //The second index (1) is the char that corresponds with the
            // operation.
            for (String op : operations) {
                if (firstWord.equals(op)) {
                    operation.set(operation.size()-1, op.charAt(1));
                    break;
                }
            }
            
            //Set x
            if(firstWord.equals(isX)) {
                String xval = lineScanner.next();
                bigInt1.get(bigInt1.size()-1).val = removeSign(xval);
                bigInt1.get(bigInt1.size()-1).isPositive = getSign(xval);
            }
            
            //Set y
            if(firstWord.equals(isY)) {
                String yval = lineScanner.next();
                bigInt2.get(bigInt2.size()-1).val = removeSign(yval);
                bigInt2.get(bigInt2.size()-1).isPositive = getSign(yval);
            }
            
            //Set answer
            if(firstWord.equals(isAnswer)) {
                answer.set(answer.size()-1, lineScanner.next());
            }
            
            //If end is reached of the System.in
            if(firstWord.equals(consoleEndWord)) {
                break;
            }
        }
        
        //Only use the same length when multiplication is used
        // to save time on the other operations.
        //NOT NECESSARY!!!
        
        for(int i=0; i<bigInt1.size(); ++i){
            if(bigInt1.get(i).val.length() < bigInt2.get(i).val.length()){
                setLeadingZeros(bigInt1.get(i).val, bigInt2.get(i).val.length());
            }
            
            if(bigInt1.get(i).val.length() > bigInt2.get(i).val.length()){
                setLeadingZeros(bigInt2.get(i).val, bigInt1.get(i).val.length());
            }
        }
        
        this.operation = operation;
        
        input = new BigInt[2*bigInt1.size()];
        for (int i = 0; i < bigInt1.size(); i++) {
            input[i*2] = bigInt1.get(i);
            input[i*2 + 1] = bigInt2.get(i);
        }
    }
    
//    @Deprecated
    private String setLeadingZeros(String integer, int length) {
        if(integer.length() == length) {
            return integer;
        }
        
        String zeros = "";
        
        for (int i = 0; i < length - integer.length(); i++) {
            zeros += "0";
        }
        
        integer = zeros.concat(integer);
        return integer;
    }
    
    private boolean getSign(String integer) {
        return !integer.startsWith("-");
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
            //System.out.println(integer.substring(1));
            return integer.substring(1);
        }
        //System.out.println(integer);
        return integer;
    }
}
