
/**
 * HP1.java, ECE4960-HW9
 * Created by Yuan He(yh772) on 2018/03/27
 * Platform: Java 8, Eclipse, MacOS
 * Copyright © 2018 Yuan He. All rights reserved.
 * 
 * New Note 6, P15:
 * Compare the performance of:
 * Backward and Trapezoid Euler estimation
 */
public class HP1 {

	// f(t) = e ^ (-t)
	private static double f(double t) {
		return Math.exp(-t);
	}
	
	// Backward Euler and Trapezoid
		public static void Euler(double dt) {
			double currBack = 1;
			double preBack = 1;
			double currTrapezoid = 1;
			double preTrapezoid = 1;
			System.out.println("\n\nWhen delta t = "+dt+":");
			for(double t=0; t<=20; t+=dt) {
				currBack = (1/(1+dt)) * preBack;
				currTrapezoid = ((2-dt)/(2+dt)) * preTrapezoid;
				System.out.println("\tt = " + t+ "\t  Backward Euler Error = "+ Math.abs(currBack - f(t))+
						"\tTrapezoid Euler Error= "+ Math.abs(currTrapezoid - f(t)));
				preBack = currBack;
				preTrapezoid = currTrapezoid;
			}
		}

	// Marching with different step size
	public static void main(String args[]) {	
		Euler(0.5);
		Euler(0.1);
		System.out.println("\n\nIt could be concluded that:"
				+ "\n - Trapezoid has smaller error;"
				+ "\n - Smaller error comes with smaller delta t.");
	}
}
