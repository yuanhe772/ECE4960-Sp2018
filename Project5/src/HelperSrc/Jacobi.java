/**
 * Jacobi.java, ECE4960-P2
 * Created by Yuan He(yh772) on 2018/03/07
 * Platform: Java 8, Eclipse, MacOS
 * Copyright © 2018 Yuan He. All rights reserved.
 */


public class Jacobi {

	/**Funtion: Implement the Jacobi Iterative solver
	 * @param: SparseMatrix A, Vector b
	 * @return: int IteratedNum*/
	@SuppressWarnings("unused")
	public static Vector solver(SparseMatrix A, Vector b) {
		// Decompose matrix sp into the form of L, D, U
		SparseMatrix L = new SparseMatrix(A.rank);
		SparseMatrix DInverse = new SparseMatrix(A.rank);
		SparseMatrix U = new SparseMatrix(A.rank);
		A.JacobiDecom(L, DInverse, U);

		// Convergence indicator (delta X / X)'s norm
		double convNorm = 1;

		// Number of iterations:
		int i=0;

		// Pre-calculate the constants, (D^-1)*b, and (D^-1)*(L+U)
		Vector Db = DInverse.productVec(b);
		SparseMatrix DLU = DInverse.leftMult(L.add(U,1));

		// Create empty Vector for containing (D^−1) * (L+U) * x(k−1)
		Vector DLUX = new Vector(Db.len);

		// Vector for previous and current solutions, initialized to (D^-1)*b:
		Vector currX = Db;
		Vector prevX = currX;

		// Normalized norm of ||Ax-b|| / ||b||, the precision indicator for previous and current solutions
		double currNN = 1;
		double prevNN = 1;

		// Iff normalized norm is decreasing with each iteration, flag = true; Iff increases, flag = false
		boolean flag = true;

		while (convNorm > Math.pow(10, -7)) {
			convNorm = 0;
			prevX = currX;
			prevNN = currNN;
			DLUX = DLU.productVec(currX);

			// An iteration of the Jacabi iterative solver:
			currX = DLUX.add(Db,1);

			// Calculate the convergence indicator, delta X / prevX
			convNorm = convNorm(currX, prevX);

			// Iteration counter increments:
			i+=1;

			// Output current iteration's normalized norm to check its convergence
			currNN = normalNorm(A,currX,b);
			//			System.out.println("\n		The "+i+"th iteration's normalized-second-norm error: ||A*x - b|| / ||b|| = "+currNN);

			// Iff the precision decreases with more iteration
			if(currNN < prevNN) {flag = false;}
		}		
		//		System.out.println("\n 			To converge to |delta| / |x| < 10^-7, it took iteration times of "+i);

		// Javobi-Iterative-solver does converge
		if(flag = true)
			return currX;

		// Jacobi-Iterative-solver does not converge
		return currX;
	}

	// Helper functions:
	/**Function: Calculate the second norm of (delta / v), for convergence evaluation of (delta X / X) 
	 * @param: Vector u, Vector v
	 * @return: double error*/
	public static double convNorm(Vector u, Vector v) {
		// Accumulating the norms of ||u - v|| and v
		double normDiff = 0;
		double normV = 0;
		for(int j=0; j<v.len; j++) {
			normDiff += Math.pow((v.v[j] - u.v[j]),2);
			normV += Math.pow(v.v[j],2);
		}

		// Calculate the current 2-norm error:
		return Math.pow(normDiff/normV, 0.5);
	}

	/**Function: The normalized residual norm for X, ||Ax-b|| / ||b|| 
	 * @parameter: SparseMatrix A, Vector x, Vector b
	 * @return: double error (of ||Ax-b|| / ||b|| )*/
	public static double normalNorm(SparseMatrix A, Vector x, Vector b) {
		// A*x-b:
		Vector err = (A.productVec(x)).add(b,-1);

		// Accumulating the square of ||A*x-b|| (errSqr), and square of ||b|| (bSqr)
		double errSqr = 0;
		double bSqr = 0;
		for(int i=0; i<x.len; i++) {
			errSqr += Math.pow(err.v[i],2);
			bSqr += Math.pow(b.v[i],2);
		}

		// Calculate the normalized residual 2-norm error:
		return Math.pow(errSqr/bSqr, 0.5);
	}
}
