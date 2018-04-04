
/**
 * HP3.java, ECE4960-HW9
 * Created by Yuan He(yh772) on 2018/03/29
 * Platform: Java 8, Eclipse, MacOS
 * Copyright © 2018 Yuan He. All rights reserved.
 */
public class HP3 {

	static double h = 1;

	//首先需要泰勒展开，泰勒展开后每一项前面*的系数就是a
	//其次K的计算公式已经给出了

	public static double x(double t) {
		double a = Math.exp(0.8*t)-Math.exp(-0.5*t);
		double b = Math.exp(-0.5*t);
		return (a*4/1.3+b*2);
	}

	public static double f(double t,double xt) {return Math.exp(0.8*t)*4-0.5*xt;}

	public static double calculateX(double t, double x) {
		double k[] = new double[4];
		k[0] = f(t,x);
		k[1] = f(t+h/2,x+k[0]*h/2);
		k[2] = f(t+h/2,x+k[1]*h/2);
		k[3] = f(t+h,x+k[2]*h);
		double X = x + (1/6) * (k[0] + 2*k[1] + 2*k[2] + k[3]) * h;
		System.out.println(X);
		return X;
	}
	
	
	public static void main(String args[]) {
		calculateX(0,2);
	}

}
