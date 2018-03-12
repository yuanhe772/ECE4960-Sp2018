import java.util.ArrayList;

/**
 * SparseMatrix.java, ECE4960-P2
 * Created by Yuan He(yh772) on 2018-03-04
 * Platform: Java 8, Eclipse, MacOS
 * Copyright © 2018 Yuan He. All rights reserved.
 */

public class SparseMatrix {

	// Data structures for keeping the Sparse Matrix
	ArrayList<Double> value = new ArrayList<Double>();
	ArrayList<Integer> rowPtr = new ArrayList<Integer>();
	ArrayList<Integer> colInd = new ArrayList<Integer>();
	int rank;

	/**Function: Constructor, construct a SparseMatrix by passing in ArrayLists of its data structure
	 * Parameters: ArrayList<Double> value, ArrayList<Integer> rowPtr, ArrayList<Integer> colInd 
	 * Return: None*/
	public SparseMatrix(ArrayList<Double> value, ArrayList<Integer> rowPtr, ArrayList<Integer> colInd) {
		// Update sparse matrix's rank
		rank = rowPtr.size()-1;

		// Update sparse matrix's data structures
		this.value = value;
		this.rowPtr = rowPtr;
		this.colInd = colInd;
	}

	/**Function: Constructor2, construct a SparseMatrix by passing in arrays of its data structure
	 * Parameters: double[] value, int[] rowPtr, int[] colInd 
	 * Return: None*/
	public SparseMatrix(double[] value, int[] rowPtr, int[] colInd) {
		// Update sparse matrix's rank
		rank = rowPtr.length-1;

		// Update sparse matrix's data structures
		for(int i=0; i<value.length; i++) {this.value.add(value[i]);}
		for(int i=0; i<rowPtr.length; i++) {this.rowPtr.add(rowPtr[i]);}
		for(int i=0; i<colInd.length; i++) {this.colInd.add(colInd[i]);}
	}

	/**Function: Constructor3, construct an empty SparseMatrix by only indicating its rank
	 * Parameters: int rank 
	 * Return: None*/
	public SparseMatrix(int rank) {
		// Update sparse matrix's rank
		this.rank = rank;

		// Create empty data structures for this sparse empty matrix
		for(int i=0; i<rank+1; i++) {rowPtr.add(0);}
	}
	
	/**Function: Retrieve element(i,j)'s value, along with its index in value[] (colInd[])
	 * 			 For the convenience of set an non-zero value to another non-zero, faster than calling matrixSetter()
	 * Parameters: int i, int j
	 * Return: [index, value] */
	public double[] retrieveElement(int i, int j) {
		// Verify function invariant: index i, j are in bound
		assertInd(i,j);

		// Data structure for keeping the result
		double r[] = new double[2];

		// Start and end of row i
		int start = rowPtr.get(i);
		int end = rowPtr.get(i+1);

		// If find column j, return
		for (int k=start; k<end; k++) {
			if(j == colInd.get(k)) {
				r[0] = k;
				r[1] = value.get(k);
				return r;
			}
		}
		return r;
	}

	/**Function: Set element(i,j)'s value of any arbitrary matrix(including empty matrix), 
	 * 			 and re-arrange the data structures according to the changes
	 * Parameters: int i, int j, double v
	 * Return: None */
	public void matrixSetter(int i, int j, double v) {
		// Verify function invariant: index i, j are in bound
		assertInd(i,j);

		try {// The range of value(colInd) = [start, end]
			int start = rowPtr.get(i);
			int end = rowPtr.get(j);

			// Iff value!=0 (no new zero brought into the matrix)
			if(v!=0) {
				for(int k=start; k<end; k++) {
					// Iff position (i,j) originally non-zero, directly set the value
					if(j == colInd.get(k)) {
						value.set(k, v);
						return;
					}
				}

				// Iff position (i,j) originally zero; Insert into value[], colInd[]
				value.add(rowPtr.get(i), v);
				colInd.add(rowPtr.get(i), j);
				// Increment what's after rowPtr[i+1] by 1:
				for(int k=i+1; k<rowPtr.size(); k++) {rowPtr.set(k, rowPtr.get(k)+1);}
			}

			// Iff value==0 (new zeros brought into the matrix)
			else {	for(int k=start; k<end; k++) {

					// Iff position (i,j) originally non-zero
					if(j == colInd.get(k)) {

						// Directly delete elements in value[] and coInd[]
						value.remove(k);
						colInd.remove(k);
						// Decrement what's after rowPtr[r+1] by 1
						for(int kk=i+1; kk<rowPtr.size(); kk++) {rowPtr.set(kk, rowPtr.get(kk) - 1);}
						return;
					}
				}
			}
		}
		// For empty Sparse Matrix
		catch(IndexOutOfBoundsException e) {
			if(v!=0) {
				//Insert into value[], colInd[]:
				value.add(v);
				colInd.add(j);
				//Increment rowPtr[r+1] and the elements after it:
				for(int k=i+1; k<rowPtr.size(); k++) {rowPtr.set(k, rowPtr.get(k)+1);}
			}
		}
		return;
	}

