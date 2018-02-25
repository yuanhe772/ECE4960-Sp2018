/** Last Update: 02/25/2018; Author: Yuan He (yh772); Platform: Eclipse, Java8 */


import java.util.ArrayList;

public class Part1_Main {
	public static void main(String args[]) {

		ArrayList<Double> value = new ArrayList<Double>();
		ArrayList<Integer> rowPtr = new ArrayList<Integer>();
		ArrayList<Integer> colInd = new ArrayList<Integer>();

		// Instantiate a Sparse Matrix:
		value.add(1.0);
		value.add(2.0);
		value.add(3.0);
		value.add(4.0);
		value.add(5.0);
		value.add(6.0);
		value.add(7.0);
		value.add(8.0);
		value.add(9.0);
		value.add(10.0);
		value.add(11.0);
		value.add(12.0);
		rowPtr.add(0);
		rowPtr.add(3);
		rowPtr.add(6);
		rowPtr.add(9);
		rowPtr.add(10);
		rowPtr.add(12);
		colInd.add(0);
		colInd.add(1);
		colInd.add(4);
		colInd.add(0);
		colInd.add(1);
		colInd.add(2);
		colInd.add(1);
		colInd.add(2);
		colInd.add(4);
		colInd.add(3);
		colInd.add(0);
		colInd.add(4);
		SparseMatrix sp = new SparseMatrix(value,rowPtr,colInd);

		// Instantiate a Full Matrix:
		double[][] full_matrix = {{1,2,0,0,3},{4,5,6,0,0},{0,7,8,0,9},{0,0,0,10,0},{11,0,0,0,12}};
		FullMatrix fm = new FullMatrix(full_matrix);
		
		// Instantiate a Vector:
		double[] v = {5,4,3,2,1};
		Vector vec = new Vector(v);

		// Testing of Part 1
		System.out.println("The outputs for part 1:");
		
		permutation(sp,fm);

		scaling(sp,fm);
		
		product(sp,fm,vec);

	}

	public static void permutation(SparseMatrix sp, FullMatrix fm) {
		sp.rowPermute(0, 2);
		fm.rowPermute(0, 2);
		System.out.println("The second norm error for permuting a sparse "
				+ "matrix's row 1&3 is "+norm_matrix(sp, fm));
		sp.rowPermute(0, 4);
		fm.rowPermute(0, 4);
		System.out.println("The second norm error for permuting a sparse "
				+ "matrix's row 1&5 is "+norm_matrix(sp, fm));
	}

	public static void scaling(SparseMatrix sp, FullMatrix fm) {
		sp.rowScale(0, 3, 3.0);
		fm.rowScale(0, 3, 3.0);
		System.out.println("The second norm error for first time scaling "
				+ "a sparse matrix is "+norm_matrix(sp, fm));
		sp.rowScale(4, 1, -4.4);
		fm.rowScale(4, 1, -4.4);
		System.out.println("The second norm error for second time scaling "
				+ "a sparse matrix is "+norm_matrix(sp, fm));
	}

	public static void product(SparseMatrix sp, FullMatrix fm, Vector v) {
		Vector result1 = sp.product(v);
		Vector result2 = fm.product(v);
		System.out.println("The second norm error for multiplying "
				+ "a sparse matrix is "+norm_vector(result1, result2));
	}

	public static double norm_matrix(SparseMatrix sp, FullMatrix fm){
		double norm = 0;
		for(int i=0; i<fm.rank; i++) {

			for(int j=0; j<fm.rank; j++) {
				norm += Math.pow((sp.retrieveElement(i, j) - fm.full_m[i][j]), 2);
			}
		}
		return norm;
	}


	public static double norm_vector(Vector sp, Vector fm){
		double norm = 0;
		for(int i=0; i<sp.len; i++) {
			norm += Math.pow((sp.v[i]-fm.v[i]),2);
		}
		return norm;
	}


}
