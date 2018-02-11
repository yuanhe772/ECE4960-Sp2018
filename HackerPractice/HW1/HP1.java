/** Last Update: 02/10/2018; Author: Yuan He (yh772); Platform: Eclipse, Java8 */

public class HP1 {

	private static double[] f1(double a, double b, double c) {
		double x1 = ( (-1) * b + Math.pow(Math.pow(b, 2) - 4*a*c, 0.5) )/(2*a);
		double x2 = ( (-1) * b - Math.pow(Math.pow(b, 2) - 4*a*c, 0.5) )/(2*a);
		double root[] = new double[2];
		root[0] = x1;
		root[1] = x2;
		return root;
				// In java, if you're trying to return two values, just 
				//put the primitive type data into array, return
				//or put an object's object-data into a set, return
	}
	
	private static double[] f2(double a, double b, double c) {
		double x1 = ((-2)*c) / (  b + Math.pow(Math.pow(b, 2) - 4*a*c, 0.5) );
		double x2 = ((-2)*c) / (  b - Math.pow(Math.pow(b, 2) - 4*a*c, 0.5) );
		double root[] = new double[2];
		root[0] = x1;
		root[1] = x2;
		return root;
	}
	
	public static void main(String args[]) {
		
		double r1[] = f1( Math.pow(10, -20), Math.pow(10, 3), Math.pow(10, 3) );
		System.out.println("Roots of f1:");
		for(int i=0; i<r1.length; i++) {
			System.out.println(r1[i]);
		}
		
		double r2[] = f2( Math.pow(10, -20), Math.pow(10, 3), Math.pow(10, 3) );
		System.out.println("\n"+"Roots of f2:");
		for(int i=0; i<r2.length; i++) {
			System.out.println(r2[i]);
		}
	}
}
