
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

	static double eA = 1e-7;
	static double eR = 1e-4;
	static double Tol1 = 1e-2;
	static double Tol2 = 1e-6;

	// The ground-truth
	public static double trueX(double t) {
		double a = Math.exp(0.8*t)-Math.exp(-0.5*t);
		double b = Math.exp(-0.5*t);
		return (a*4/1.3+b*2);
	}

	// f(x,t) = dx / dt
	public static double f(double t,double x) {
		return 4*Math.exp(0.8*t)-0.5*x;
	}

	// K1 - K4
	public static double K1(double t, double x) {return f(t, x);}
	public static double K2(double t, double x, double h, double k1) {return f(t+h*0.5, x+k1*h*0.5);}
	public static double K3(double t, double x, double h, double k2) {return f(t+h*0.75, x+k2*h*0.75);}
	public static double K4(double t, double x, double h, double k3) {return f(t+h, x+k3*h);}

	// RK3
	public static double RK3(double t, double h, double x) {
		double k1= K1(t, x);
		double k2= K2(t, x, h, k1);
		double k3= K3(t, x, h, k2);
		return x+(2*k1+3*k2+4*k3)*h/9.0; 
	}

	// RK4
	public static double RK4(double t, double h, double x) {
		double k1= K1(t, x);
		double k2= K2(t, x, h, k1);
		double k3= K3(t, x, h, k2);
		double k4= K4(t, x, h, k3);
		return x+(7*k1+6*k2+8*k3+3*k4)*h/24.0;
	}

	// Compute r
	public static double r(double xRK3, double XRK4) {
		double E_i1 = Math.abs(xRK3 - XRK4);
		return E_i1 / (eR * xRK3 + eA);
	}

	// Compute new h
	public static double newh(double h, double t, double x) {
		double k1= K1(t, x);
		double k2= K2(t, x, h, k1);
		double k3= K3(t, x, h, k2);
		double k4= K4(t, x, h, k3);
		double E = 1.0/72*(-5*k1 + 6*k2 + 8*k3 - 9*k4);
		h *= Math.pow(eR / Math.abs(E/x), 1.0/3);
		return h;
	}

	// RK34
	public static double[] RK34_hModify(double t, double h, double x) {
		double xRK3= 0;
		double xRK4= 0;
		double r= 0;
		double step = 0;
		while(step<1) {
			xRK3= RK3(t, h, x);
			xRK4= RK4(t, h, x);
			r= r(xRK3, xRK4);
			while(h+t < 1 && (Math.abs(r-1)>0.01||Math.abs(r-1)<0.000001)) {
				h = newh(h, t, x);
				System.out.println(h);
				xRK3= RK3(t, h, x);
				xRK4= RK4(t, h, x);
				r= r(xRK3, xRK4);
			}
			t=t+h;
			step=step+h;
			x=xRK4;
		}
		double estimatedError= Math.abs(xRK3-xRK4);
		double[] res= {xRK4, estimatedError, h};
		return res;
	}

	// Main Func()
	public static void main(String args[]) {
		double result[] = {0,2.0,0};
		double h = 1;
		for(double t=0; t<=4; t+=1) {
			result = RK34_hModify(t, h, 2);
			System.out.println("t = "+t+":\n  x = "+result[0]+", Error% = "+(result[0] - trueX(t))/trueX(t) +", Estimated Error = "+result[1]+", time step = "+result[2]);
		}
	}
}
