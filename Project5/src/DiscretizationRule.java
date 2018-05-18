/**
 * DiscretizationMethod.java, ECE4960-P5
 * Created by Yuan He(yh772) on 2018/05/16
 * Platform: Java 8, Eclipse, MacOS
 * Copyright © 2018 Yuan He. All rights reserved.
 * 
 * This interface is used to create method handlers for constructing Matrix A and B
 * for different discretization rules in 1D and 2D 
 */

/**@Interface: provide method handler for different discretizating rules*/
interface ruleType {
	
	/**Function: Construct the elements A1, A2, B1, B2 for matrix A and B
	 * @param: double h, double D, double dt
	 * @return: [A1, A2, B1, B2]*/
	public double[] A1A2B1B2(double h, double D, double dt);
	
	/**Function: Return its rule name for output log file creation
	 * @param: None
	 * @return: String name*/
	public String ruleName();
}

/**@Protected_classes: forward Euler in 1D, implements the abstract methods in interface ruleType*/
class forward1D implements ruleType {
	
	@Override
	public double[] A1A2B1B2(double h, double D, double dt) {
		// construct A1, A2, B1, B2 according to trapezoid rule
		double A1 = dt*D/(h*h);
		double A2 = -2*D*dt/(h*h) + 1/dt;
		double[] res = {A1, A2};
		return res;
	}

	@Override
	public String ruleName() {
		return "Forward1D";
	}
}

/**@Protected_classes: backward Euler in 1D, implements the abstract methods in interface ruleType*/
class backward1D implements ruleType {
	
	@Override
	public double[] A1A2B1B2(double h, double D, double dt) {
		// construct A1, A2, B1, B2 according to trapezoid rule
		double A1 = -D/(h*h);
		double A2 = 1.0/dt + 2.0*D/(h*h);
		double B1 = 0;
		double B2 = 1.0/dt;
		double[] res = {A1, A2, B1, B2};
		return res;
	}
	
	@Override
	public String ruleName() {
		return "Backward1D";
	}
}

/**@Protected_classes: trapezoid Euler in 1D, implements the abstract methods in interface ruleType*/
class trapezoid1D implements ruleType {
	
	@Override
	public double[] A1A2B1B2(double h, double D, double dt) {
		// construct A1, A2, B1, B2 according to trapezoid rule
		double A1 = -D/(2*h*h);
		double A2 = 1.0/dt + D/(h*h);
		double B1 = D/(2.0*h*h);
		double B2 = 1.0/dt - D/(h*h);
		double[] res = {A1, A2, B1, B2};
		return res;
	}
	
	@Override
	public String ruleName() {
		return "Trapezoid1D";
	}
}

/**@Protected_classes: forward Euler in 2D, implements the abstract methods in interface ruleType*/
class forward2D implements ruleType {

	@Override
	public double[] A1A2B1B2(double h, double D, double dt) {
		// construct A1, A2, B1, B2 according to trapezoid rule
		double A1 = dt*D/(h*h);
		double A2 = -4*D*dt/(h*h) + 1;
		double[] res = {A1, A2};
		return res;
	}
	
	@Override
	public String ruleName() {
		return "Forward2D";
	}
}

/**@Protected_classes: backward Euler in 2D, implements the abstract methods in interface ruleType*/
class backward2D implements ruleType {
	
	@Override
	public double[] A1A2B1B2(double h, double D, double dt) {
		// construct A1, A2, B1, B2 according to trapezoid rule
		double A1 = -D/(h*h);
		double A2 = 1.0/dt + 4.0*D/(h*h);
		double B1 = 0;
		double B2 = 1.0/dt;
		double[] res = {A1, A2, B1, B2};
		return res;
	}
	
	@Override
	public String ruleName() {
		return "Backward2D";
	}
}

/**@Protected_classes: trapezoid Euler in 2D, implements the abstract methods in interface ruleType*/
class trapezoid2D implements ruleType {
	
	@Override
	public double[] A1A2B1B2(double h, double D, double dt) {
		// construct A1, A2, B1, B2 according to trapezoid rule
		double A1 = -D/(2.0*h*h);
		double A2 = 1.0/dt + 2.0*D/(h*h);
		double B1 = D/(2.0*h*h);
		double B2 = 1.0/dt - 2.0*D/(h*h);
		double[] res = {A1, A2, B1, B2};
		return res;
	}
	
	@Override
	public String ruleName() {
		return "Trapezoid2D";
	}
}