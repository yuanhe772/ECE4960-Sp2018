/** Last Update: 02/10/2018; Author: Yuan He (yh772); Platform: Eclipse, Java8 */

public class HP1 {
	private static double f1(double x ){
		return Math.pow(x, 2);
	}

	private static double f2(double x){
		return (Math.pow(x,2) + Math.pow(10, 8));
	}

	public static void main(String args[]){
		System.out.println("f1(x):\n");
		System.out.println("f1-Eq(1):");

		for(double h=0.1; h>= Math.pow(10, -18); h/=10) {
			System.out.println( Math.abs(2 - ((f1(1+h) - f1(1)) / h)) / 2  );
		}

		System.out.println("\nf1-Eq(2):");

		for(double h=0.1; h>= Math.pow(10, -18); h/=10) {
			System.out.println( Math.abs(2 - ((f1(1+h) - f1(1-h)) / (2*h))) / 2  );
		}

		System.out.println("\n\nf2(x):\n");
		System.out.println("f2-Eq(1):");

		for(double h=0.1; h>= Math.pow(10, -18); h/=10) {
			System.out.println( Math.abs(2 - ((f2(1+h) - f2(1)) / h) ) / 2 );
		}

		System.out.println("\nf2-Eq(2):");

		for(double h=0.1; h>= Math.pow(10, -18); h/=10) {
			System.out.println( Math.abs(2 - ((f2(1+h) - f2(1-h)) / (2*h))) / 2  );
		}
	}
}
