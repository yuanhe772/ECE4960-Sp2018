
/**
 * HP3.java, ECE4960-HW6
 * Created by Yuan He(yh772) on 2018/03/06
 * Platform: Java 8, Eclipse, MacOS
 * Copyright © 2018 Yuan He. All rights reserved.
 * 
 * Hacker Practice Purpose:
 * Use the bisection method to solve the following nonlinear equation:
 * f(x) = ex – 1 = 0
 */
public class HP3 {
	
	public static double f(double x) {
		return (Math.exp(x)-1);
	}

	public static double bisection(double rangeBot, double rangeUp) {
		// Middle point
		double mid = (rangeBot + rangeUp)*0.5;
		
		// Besectionally solve the e^x-1=0 's outcome for x
		while(Math.abs(f(mid)-0) > Math.pow(10, -7)) {
			
			// Update the bound
			if(f(mid)>0) {
				rangeUp = mid;
			}
			else if (f(mid)<0) {
				// bottom = mid
				rangeBot = mid;
			}
			mid = (rangeBot + rangeUp)*0.5;
			System.out.println("f(mid) = "+Math.exp(mid)+", when mid = "+mid+", with range = ["+rangeBot+", "+rangeUp+"]");		
		}
		return mid;
	}
	
	
	public static void main(String Args[]) {
		System.out.println("The computed mid point is " + bisection(-5,10));
	}
	
	
}
