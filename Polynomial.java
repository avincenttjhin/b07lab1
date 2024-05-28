import java.util.Arrays;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;


public class Polynomial {
	//fields
	double [] coefficients;
    int [] exp;
	
    //methods
	//constructors
	public Polynomial() {
        this.coefficients = new double[]{0};
        this.exp = new int[]{0};
	}
	
    public Polynomial(double [] coefficients, int [] exp) {
        this.coefficients = coefficients;
        this.exp = exp;
	}



    public Polynomial(File file) {
        try(BufferedReader input = new BufferedReader(new FileReader(file))) {
        
            String line = input.readLine();

                String[] coeffs = line.split("(?=[+-])"); // Split by + or -
                coefficients = new double[coeffs.length];
                exp = new int[coeffs.length];
                int counter = 0;
    
                for (int i = 0; i < coeffs.length; i++) {

                    double coefficient;
                    int expo;

                    String coeff = coeffs[i];
                    String[] each = coeff.split("x");

                    if (each.length == 1 && counter == 0) {
                        expo = 0;
                        counter = 1;
                    } else if (each.length == 1 && counter == 1) {
                        expo = 1;
                    } else { 
                        expo = Integer.parseInt(each[1]);
                    }
    
                    if (each[0].isEmpty()) {
                        coefficient = 1.0;
                    } else if (each[0].equals("-")) {
                        coefficient = -Double.parseDouble(each[0]);
                    } else {
                        coefficient = Double.parseDouble(each[0]);
                    }
    
                    coefficients[i] = coefficient;
                    exp[i] = expo;
                }
        } catch (IOException e) {
            e.printStackTrace();
        }
                
    }

	
    public Polynomial add(Polynomial poly) {

        int maxlength = this.coefficients.length + poly.coefficients.length;

        double[] resultcoefficients = new double[maxlength];
        int[] resultexp = new int[maxlength];

        int x = 0, y = 0, z = 0;

        while (x < this.coefficients.length && y < poly.coefficients.length) {
            if (this.exp[x] == poly.exp[y]) {
                resultcoefficients[z] = this.coefficients[x] + poly.coefficients[y];
                resultexp[z] = this.exp[x];
                x++;
                y++;
            } else if (this.exp[x] < poly.exp[y]) {
                resultcoefficients[z] = this.coefficients[x];
                resultexp[z] = this.exp[x];
                x++;
            } else {
                resultcoefficients[z] = poly.coefficients[y];
                resultexp[z] = poly.exp[y];
                y++;
            }
            z++;
        }

        while (x < this.coefficients.length) {
            resultcoefficients[z] = this.coefficients[x];
            resultexp[z] = this.exp[x];
            x++;
            z++;
        }

        while (y < poly.coefficients.length) {
            resultcoefficients[z] = poly.coefficients[y];
            resultexp[z] = poly.exp[y];
            y++;
            z++;
        }

        return new Polynomial(resultcoefficients, resultexp);

    }


    public double evaluate(double x) {
        double result = 0;

        for (int i = 0; i < this.coefficients.length; i++) {
            result += this.coefficients[i] * Math.pow(x, this.exp[i]);
        }

        return result;
    }

    public Boolean hasRoot(double x) {

        return evaluate(x) == 0;

    }
    

    public Polynomial multiply(Polynomial poly) {
        int maxlength = this.exp.length * poly.exp.length;

        double[] resultcoefficients = new double[maxlength];
        int[] resultexp = new int[maxlength];
        int index = 0;

        for (int i = 0; i < this.coefficients.length; i++) {

            for (int j = 0; j < poly.coefficients.length; j++) {

                double productcoeff = this.coefficients[i] * poly.coefficients[j];
                int sumexp = this.exp[i] + poly.exp[j];
                boolean exist = false;

                for (int k = 0; k < index; k++) {
                    if (resultexp[k] == sumexp) {
                        resultcoefficients[k] += productcoeff;
                        exist = true;
                        break;
                    }
                }

                if (!exist) {
                    resultcoefficients[index] = productcoeff;
                    resultexp[index] = sumexp;
                    index++;
                }
            }
        }

        return new Polynomial(resultcoefficients, resultexp);

    }


    public void saveToFile(String fname) {
       try (FileWriter writer = new FileWriter(fname)) {
      
            if (coefficients.length != 0) {
                    if (coefficients[0] != 1 || exp[0] == 0) {
                        if (coefficients[0] % 1 == 0) {
                            int intCoeff = (int) coefficients[0];
                            writer.write(Integer.toString(intCoeff));
                        } else {
                            writer.write(Double.toString(coefficients[0]));
                        }
                    }
    
                    if (exp[0] != 0) {
                        writer.write("x");
                        if (exp[0] != 1) {
                            writer.write(Integer.toString(exp[0]));
                        }
                    }
                }
                    

            for (int i = 1; i < coefficients.length; i++) {
                if (coefficients[i] > 0) {
                    writer.write("+");
                } else {
                    writer.write("-");
                }

                double coeff = Math.abs(coefficients[i]);
                if (coeff != 1 || exp[i] == 0) {
                    if (coeff % 1 == 0) {
                        int intCoeff = (int) coeff;
                        writer.write(Integer.toString(intCoeff));
                    } else {
                        writer.write(Double.toString(coeff));
                    }
                }

                if (exp[i] != 0) {
                    writer.write("x");
                    if (exp[i] != 1) {
                        writer.write(Integer.toString(exp[i]));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
