
/**
 * HP2.java, ECE4960-HW9
 * Created by Yuan He(yh772) on 2018/03/29
 * Platform: Java 8, Eclipse, MacOS
 * Copyright © 2018 Yuan He. All rights reserved.
 */
public class HP2 {
	double h = 1;
	
	public static double x(double t) {
		double a = Math.exp(0.8*t)-Math.exp(-0.5*t);
		double b = Math.exp(-0.5*t);
		return (a*4/1.3+b*2);
	}
	
	public static double dx(double t,double xt) {
		return Math.exp(0.8*t)*4-0.5*xt;
	}
	
	public static double forwardEuler(double t, double x) {
		return 0;
	}
	
	public static double huen(double t, double x) {
//		double xx = x+h*(f(t,x)+f(t1,x1))/2;
		return 0;
	}
	
	
	public static void main(String args[]) {
		
		
		
	}
	
	
}
