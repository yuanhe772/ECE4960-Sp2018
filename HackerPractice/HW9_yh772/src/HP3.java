import java.util.Arrays;

/**
 * HP3.java, ECE4960-HW9
 * Created by Yuan He(yh772) on 2018/03/29
 * Platform: Java 8, Eclipse, MacOS
 * Copyright © 2018 Yuan He. All rights reserved.
 * 
 * New note 6, page 37：
 * Calculate RK4 and compare with your previous results.
 */
public class HP3 {

	static double h = 1;

	//First Taylor expansion, after expansion each *parameter is a

	// The groundtruth
	public static double trueX(double t) {
		double a = Math.exp(0.8*t)-Math.exp(-0.5*t);
		double b = Math.exp(-0.5*t);
		return (a*4/1.3+b*2);
	}

	// f(x,t) = dx/dt
	public static double f(double t,double xt) {return Math.exp(0.8*t)*4-0.5*xt;}

	// Runge-Kutta Method
	public static double[] calculateX(double t, double x_i0) {
		double k[] = new double[5];
		k[0] = f(t,x_i0);
		k[1] = f(t+h/2,x_i0+k[0]*h/2);
		k[2] = f(t+h/2,x_i0+k[1]*h/2);
		k[3] = f(t+h,x_i0+k[2]*h);
		double x_i1 = x_i0 + (1.0/6.0) * (k[0] + 2*k[1] + 2*k[2] + k[3]) * h;
		double result[] = {k[0],k[1],k[2],k[3], x_i1};
		return result;
	}
	
	public static void main(String args[]) {
		double result[] = {0,0,0,0,2.0};
		for(double t=0; t<=4; t+=1) {
			// Use the previous X = result[4] for calculating new X
			result = calculateX(t,result[4]);
			System.out.println("t = "+t+":    [k1, k2, k3, k4] = "+Arrays.toString(Arrays.copyOfRange(result, 0, 4))+
					",\t x = "+result[4]+",\t |Error| % = "+Math.abs(result[4] - trueX(t))/trueX(t));
		}
	}
}
