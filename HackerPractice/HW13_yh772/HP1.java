import java.util.Arrays;

/**
 * HP1.java, ECE4960-HW13
 * Created by Yuan He(yh772) on 2018/05/01
 * Platform: Java 8, Eclipse, MacOS
 * Copyright © 2018 Yuan He. All rights reserved.
 * 
 * Note 7, Page 12;
 * Using RoofTop estimation to estimate sin(x) over (0, pi)
 */
public class HP1 {

	/* Class Variants:*/
	// x(k+1) - x(k-1):
	static double delta = Math.PI/8;
	// The span that needs simulation
	static double span = Math.PI;
	// The x(k-1) for each of the 8 intervals
	static double[] xk_1s = {0, delta, 2*delta, 3*delta, 4*delta, 5*delta, 6*delta, 7*delta};
	// a[] = a1~an
	static double[] a = a();
	static int flag = 0;

	/* Class Methods:*/
	// ground truth f(x) = sin(x)
	public static double f(double x) {return Math.sin(x);}

	// roof function v
	public static double v(double x, double xk_1) {
		double xk = xk_1 + delta;
		double xkPlus1 = xk_1 + delta*2;
		double v = 0;
		if(x < xk_1 || x > xkPlus1) return 0;
		else if(x>xk && x<xkPlus1) v = (xkPlus1 - x) / (xkPlus1 - xk);
		else v = (x - xk_1) / (xk - xk_1);
		return v;
	}

	// calculate a1-ak
	public static double[] a() {
		double[] a = new double[7];
		double x = 0;
		for(int i=0; i<a.length; i++) {
			x = (i+1)*delta;
			a[i] = f(x)/v(x, x - delta);
		}
		return a;
	}

	public static double approximation(double x) {
		flag += 1;
		// Check x is within what intervals
		int interval = (int) (x*8/span);

		if(interval == 0) {
			System.out.println("The "+flag+"th point: Ground truth f(x) = "+f(x)+",\tApproximated f(x) = "+a[interval] * v(x, 0));
			return a[interval] * v(x, 0);
		}
		else if(interval == 7){
			System.out.println("The "+flag+"th point: Ground truth f(x) = "+f(x)+",\t Approximated f(x) = "+a[interval-1] * v(x, 6*delta));
			return a[interval-1] * v(x, 6*delta);
		}
		else {
			double term1 = v(x, xk_1s[interval]) * a[interval];
			double term2 = v(x, xk_1s[interval-1]) * a[interval-1];
			System.out.println("The "+flag+"th point: Ground truth f(x) = "+f(x)+",\t Approximated f(x) = "+(term1+term2));
			return (term1 + term2);
		}	
	}

	public static void main(String args[]) {

		// Calculate the approximated 100 points
		double[] approx = new double[101];
		double newInterval = span/100;
		int i = 0;
		for(double x = 0; x<span; x += newInterval) {
			approx[i] = approximation(x);
			i += 1;
		}

		// Calculate the approximated curve's integral over (0, pi)
		double approxIntegral = 0;
		approxIntegral += approx[1]*newInterval/2;
		approxIntegral += approx[100]*newInterval/2;
		for(i = 1; i<99; i++) {
			approxIntegral += (approx[i] + approx[i+1])*newInterval/2;
		}
		System.out.println("Approximated integral = "+
				approxIntegral+", where true integral = 2, the percentage error in the estimated area = %"+
				(2-approxIntegral)/2*100);

	}
}