	/**Function: Calculate Matrix * Vector vec
	 * Parameters: Vector vec
	 * Return: Vector product */
	public Vector product(Vector vec) {
		// Verify function invariant: matrix's rank == vector's length
		assert rank == vec.len : "Unmatched matrix sizes!";

		// Create data structure for containing the result vector
		double[] r = new double[vec.len];

		// Iteratively assign each element of the new vector to be the sum of row*vec
		for(int i=0; i < rank; i++) {
			for(int j=0; j < rank; j++) {
				r[i] += retrieveElement(i, j)[1] * vec.v[j]; 
			}
		}	
		return new Vector(r);
	}

	/**Function: Calculate DiagonalMatrix * Matrix sp
	 * Parameters: SparseMatrix sp
	 * Return: SparseMatrix product */
	public SparseMatrix leftMult(SparseMatrix sp) {
		// Verify function invariants: two matrixes are of the same rank
		assert sp.rank == rank : "Unmatched matrix sizes!";

		// Create data structure for storing the left-multiplication result
		SparseMatrix r = new SparseMatrix(sp.value, sp.rowPtr,sp.colInd);

		// Diagonal matrix's element(i,i), and right matrix's element(i,j)
		double ii = 0;
		double ij[] = new double[2];

		for(int i=0; i<rank; i++) {
			ii=retrieveElement(i,i)[1];
			for(int j=0; j<rank; j++) {
				ij = sp.retrieveElement(i, j);

				// Set matrix's value(Faster than calling matrixSetter() in this case), knowing no new 0 generated 
				if(ii*ij[1]!=0) {r.value.set((int) ij[0], ii*ij[1]);}
			}
		}
		return sp;
	}

	/**Function: Calculate MatrixA + d * MatrixB
	 * Parameters: SparseMatrix MatrixB, double d
	 * Return: SparseMatrix sumOf(A+d*B) */
	public SparseMatrix add(SparseMatrix B, double d) {
		// Verify function invariants: two matrixes are of the same rank
		assert B.rank == rank : "Unmatched matrix sizes!";

		// Create data structure for storing the addition result
		SparseMatrix sum = new SparseMatrix(rank);

		// Iteratively add two matrixes' (this matrix and matrix m) corresponding values into the sum
		for(int i=0; i<rank; i++) {
			for(int j=0; j<rank; j++) {
				sum.matrixSetter(i, j, retrieveElement(i,j)[1] + (B.retrieveElement(i, j)[1]*d));
			}
		}
		return sum;
	}

	
	/**Function: Decompose this matrix into (-L) + D-Inverse + (-U) for Jacobi Iterative Solver
	 * Parameters: SparseMatrix L, SparseMatrix DInverse, SparseMatrix U
	 * Return: None */
	public void JacobiDecom(SparseMatrix L, SparseMatrix DInverse, SparseMatrix U) {
		// Keep the function invariants true
		assert L.rank+DInverse.rank+U.rank == 3*rank : "Wrong input matrix for Jacobi Decomposation!";

		// Create DInverse matrix, by assigning the reciprocal of this matrix's diagonal to its diagonal
		for(int i=0; i<rank; i++) {
			DInverse.matrixSetter(i,i,1/retrieveElement(i, i)[1]);
		}

		// Create L & U matrix, by assigning this matrix's upper and lower diagonal values to them
		for(int i=0; i<rank; i++) {
			for(int j=i+1; j<rank; j++) {
				U.matrixSetter(i, j, (-1)*retrieveElement(i, j)[1]);
				L.matrixSetter(j, i, (-1)*retrieveElement(j, i)[1]);
			}
		}
	}

	/**Function: Verify Function invariants, making sure element(i,j) is in-bound of the index
	 * Parameters: int i, int j
	 * Return: None */
	public void assertInd(int i, int j) {
		assert i>=0 && i<rank : "IndexOutOfBound!";
		assert j>=0 && j<rank : "IndexOutOfBound!";
	}
}