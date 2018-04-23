import java.util.ArrayList;

/**
 * Solvers.java, ECE4960-P4
 * Created by Yuan He(yh772) on 2018/04/17
 * Platform: Java 8, Eclipse, MacOS
 * Copyright © 2018 Yuan He. All rights reserved.
 * 
 * P4, implement ODE solvers:
 * Implements ODE solvers, including forward Euler, RK4, and RK34 with time adaption
 */
public class Solvers {

	/* Class Variants: */
	static final double eR = 1e-4;
	static final double eA = 1e-7;
	static final double Tol1 = 1e-2;
	static final double Tol2 = 1e-6;

	/* Class Methods: */
	/**Function: The ground-truth x(t)
	 * @param: double ti, String fxType
	 * @return: */
	public static double trueX(double t, String fType) {
		if(fType.equals("ODE Validation")) {
			double a = Math.exp(0.8*t)-Math.exp(-0.5*t);
			double b = Math.exp(-0.5*t);
			return (a*4/1.3+b*2);
		}
		else return 0;
	}

	/**Function: ODE functions: f(x,t) = dx(t) / dt
	 * 			 Functions are chosen according to the input fType
	 * @param: double x, double t, String fxType
	 * @return: double value*/
	public static double f(Vector x, double t, String fType) {
		// The constants
		double C1 = 1e-12;
		double C2 = 1e-12;
		double R1 = 1e4;
		double R2 = 1e4;
		double R3 = 1e4;
		double RG = 1e4;
		double RL = 1e4;
		double Vdd = 5;

		// The functions
		if(fType.equals("ODE Validation")) {
			return 4*Math.exp(0.8*t)-0.5*x.v[0];
		}
		else if(fType.equals("Circuit1-1")) {
			double former = -(1/(C1*R1) + 1/(C1*R2)) * x.v[0];
			double latter = 1/(C1*R2) * x.v[1];
			return (former + latter + i(t*1e9)/C1);
		}
		else if(fType.equals("Circuit1-2")) {
			double former = 1/(C1*R2) * x.v[0];
			double latter = -(1/(C2*R2) + 1/(C2*R3)) * x.v[1];
			return (former + latter);
		}
		else if(fType.equals("Circuit2-1")) {
			return (i(t*1e9)*RG - x.v[0]) / (RG*C1);
		}
		else if(fType.equals("Circuit2-2")) {
			double former = - IdEKV(x.v[0], x.v[1]) / C2;
			double latter = (Vdd - x.v[1])/ (RL*C2);
			return (former + latter);
		}
		else return 0;
	}

	/**Function: Transient i(t)
	 * @param: double t, unit = (ns)
	 * @return: double value*/
	public static double i(double t) {
		// Divide by period T = 20ns
		double remainder= t%20;

		// Calculate i(t)
		if(remainder>=0 && remainder<1) 
			return remainder*1e-4;
		else if(remainder>=1 && remainder<10) 
			return 1e-4;
		else if(remainder>=10 && remainder<11) 
			return (-0.1*remainder+1.1)*1e-3;
		else return 0;
	}

	/**Function: EKV IdEKV(t)
	 * @param: double V1, double V2
	 * @return: double Id*/
	private static double IdEKV(double V1, double V2) {
		// The constants
		double Is = 5e-6;
		double Kappa = 0.7;
		double Vth = 1;
		double Vt = 0.026;

		// The calculation
		double former= 1 + Math.exp(Kappa*(V1-Vth) / (2*Vt));
		double latter= 1 + Math.exp((Kappa*(V1-Vth)-V2) / (2*Vt));
		return Is*(Math.log(former)*Math.log(former) - Math.log(latter)*Math.log(latter));
	}

	/**Function: Calculate the values of K for RK3, and RK4
	 * @param: Vector xi, double ti, double h, String fxType
	 * @return: ArrayList<Vector> K = [K1, K2, K3, K4], where each K
	 * 			contains two elements, respectively for V1 and V2*/
	public static ArrayList<Vector> K(Vector x, double t, double h, String[] fType) {
		ArrayList<Vector> K = new ArrayList<Vector>();
		K.add(new Vector(x.len));
		K.add(new Vector(x.len));
		K.add(new Vector(x.len));
		K.add(new Vector(x.len));

		// Update K1
		for (int i=0; i<x.len; i++) {
			K.get(0).v[i] = f(x, t, fType[i]);
		}
		// Update K2
		for(int i=0; i<x.len; i++) {
			K.get(1).v[i] = f(x.add(K.get(0), 0.5*h), t + 0.5*h, fType[i]);
		}
		// Update K3
		for(int i=0; i<x.len; i++) {
			K.get(2).v[i] = f(x.add(K.get(1), 3.0/4*h), t + 3.0/4*h, fType[i]);
		}
		// Update K4
		for(int i=0; i<x.len; i++) {
			K.get(3).v[i] = f(x.add(K.get(2), h), t + h, fType[i]);
		}
		return K;
	}

