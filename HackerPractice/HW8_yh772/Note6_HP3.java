import java.util.Arrays;

/**
 * Lec_HP3.java, ECE4960-HW7
 * Created by Yuan He(yh772) on 2018/03/23
 * Platform: Java 8, Eclipse, MacOS
 * Copyright © 2018 Yuan He. All rights reserved.
 * 
 * Calculating π by Monte Carlo
 */
public class Note6_HP3 {

	// Calculate pi
	public static double pi(int N) {
		double x, y;
		int count = 0;
		for(int i=0; i<Math.pow(10, N); i++) {
			x = Math.random();
			y = Math.random();
			if((x*x + y*y)<1) {count ++;}
		}
		//		System.out.println("When n = " + Math.pow(10, N) + ", pi = "+4*count/Math.pow(10, N));
		return 4*count/Math.pow(10, N);
	}

	public static double[] error(double[] pi) {
		double truePi = 3.14159265359;
		double error[] = new double[pi.length];
		for(int i=0; i<pi.length; i++) {
			error[i] = Math.abs(pi[i] - truePi);
		}
		return error;
	}

	public static void main(String args[]) {
		int N = 8;
		double pi[] = new double[N];

		// Calculate the pi with more random points
		for(int i=1; i<=N; i++) {
			pi[i-1] = pi(i);
		}

		// Plot X = B, Y = calculatedPi - truePi
		System.out.println(Arrays.toString(pi));
		System.out.println(Arrays.toString(error(pi)));
	}
}
