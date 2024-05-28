import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;




public class Driver {
    public static void main(String [] args) {
        Polynomial p = new Polynomial();
        System.out.println(p.evaluate(3));
        double [] c1 = {6,5};
        int [] x1 = {0, 3};
        Polynomial p1 = new Polynomial(c1, x1);
        double [] c2 = {-2,-9};
        int [] x2 = {1,4};
        Polynomial p2 = new Polynomial(c2, x2);
        Polynomial s = p1.add(p2);
        System.out.println("s(0.1) = " + s.evaluate(0.1)); 
        if(s.hasRoot(1))
            System.out.println("1 is a root of s");     
        else
            System.out.println("1 is not a root of s");

        Polynomial result = p1.multiply(p2);
        System.out.println("s(0.1) = " + result.evaluate(0.1)); 
        if(result.hasRoot(1))
            System.out.println("1 is a root of result");     
        else
            System.out.println("1 is not a root of result"); 

        System.out.println("result(0) = " + result.evaluate(0)); 
        if(result.hasRoot(0))
            System.out.println("0 is a root of result");     
        else
            System.out.println("0 is not a root of result"); 



        try {
            
            File tempFile = File.createTempFile("tempfile", ".txt");
            String polynomial = "1+6x+5x2-2x3-9x4";
            Files.write(Paths.get(tempFile.getPath()), polynomial.getBytes());
            
            
           
            Polynomial pold = new Polynomial(tempFile);

            for (double coeff : pold.coefficients) {
                System.out.println(coeff);
            }
            for (int exp : pold.exp) {
                System.out.println(exp);
            }
    
            
            
            double[] expectedCoefficients = {1.0, 6.0, 5.0, -2.0, -9.0};
            int[] expectedExponents = {0, 1, 2, 3, 4};


            // Delete temporary file
            tempFile.delete();
        } catch (IOException e) {
            System.out.println("IOException occurred: " + e.getMessage());

        }

        double[] coefficients = {6, 5, -2, -9};
        int[] exponents = {1, 2, 3, 4};
        Polynomial pnew = new Polynomial(coefficients, exponents);

       
        String fileName = "test_polynomial.txt";
        pnew.saveToFile(fileName);

       
        try {
            String content = Files.readString(Paths.get(fileName));
            String expectedContent = "6x+5x2-2x3-9x4";

            
            System.out.println(content);
            
           
            Files.deleteIfExists(Paths.get(fileName));
        } catch (IOException e) {
            System.out.println("IOException occurred: " + e.getMessage());

        }
        
    }


}