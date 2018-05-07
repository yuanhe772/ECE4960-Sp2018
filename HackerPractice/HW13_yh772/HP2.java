import java.util.Arrays;

/**
 * HP2.java, ECE4960-HW13
 * Created by Yuan He(yh772) on 2018/05/03
 * Platform: Java 8, Eclipse, MacOS
 * Copyright © 2018 Yuan He. All rights reserved.
 * 
 * Note 7(2), page 25
 */
public class HP2 {
	/* Class Variants: */
	// Matrix A:
	static int[] rowPtrA = { 0, 2, 5, 8, 11, 14, 17, 20, 23, 25 };
	static int[] colIndA = { 0,1, 0,1,2, 1,2,3, 2,3,4, 3,4,5, 4,5,6, 5,6,7, 6,7,8, 7,8};
	static double[] valueA = { -2,1, 1,-2,1, 1,-2,1, 1,-2,1, 1,-2,1, 1,-2,1, 1,-2,1, 1,-2,1, 1,-2 };
	static SparseMatrix A = new SparseMatrix(valueA, rowPtrA, colIndA);

	/* Class Methods: */
	/** A * PHI = h^2 * f, Want to solve PHI */
	public static Vector finiteElement(int N, double h) {
		double h2 = h*h;
		// Vector f
		double[] f = new double[N-1];
		for (int i = 0; i < f.length; i++) {f[i] = -1 * (h2);}			
		Vector PHI = Jacobi.solver(A,new Vector(f));
		return PHI;
	}

	public static void main(String[] args) {
		int N = 10;
		double h = (double) 1/N;
		Vector PHI = finiteElement(N, h);
		System.out.println("PHI = "+Arrays.toString(PHI.v)+"\n,  which is consistent with previous Hacker Practices.");
	}
}
