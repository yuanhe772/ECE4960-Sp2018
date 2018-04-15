import java.util.Arrays;

/**
 * HP1.java, ECE4960-HW10
 * Created by Yuan He(yh772) on 2018/04/10
 * Platform: Java 8, Eclipse, MacOS
 * Copyright © 2018 Yuan He. All rights reserved.
 * 
 * Note 6, Page 46:
 * Calculate the estimated error in RK34 and observe its relation to |εx|
 */
public class HP1 {

	static double h = 1;
	static double eA = 1e-7;
	static double eR = 1e-4;
	static double Tol1 = 1e-2;
	static double Tol2 = 1e-6;
	static int m = 1;

	//First Taylor expansion, after expansion each *parameter is a

	// The groundtruth
	public static double trueX(double t) {
		double a = Math.exp(0.8*t)-Math.exp(-0.5*t);
		double b = Math.exp(-0.5*t);
		return (a*4/1.3+b*2);
	}

	// f(x,t) = dx/dt
	public static double f(double t,double xt) {
		return Math.exp(0.8*t)*4-0.5*xt;
	}

	// RK3
	public static double RK3(double x, double k1, double k2, double k3) {
		return x + (1.0/9.0) * (2.0*k1 + 3.0*k2 + 4.0*k3) * h;
	}

	// RK4
	public static double RK4(double x, double k1, double k2, double k3, double k4) {
		return x + (1.0/24.0) * (7*k1 + 6*k2 + 8*k3 + 3*k4) * h;
	}

	// Compute r
	public static double r(double xRK3, double XRK4) {
		double relativeErr = Math.abs(xRK3 - XRK4)/xRK3;
		return relativeErr / (eR * xRK3 + eA);
	}

	//	Compute new h
	public static double newh(double x1, double[] k) {
		double E = 1.0/72*(-5*k[0] + 6*k[1] + 8*k[2] - 9*k[3]);
		h *= Math.pow(eR / Math.abs(E/x1), 1.0/3);
		return h;
	}

	// RK34
	public static double[] RK34(double t, double x_i0) {
		double k[] = new double[5];
		k[0] = f(t,x_i0);
		k[1] = f(t+h/2,x_i0+k[0]*h/2.0);
		k[2] = f(t+3*h/4.0,x_i0+k[1]*3*h/4.0);
		double x_i1_RK3 = RK3(x_i0, k[0], k[1], k[2]);
		k[3] = f(t+h, x_i1_RK3);
		double x_i1_RK4 = RK4(x_i0, k[0], k[1], k[2], k[3]);
		double result[] = {k[0],k[1],k[2],k[3], x_i1_RK4};
		return result;
	}

	public static double[] RK34_hModify(double t, double x_i0) {
		double resRK34[] = RK34(t, x_i0);
		double k[] = {resRK34[0], resRK34[1], resRK34[2], resRK34[3]};
		double xRK3 = RK3(x_i0, k[0], k[1], k[2]);
		double xRK4 = RK4(x_i0, k[0], k[1], k[2], k[3]);
		double r = r(xRK3, xRK4);

		// Update h
		while(r>Tol1 || r<Tol2) {
			h = newh(xRK3, k);
			xRK3 = RK3(x_i0, k[0], k[1], k[2]);
			xRK4 = RK4(x_i0, k[0], k[1], k[2], k[3]);
			r= r(xRK3, xRK4);
		}

		double estimateError= Math.abs(xRK3-xRK4);
		double[] res= {xRK4, estimateError, xRK3 - xRK4};
		return res;
	}

	public static void main(String args[]) {
		double result[] = {0,2.0,0};
		for(double t=0; t<=4; t+=1) {
			System.out.println("t = "+t+":\n  x = "+result[1]+", Error% = "+(result[1] - trueX(t))/trueX(t) +", Estimated Error = "+result[2]+", time step = "+h);
			result = RK34_hModify(t,result[1]);
		}
	}
}
