
/**
 * FullMatrix.java, ECE4960-P2
 * Created by Yuan He(yh772) on 2018-03-04
 * Platform: Java 8, Eclipse, MacOS
 * Copyright © 2018 Yuan He. All rights reserved.
 * 
 * Project3, Type FullMatrix:
 * Constructed type FullMatrix, and implemented FullMatrix's basic operation.
 */

public class FullMatrix {

	/* Class Variants: Data structures for keeping the FullMatrix*/
	double[][] full;
	int rank;

	
	/* Class Methods:*/
	/**Function: Constructor, construct a FullMatrix with a 2-d array
	 * Parameters: double[][] m
	 * Return : None*/
	public FullMatrix(double[][] m) {
		full = m.clone();
		rank = m.length;
	}

	/**Constructor2*/
	/**Function: Constructor2, construct an empty 2-d FullMatrix by only indicating its rank
	 * Parameters: int rank
	 * Return : None*/
	public FullMatrix(int rank) {
		double[][] fm = new double[rank][rank];
		full = fm;
		this.rank = rank;
	}
	
	/**Function: compare two matrixes element to element to see if they are equivalent to each other
	 * Parameters: this.FullMatrix, FullMatrix B
	 * Return: int indicator, with 0 representing in-equivalent and 1 representing equivalent*/
	public int equals(FullMatrix B) {
		for(int i=0; i<rank; i++) {
			for(int j=0; j<rank; j++) {
				if(full[i][j] != B.full[i][j]) {
					return 0;
				}
			}
		}
		return 1;
	}

	/**Function: Switch row[i] and row[j] for matrix A and Vector x.
	 * Parameters: row index, int i and j
	 * Return: 0 on success, 1 on errors and exceptions*/
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
	/**Function: Switch row[i] and row[j] for matrix A and Vector x.
	 * Parameters: row index, int i and j
	 * Return: 0 on success, 1 on errors and exceptions*/
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

	/**Function: Return the product of this.Matrix * Vector
	 * Parameter: the right multiplied Vector, vec
	 * Return: result Vector*/
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

	/**Function: Return the inverse matrix of a 2*2 matrix
	 * Parameters: None
	 * Return: an inversed 2*2 matrix*/
	public FullMatrix inverseRank2() {
		// Calculate the determinant of this matrix
		double determinant = full[0][0]*full[1][1] - full[0][1]*full[1][0] ;

		// Calculate the adjoint matrix
		FullMatrix inverse = new FullMatrix(rank);
		inverse.full[0][0] = full[1][1];
		inverse.full[0][1] = -full[0][1];
		inverse.full[1][0] = -full[1][0];
		inverse.full[1][1] = full[0][0];

		// Inverse = 1/|A| * [Adjoint Marix]
		for(int i=0; i<rank; i++) {
			for(int j=0; j<rank; j++) {inverse.full[i][j] /= determinant;}
		}
		return inverse;
	}

	/**Function: Return the inverse matrix of a 3*3 matrix
	 * Parameters: None
	 * Return: an inversed 3*3 matrix*/
	public FullMatrix inverseRank3() {
		// Calculate the determinant of this matrix
		double determinant = full[0][0]*(full[1][1]*full[2][2] - full[2][1]*full[1][2]) - 
				full[1][0]*(full[0][1]*full[2][2] - full[2][1]*full[0][2]) + 
				full[2][0]*(full[0][1]*full[1][2] - full[1][1]*full[0][2]);

		// Calculate the adjoint matrix
		FullMatrix inverse = new FullMatrix(rank);
		inverse.full[0][0] = full[1][1]*full[2][2] - full[2][1]*full[1][2];
		inverse.full[0][1] = full[0][2]*full[2][1] - full[2][2]*full[0][1];
		inverse.full[0][2] = full[0][1]*full[1][2] - full[0][2]*full[1][1];
		inverse.full[1][0] = full[1][2]*full[2][0] - full[2][2]*full[1][0];
		inverse.full[1][1] = full[0][0]*full[2][2] - full[2][0]*full[0][2];
		inverse.full[1][2] = full[0][2]*full[1][0] - full[1][2]*full[0][0];
		inverse.full[2][0] = full[1][0]*full[2][1] - full[1][1]*full[2][0];
		inverse.full[2][1] = full[0][1]*full[2][0] - full[2][1]*full[0][0];
		inverse.full[2][2] = full[0][0]*full[1][1] - full[1][0]*full[0][1];

		// Inverse = 1/|A| * [Adjoint Marix]
		for(int i=0; i<rank; i++) {
			for(int j=0; j<rank; j++) {inverse.full[i][j] /= determinant;}
		}
		return inverse;
	}

	/**Keep function invariants true: 0 <= index < size*/
	public void assertInd(int i, int j) {
		assert i>=0 && i<rank;
		assert j>=0 && j<rank;
	}

}