package src;
import java.io.IOException;

public class A {
    public static void main(String[] args) {
        C obj = new C();
        String inputFilename = "input.csv";
        String outputFilename = "output.pgn";
        
        long startTime = System.currentTimeMillis(); // Record start time
        
        try {
            obj.convertCsvToPgn(inputFilename, outputFilename);
            System.out.println("Conversion successful!");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        
        long endTime = System.currentTimeMillis(); // Record end time
        long executionTime = endTime - startTime; // Calculate execution time
        System.out.println("Execution time: " + executionTime + "ms");
    }
}
