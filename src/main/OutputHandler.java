package main;

import java.lang.StringBuilder;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.File;
import main.IntegerArithmetic;

public class OutputHandler {
    StringBuilder output = new StringBuilder();
    BigInt[] results;
    String filename; // Input file, or null
    
    char[] operation; // Get from main class (if possible directly, so this can be removed)
    int nAdd;
    int nMultiply;
    
    public OutputHandler(BigInt[] output, File file) {
        this.results = output;
        operation = new char[IntegerArithmetic.inputHandler.operation.size()];
        for(int i=0; i<IntegerArithmetic.inputHandler.operation.size(); ++i){
            operation[i] = IntegerArithmetic.inputHandler.operation.get(i);
        }
        if(file != null){
            filename = file.getPath();
        }else{
            filename = null;
        }
    }
    
    public void giveOutput(){
        constructString();
        
        if(filename != null){ // Output to file 
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename, true))) {
                bw.write(output.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{ // Output to console
            System.out.println(output.toString());
        }
    }
    
    private void constructString(){
        assert output.length() == 0;
        for(int i = 0; i < results.length && operation[i] != 0; i++) {
            output.append("# [x] " + IntegerArithmetic.input[i*2] + "\n");
            switch(operation[i]) {
                case 'a':
                    output.append("# [add]");
                    break;
                case 's':
                    output.append("# [subtract]");
                    break;
                case 'm':
                    output.append("# [multiply]");
                    break;
                case 'k':
                    output.append("# [karatsuba]");
                    break;
                default :
                    assert false;
                    break;
            }
            output.append(" with radix " + IntegerArithmetic.input[i*2].rad + "\n");
            output.append("# [y] " + IntegerArithmetic.input[i*2 + 1] + "\n\n");
            output.append("# [result] " + results[i] + "\n\n");

            if(operation[i] == 'm' || operation[i] == 'k'){
                output.append("# Number of additions = " + nAdd);
                output.append("\n\n# Number of multiplications = " + nMultiply);
            }
        }
    }
}
