

public class Polynomial {
	//fields
	double [] coefficients;
	
    //methods
	//constructors
	public Polynomial() {
        this.coefficients = new double[]{0};
	}
	
    public Polynomial(double [] coefficients) {
        this.coefficients = coefficients;
	}
	
    public Polynomial add(Polynomial poly) {
        int length;

        if (poly.coefficients.length > this.coefficients.length) {
            length = poly.coefficients.length;
        } else {
            length = this.coefficients.length;
        }

        double[] resultcoefficients = new double[length];

        for (int i = 0; i < length; i++) {

            double thiscoefficient; 
            double polycoefficient; 

            if (i < this.coefficients.length) {
                thiscoefficient = this.coefficients[i];
            } else {
                thiscoefficient = 0;
            }

            if (i < poly.coefficients.length) {
                polycoefficient = poly.coefficients[i];
            } else {
                polycoefficient = 0;
            }

            resultcoefficients[i] = thiscoefficient + polycoefficient;
        }

        return new Polynomial(resultcoefficients);

    }

    public double evaluate(double x) {
        double result = 0;

        for (int i = 0; i < this.coefficients.length; i++) {
            result += coefficients[i] * Math.pow(x, i);
        }

        return result;
    }

    public Boolean hasRoot(double x) {

        return evaluate(x) == 0;

    }


}
