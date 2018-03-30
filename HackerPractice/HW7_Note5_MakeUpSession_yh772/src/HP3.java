import java.util.Arrays;

/**
 * HP3.java, ECE4960-HW7
 * Created by Yuan He(yh772) on 2018/03/20
 * Platform: Java 8, Eclipse, MacOS
 * Copyright © 2018 Yuan He. All rights reserved.
 * 
 * note 5, slide 37:
 * Use the quasi-Newton method with line search to solve the nonlinear 
 * optimization function V by making x(0) = (2, 2) and the local 
 * analysis of the Hessian matrix by 10-4 perturbation.
 */
public class HP3 {

	// Vector x's first derivative
	public static Vector FirstDeriV(Vector x) {
		Vector FirstDeriV = new Vector(x.len);
		// 1-order derivative for x1
		FirstDeriV.v[0] = 40*Math.pow(x.v[0], 3) - 40*x.v[0];
		// 1-order derivative for x2
		FirstDeriV.v[1] = 20*x.v[1] - 20;
		return FirstDeriV;
	}

	// The reverse of Hessian matrix
	public static FullMatrix HessianReverse(Vector x) {
		FullMatrix H = new FullMatrix(x.len);
		H.full[0][0] = Math.pow(120*Math.pow(x.v[0],2) - 40, -1); 
		H.full[0][1] = 0;
		H.full[1][0] = 0;
		H.full[1][1] = Math.pow(20, -1);
		return H;
	}

	// Delta(x bar)
	public static Vector dx(Vector x) {
		FullMatrix H = HessianReverse(x);
		Vector FirstDeriV = FirstDeriV(x);
		Vector dx = H.product(FirstDeriV.scale(-1));
		return dx;
	}

	// V(x bar)
	public static Vector vx(Vector x) {
		double[] rr = new double[2];
		Vector r = new Vector(rr);
		r.v[0] = Math.pow((3*Math.pow(x.v[0], 2) + x.v[1] - 4),2);
		r.v[1] = Math.pow(Math.pow(x.v[0], 2) - 3*x.v[1] + 2, 2);
		return r;
	}

	// Newton Method
	public static void Newton(Vector x) {
		double t = 1;
		double norm1 = 0;
		double norm2 = 0;
		int counter = 0;

		while (vx(x).secondNorm()>Math.pow(10, -7)) {
			t = 1;
			norm1 = vx(x.add(dx(x),t)).secondNorm();
			norm2 = vx(x.add(dx(x),t/2)).secondNorm();
			while(norm1>norm2) {
				norm1 = norm2;
				t /= 2;
				norm2 = vx(x.add(dx(x),t/2)).secondNorm();
				System.out.println(t);
			}
			x = x.add(dx(x),t);
			System.out.println("|| x || = "+x.secondNorm()+"     [x1, x2] = "+Arrays.toString(x.v)+
					"     || dx(x) ||= "+dx(x).secondNorm()+"     t = "+t+"     || Vx(x) || ="
					+vx(x).secondNorm());
			counter += 1;
		}
		System.out.println("To converge, it took "+counter+" times of iterations.");
	}

	public static void main(String args[]) {
		// Initial guess is x = [2,2]
		System.out.println("The initial guess is [2,2]:");
		double xx[] = {2,2};
		Vector x = new Vector(xx);
		Newton(x);

		// Initial guess is x = [-2,2]
		System.out.println("\n\nThe initial guess is [-2,2]:");
		double yy[] = {-2,2};
		Vector y = new Vector(yy);
		Newton(y);
	}
}