	/**Function: Calculate x[] by RK3 and RK4
	 * @param: Vector x, double t, double h(step length), String[] fType
	 * @return: ArrayList<Vector> X = [X(RK3), X(RK4)]*/
	public static ArrayList<Vector> xRK3_xRK4(Vector x, double t, double h, String[] fType) {
		ArrayList<Vector> K = K(x, t, h, fType);
		Vector K1 = K.get(0);
		Vector K2 = K.get(1);
		Vector K3 = K.get(2);
		Vector K4 = K.get(3);
		
		// Calculate x(RK3)
		Vector KsumRK3 = ((K1.scale(2.0)).add(K2, 3.0)).add(K3, 4.0);
		Vector x_i1_RK3 = x.add(KsumRK3, h*1.0/9.0);

		// Calculate x(RK4)
		Vector KsumRK4 = (((K1.scale(7.0)).add(K2, 6.0)).add(K3, 8.0)).add(K4, 3.0);
		Vector x_i1_RK4 = x.add(KsumRK4, h*1.0/24.0);

		// Return RK3, RK4 together as an ArrayList
		ArrayList<Vector> xRK3_xRK4 = new ArrayList<Vector>();
		xRK3_xRK4.add(x_i1_RK3);
		xRK3_xRK4.add(x_i1_RK4);
		return xRK3_xRK4;
	}

	/**Function: Calculate x[] by RK34 with adaptive h
	 * @param: Vector x, double t, double h(step length), String fType[]
	 * @return: */
	public static Vector RK34AdaptiveH(Vector x, double t, double h, String fType[]) {
		// Initialize the method data structures
		ArrayList<Vector> xRK3_xRK4 = new ArrayList<Vector>(x.len);
		double r = 0;
		double step = 0;
		double stepLimit = h;

		// Within each time-step
		while(step < stepLimit) {
			xRK3_xRK4 = xRK3_xRK4(x, t, h, fType);
			r = r(xRK3_xRK4);
			// Adapt the h
			while(Math.abs(r-1) > Tol1 || Math.abs(r-1) < Tol2) {
				h /= Math.pow(r, 1.0/3.0); 
				if(h+step > stepLimit) 
					break;
				xRK3_xRK4 = xRK3_xRK4(x, t, h, fType);
				r = r(xRK3_xRK4);
			}
			// Update the x_i0
			t += h;
			step += h;
			x.v = xRK3_xRK4.get(1).v.clone();
			h = stepLimit - step;
		}
		return x;
	}

	/**Function: Calculate the normalized error r = (xRK3 - xRK4) / (eR*xRK4 + eA)
	 * @param: ArrayList<Vector> [x(RK3), x(RK4)]
	 * @return: double r*/
	public static double r(ArrayList<Vector> xRK3_xRK4) {
		Vector xRK3 = xRK3_xRK4.get(0);
		Vector xRK4 = xRK3_xRK4.get(1);
		Vector E = xRK3.add(xRK4, -1);
		double r = E.norm() / (xRK4.scale(eR).norm() + eA);
		return r;
	}

	/**Function: Calculate x[] by Forward-Euler
	 * @param: Vector x, double t, double h(step length), String fType[]
	 * @return:*/
	public static Vector forwardEuler(Vector x, double t, double h, String fType[]) {
		Vector f = new Vector(x.len);
		for(int i=0; i<x.len; i++) {
			f.v[i] = f(x, t, fType[i]);
		}
		x = x.add(f, h);
		return x;
	}

	/**Function: Calculate the normalized relative error, ||Error%||2:
	 * 			By computing the relative error of each unknown variable, 
	 * 			and take the 2nd-order norm of their sum
	 * @param: Vector x, double t, String[] fType
	 * @return: double ||Error%||2 */
	public static double relativeErr(Vector x, double t, String[] fType) {
		double truth = 0;
		double sum = 0;

		// Computing each unknown variable's 2nd-order norm
		for(int i=0; i<x.len; i++) {
			truth = trueX(t+1, fType[i]);
			sum += Math.pow((truth - x.v[i])/truth, 2);
		}
		return Math.pow(sum, 0.5);
	}
}
