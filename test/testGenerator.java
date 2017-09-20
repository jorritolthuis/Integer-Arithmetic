
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Generates test cases for Integer Arithmetic in C:\tests\.
 * @author Jorrit Olthuis
 */

public class testGenerator {
    // ---- INPUT ----
    static final int NO_OF_CASES =     10; // Maximum number of cases per file
    static final int NO_OF_TESTS =     3; // Number of files with same number of cases
    // ---------------    
    
    public static void main(String[] args) throws IOException {
        try{
            Files.createDirectory(Paths.get("C:\\tests\\"));
        }catch(IOException e){
            throw new IOException("Test folder could not be created");
        }
        
        for(int k = 1; k<=NO_OF_CASES; ++k){ // k*add/sub/mul/kar
            for(int j = 1; j<=NO_OF_TESTS; ++j){ // repeat that j times
                StringBuilder[] output = new StringBuilder[k];
                
                for(int i=0; i<k; ++i){
                    output[i] = new StringBuilder("");
                    
                    // RADIX
                    int radix = rand(14)+2;
                    output[i].append("[radix] " + radix + System.getProperty("line.separator"));

                    // OPERATION
                    switch(rand(3)){
                        case 0:
                            output[i].append("[add]" + System.getProperty("line.separator"));
                            break;
                        case 1:
                            output[i].append("[subtract]" + System.getProperty("line.separator"));
                            break;
                        case 2:
                            output[i].append("[multiply]" + System.getProperty("line.separator"));
                            break;
                        case 3:
                            output[i].append("[karatsuba]" + System.getProperty("line.separator"));
                            break;
                    }

                    // X
                    if(rand()){
                        output[i].append("[x] ");
                    }else{
                        output[i].append("[x] -");
                    }
                    output[i].append(generateString(radix));
                    output[i].append(System.getProperty("line.separator"));

                    // Y
                    if(rand()){
                        output[i].append("[y] ");
                    }else{
                        output[i].append("[y] -");
                    }
                    output[i].append(generateString(radix));                    
                    output[i].append(System.getProperty("line.separator"));
                }

                Path path = Paths.get("C:\\tests\\test" + k + "_" + j + ".txt");
                System.out.println("C:\\tests\\test" + k + "_" + j + ".txt");
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(path.toString(), true))) {
                    for(int i=0; i<k; ++i){
                        bw.append(output[i].toString());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    static String generateString(int radix){
        int max = rand(250); // Generate integers of at most 250 characters
        String generated = "";
        
        for(int j=0; j<max; ++j){
            generated += getCharacter(radix);
        }
        return generated;
    }
    
    static char getCharacter(int radix){
        int charcode = rand(radix);
        switch(charcode){
            case 0:
                return '0';
            case 1:
                return '1';
            case 2:
                return '2';
            case 3:
                return '3';
            case 4:
                return '4';
            case 5:
                return '5';
            case 6:
                return '6';
            case 7:
                return '7';
            case 8:
                return '8';
            case 9:
                return '9';
            case 10:
                return 'A';
            case 11:
                return 'B';
            case 12:
                return 'C';
            case 13:
                return 'D';
            case 14:
                return 'E';
            case 15:
                return 'F';
            default:
                return '0';
        }
    }
    
    static int rand(int max){
        return (int)(Math.random() * max);
    }
    
    static boolean rand(){
        return Math.random() < 0.5;
    }
}
