
/**
 * HP4.java, ECE4960-HW6
 * Created by Yuan He(yh772) on 2018/03/08
 * Platform: Java 8, Eclipse, MacOS
 * Copyright © 2018 Yuan He. All rights reserved.
 * 
 * HP Purpose:
 * Use the Newton method to solve the following nonlinear equation:
 */
public class HP4 {

	public static double f(double x) {return Math.exp(50*x);}

	public static double f_fD(double x, double diff) {
		if(x == 1) diff = 1;
		return (f(x)-1)/((-50)*Math.exp(49*x));
		}

	public static double itNorm(double prev, double curr) {
		return Math.abs(prev-curr)/prev;
	}

	public static void newton(double x) {
		System.out.println("When x(" + (int)x + ") = " + x);
		int diff = 0;
		if(x == 1) {diff = 1;}
		double fx = f(x) - diff;
		double ffd = f_fD(x,diff);
		while(Math.abs(fx)>Math.pow(10, -7)) {
			System.out.println("x = "+x+", delta x = "+ffd+", f(x) = "+fx);
			x= x+ffd;
			fx= Math.exp(50*x)-1;
			ffd= fx/(-50*Math.exp(50*x));
		}
	}

	public static void main(String args[]) {
		newton(1);
		newton(10);
	}
}
