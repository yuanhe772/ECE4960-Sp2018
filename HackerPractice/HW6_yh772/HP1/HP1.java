import java.util.Arrays;

/**
 * HP1.java, ECE4960-HW6
 * Created by Yuan He(yh772) on 2018/03/05
 * Platform: Java 8, Eclipse, MacOS
 * Copyright © 2018 Yuan He. All rights reserved.
 */
public class HP1 {

	/**Implement the Jacobi Iterative solver*/
	public static int Jacobi(SparseMatrix A, Vector b) {
		// Decompose matrix sp into the form of L, D, U
		SparseMatrix L = new SparseMatrix(A.rank);
		SparseMatrix DInverse = new SparseMatrix(A.rank);
		SparseMatrix U = new SparseMatrix(A.rank);
		A.JacobiDecom(L, DInverse, U);

		// Norm of difference between last and current iteration's outcome
		double errNorm = 1;
		
		// Number of iterations:
		int i=0;

		// Pre-calculate (D^-1)*b, and (D^-1)*(L+U)
		Vector Db = DInverse.product(b);
		SparseMatrix DLU = DInverse.leftMult(L.add(U));

		// Vector containing (D^−1) * (L+U) * x(k−1)
		Vector DLUX = new Vector(Db.len);

		// Vector for previous and current outcomes, initialized to (D^-1)*b:
		Vector currX = Db;
		Vector prevX = currX;
		
		while (errNorm > 0.0000001) {
			errNorm = 0;
			prevX = currX;
			DLUX = DLU.product(currX);

			// An iteration of the Jacabi iterative solver:
			currX = DLUX.add(Db);

			// Calculate the second norm error
			errNorm = norm2Err(currX, prevX);

			// Iteration counter increments:
			i+=1;

//			// Printing out the outcome of result:
//			System.out.println("\nThe error norm = "+errNorm);
//			System.out.println("Outcome X = "+Arrays.toString(currX.v));
		}
		return i;
	}

	/**Calculate the second norm difference between u & v*/
	public static double norm2Err(Vector u, Vector v) {
		// Increment the error:
		double errNorm = 0;
		for(int j=0; j<v.len; j++) {
			errNorm += Math.pow((v.v[j] - u.v[j]),2);
		}

		// Calculate the current 2-norm error:
		errNorm = Math.pow(errNorm, 0.5);
		return errNorm;
	}

	/**Main function*/
	public static void main(String args[]) {
		/** Instantiate the Sparse Matrix, and it's LDU-Decomposition*/
		double value[] = {-4,1,1,4,-4,1,1,-4,1,1,-4,1,1,1,-4};
		int rowPtr[] = {0,3,6,9,12,15};
		int colInd[] = {0,1,4,0,1,2,1,2,3,2,3,4,0,3,4};
		SparseMatrix A = new SparseMatrix(value, rowPtr, colInd);

		double bb[] = {1,0,0,0,0};
		Vector b = new Vector(bb);

		System.out.println("\nIteration times = "+Jacobi(A, b));

	}
}
