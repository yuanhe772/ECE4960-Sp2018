
/**
 * HP1.java, ECE4960-HW7
 * Created by Yuan He(yh772) on 2018/03/20
 * Platform: Java 8, Eclipse, MacOS
 * Copyright © 2018 Yuan He. All rights reserved.
 * 
 * Note 5, p25: 
 * Newton’s method will converge only in the
 * proximity of the solution: basin of attraction.
 */
public class HP1 {

	public static double fx(double x) {return Math.exp(50*x)-1;}

	public static double dx(double x) {return fx(x)/(-50*Math.exp(50*x));}

	public static void newton(double x) {
		double t = 1;
		double norm1 = 0;
		double norm2 = 0;
		while(Math.abs(fx(x))>Math.pow(10, -7)) {
			t = 1;
			norm1 = fx(x+t*dx(x));
			norm2 = fx(x+(t/2)*dx(x));
			// If the middle point's value is smaller than the delta x value
			// Then implement a binary search
			while (norm2<norm1) {
				norm1 = norm2;
				t/=2;
				norm2 = fx(x+(t/2)*dx(x));
				System.out.println(t);
			}
			x = x+(t)*dx(x);
			System.out.println("x = "+x+"\tt = "+t+"\tdx(x) = "+dx(x)+"\tfx(x) ="+fx(x));
		}
	}

	public static void main(String args[]) {
		newton(10);
	}
}
