import java.util.Arrays;

/**
 * HP1.java, ECE4960-HW6
 * Created by Yuan He(yh772) on 2018/03/11
 * Platform: Java 8, Eclipse, MacOS
 * Copyright © 2018 Yuan He. All rights reserved.
 * 
 * Hacker Practice Purpose:
 * Use the SOR iterative solver to solve the problem from finite element.
 */
public class HP2 {

	/**Calculate the second norm of (delta/v) */
	public static double norm2Err(Vector u, Vector v) {
		// Increment the error:
		double errNorm = 0;
		for(int j=0; j<v.len; j++) {
			errNorm += Math.pow((v.v[j] - u.v[j]),2);
		}

		// The norm of v:
		double normV = 0;
		for(int j=0; j<v.len; j++) {
			normV += Math.pow(v.v[j],2);
		}

		// Calculate the current 2-norm error:
		errNorm = Math.pow(errNorm/normV, 0.5);
		return errNorm;
	}

	/**The norm of ||Ax-b|| */
	public static double precision(SparseMatrix A, Vector x, Vector b) {
		// ||A*x-b||:
		Vector err = (A.product(x)).add(b,-1);

		double errNorm = 0;
		for(int j=0; j<x.len; j++) {
			errNorm += Math.pow(err.v[j],2);
		}

		// Calculate the current 2-norm error:
		errNorm = Math.pow(errNorm, 0.5);
		return errNorm;
	}

	public static int SOR(SparseMatrix A, Vector b, double w) {
		
		SparseMatrix DInverse = new SparseMatrix(A.rank);
		for(int i=0; i<DInverse.rank ;i++) {
			DInverse.matrixSetter(i,i,-0.25);
		}
		
		Vector Db = DInverse.product(b);
		Vector X = Db;
		Vector PrevX = X;
		
		// Norm of difference between last and current iteration's outcome
		double errNorm = 1;

		Vector R = new Vector(b.len);
		
		SparseMatrix DInverse2 = new SparseMatrix(A.rank);
		for(int i=0; i<DInverse2.rank ;i++) {
			DInverse2.matrixSetter(i,i,-0.25*w);
		}
		
		int i=0;
		while (errNorm > Math.pow(10, -7)) {
			errNorm = 0;
			PrevX = X;
			R = b.add(A.product(X), -1);
			X = X.add(DInverse.product(R), 1);
			errNorm = norm2Err(X, PrevX);
			
			System.out.println("\nThe "+i+"th iteration, X = "+Arrays.toString(X.v)+
					"\n  ||delta X|| = " + errNorm + 
					"\n  || R(k) || = " + normVec(R) + 
					"\n  R(k) = "+Arrays.toString(R.v));
			i++;
		}
		return i;
	}
	
	public static double normVec(Vector c) {
		int sum = 0;
		for(int i=0; i<c.len;i++) {
			sum += Math.pow(c.v[i],2);
		}
		return Math.pow(sum,0.5);
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
		double w = 2;
		
		SOR(A, b, w);

	}
}
