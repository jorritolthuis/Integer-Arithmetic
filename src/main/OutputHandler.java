package main;

import java.lang.StringBuilder;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

public class OutputHandler {
    StringBuilder output;
    BigInt result;
    String filename; // Input file, or null
    BigInt input[];
    
    char operation; // Get from main class (if possible directly, so this can be removed)
    int nAdd;
    int nMultiply;
    
    public OutputHandler(BigInt output, String filename) {
        this.result = output;
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
            System.out.println(output);
        }
    }
    
    private void constructString(){
        assert output.length() == 0;
        
        output.append("# [x] " + input[0] + "\n");
        switch(operation){
            case 'a':
                output.append("[add]");
                break;
            case 's':
                output.append("[subtract]");
                break;
            case 'm':
                output.append("multiply");
                break;
            case 'k':
                output.append("karatsuba");
                break;
            default :
                assert false;
                break;
        }
        output.append("# [add] with radix " + input[0].rad + "\n");
        output.append("# [y] " + input[1] + "\n\n");
        output.append("# [result] " + result);
        
        if(operation == 'm' || operation == 'k'){
            output.append("\n\n# Number of additions = " + nAdd);
            output.append("\n\n# Number of multiplications = " + nMultiply);
        }
    }
}
