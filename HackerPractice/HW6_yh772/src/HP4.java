
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

	public static double fD(double x, int it_num) {
		int e = 50;
		for(int i=50; i>50-it_num; i--) {
			if(e!=0)
				e *= i;
		}
		//System.out.println(e*Math.exp((50-it_num)*x));
		return e*Math.exp((49-it_num)*x);
	}

	public static double itNorm(double prev, double curr) {
		return Math.abs(prev-curr)/prev;
	}

	public static void main(String args[]) {

		double prevX = 0;
		double currX = 1;
		int i = 0;

		while(itNorm(prevX,currX)>Math.pow(10, -7)) {
			prevX = currX;
			currX = prevX - f(prevX)/fD(prevX, i);
			i+=1;
//			System.out.println(prevX);
//			System.out.println(currX);
			System.out.println(itNorm(prevX, currX));
		}

	}




}
