/**
 * FullMatrix.java, ECE4960-P2
 * Created by Yuan He(yh772) on 2018-03-04
 * Platform: Java 8, Eclipse, MacOS
 * Copyright © 2018 Yuan He. All rights reserved.
 */


public class FullMatrix {

	// Data structures for keeping the Vector
	double[][] full;
	int rank;


	/**Constructor*/
	public FullMatrix(double[][] m) {
		// Update the data structures
		full = m.clone();
		rank = m.length;
	}


	/**Switch row[i] and row[j] for matrix A and Vector x. (Return 0 on success, 1 on failures)*/
	public int rowPermute(int i, int j) {
		try {
			// Keep function invariants true
			assertInd(i ,j);
			
			// Iteratively switch each corresponding element in row i & j
			double temp = 0;
			for(int it=0; it<rank; it++) {
				temp = full[i][it];
				full[i][it] = full[j][it];
				full[j][it] = temp;
			}
			return 0;
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return 1;
		}
	}

	/**Add a*row[i] to row[j] for Matrix A and Vector x. (Return 0 on success, 1 on failures)*/
	public int rowScale(int i, int j, double a) {
		try {
			// Keep function invariants true
			assertInd(i ,j);
			
			// Iteratively scale up each corresponding element in row j by row i
			for(int it=0; it<rank; it++) {
				full[j][it] += a*full[i][it];
			}
			return 0;
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return 1;
		}
	}

	/**Return the product of Ax = b*/
	public Vector product(Vector vec) {
		if(vec.len != rank) 
			throw new ArithmeticException ("Matrix*Vector unmatching size error: rank != length");
		double[] r = new double[vec.len];
		// Iteratively multiply each element in Full Matrix by vector vec
		for(int i=0; i<rank; i++) {
			for(int j=0; j<rank; j++) {
				r[i] += full[i][j]*vec.v[j];
			}
		}		
		return new Vector(r);
	}
	
	/**Keep function invariants true: 0 <= index < size*/
	public void assertInd(int i, int j) {
		assert i>=0 && i<rank;
		assert j>=0 && j<rank;
	}
}

