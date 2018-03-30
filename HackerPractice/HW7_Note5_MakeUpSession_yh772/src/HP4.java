import java.util.Arrays;

/**
 * HP4.java, ECE4960-HW7
 * Created by Yuan He(yh772) on 2018/03/21
 * Platform: Java 8, Eclipse, MacOS
 * Copyright © 2018 Yuan He. All rights reserved.
 * 
 * note5, p41:
 * Use the steepest descent method with line search to solve the 
 * nonlinear optimization function V by making x(0) = (2, 2) and 
 * the local analysis by Δxi = 10-4⋅xi perturbation.
 */
public class HP4 {

	// Calculate the value of V
	public static double V(Vector x) {return Math.pow((3*Math.pow(x.v[0], 2) + x.v[1] - 4), 2) 
			+ Math.pow((Math.pow(x.v[0], 2) - 3*x.v[1] + 2), 2);}

	// Calculate dx(x bar)
	public static Vector dx(Vector x) {
		Vector perturb = x.scale(0.0001);
		Vector dx = new Vector(x.len);
		double xChange[] = new double[x.len];
		for(int i=0; i<x.len; i++) {
			xChange = x.v.clone();
			xChange[i] += perturb.v[i];
			dx.v[i] = (V(new Vector(xChange)) - V(x));
			dx.v[i] /= (-perturb.v[i]);
		}
		return dx;
	}

	// Newton Method
	public static void Newton(Vector x) {
		System.out.println("\n\nThe initial guess is ["+x.v[0]+","+x.v[1]+"]:");
		double t = 1;
		double norm1 = 0;
		double norm2 = 0;
		int counter = 0;

		while (Math.abs(V(x))>Math.pow(10, -7)) {
			t = 1;
			norm1 = Math.abs(V(x.add(dx(x),t)));
			norm2 = Math.abs(V(x.add(dx(x),t/2)));
			while(norm1>norm2) {
				norm1 = norm2;
				t /= 2;
				norm2 = Math.abs(V(x.add(dx(x),t/2)));
			}
			Vector d=dx(x);
			x = x.add(d,t);
			System.out.println("    || x || = "+x.secondNorm()+"     [x1, x2] = "+Arrays.toString(x.v)+
					"     || dx(x) ||= "+dx(x).secondNorm()+"     t = "+t+"     || Vx(x) || ="
					+V(x));
			counter += 1;
		}
		System.out.println("To converge, it took "+counter+" times of iterations.");
	}

	public static void main(String args[]) {
		// Initial guess is x = [2,2]
		double xx[] = {2, 2};
		Vector x = new Vector(xx);
		Newton(x);
		
		// Initial guess is x = [-2,-2]
		double yy[] = {-2, -2};
		Vector y = new Vector(yy);
		Newton(y);
	}
}
