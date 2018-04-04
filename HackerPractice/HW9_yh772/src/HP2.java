
/**
 * HP2.java, ECE4960-HW9
 * Created by Yuan He(yh772) on 2018/03/29
 * Platform: Java 8, Eclipse, MacOS
 * Copyright © 2018 Yuan He. All rights reserved.
 * 
 * New Note 6, page 23:
 * Compare the forward Euler method, the one-step Huen’s method (trapezoidal) 
 * and the Huen’s method with iteration (using tol = 10-7).
 */
public class HP2 {
	static double h = 1;
	
	// The true x(0) = 2
	public static double xTrue(double t) {
		double a = Math.exp(0.8*t) - Math.exp(-0.5*t);
		double b = Math.exp(-0.5*t);
		return ((4/1.3)*a + 2*b);
	}

	// The f(x,t) = dx/dt
	public static double fxt(double xt,double t) {
		return (4*Math.exp(0.8*t) - 0.5*xt);
	}

	// The predictor
	public static double predictor(double x, double t) {
		return (x + fxt(x,t) * h);
	}

	// The norm of corrector
	public static double correctorNorm(double prevX, double currX) {
		return Math.abs((currX-prevX) / currX);
	}

	// The corrector (should indicate the iteration time)
	public static double corrector(double x, double x_i1_j_1, double t) {
		double t_i1 = t + h;
		return (x + (fxt(x,t) + fxt(x_i1_j_1,t_i1))*0.5*h);
	}
	
	// The iterative huen corrector
	public static double huenIterator(double x, double t) {
		double x_i1_j_1 = predictor(x,t);
		double currX = 0;
		double norm = 1;
		while(norm>Math.pow(10,-7)) {
			x_i1_j_1 = currX;
			currX = corrector(x,x_i1_j_1,t);
			norm = correctorNorm(x_i1_j_1, currX);
		}
		return currX;
	}

	public static void main(String args[]) {
		// The initial value for Euler estimators
		double forwardEuler_x = 2;
		double oneStep_x = 2;
		double iterativ_x = 2;
		
		// March with t being from 0~4
		for(double t=0; t<=4; t+=1) {
			System.out.println("t = "+t+
					"\n   ForwardEuler: x ="+forwardEuler_x+", Error% = "+Math.abs(forwardEuler_x-xTrue(t))/forwardEuler_x+
					".\n   OneStepHuen: x = "+oneStep_x+", Error% = "+Math.abs(oneStep_x-xTrue(t))/oneStep_x+
					".\n   IterativeHuen: x = "+iterativ_x+", Error% = "+Math.abs(iterativ_x - xTrue(t))/iterativ_x);
			forwardEuler_x = predictor(forwardEuler_x, t);
			oneStep_x = corrector(oneStep_x, predictor(oneStep_x,t), t);
			iterativ_x = huenIterator(iterativ_x, t);
		}	
	}
}
