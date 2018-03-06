/**
 * Vector.java, ECE4960-P2
 * Created by Yuan He(yh772) on 2018-03-04
 * Platform: Java 8, Eclipse, MacOS
 * Copyright © 2018 Yuan He. All rights reserved.
 */


public class Vector {

	// Data structures for keeping the Vector
	double[] v;
	int len;

	/** Constructor, pass in any one-dimensional array*/
	public Vector(double[] a) {
		// Update the data structures
		v = a.clone();
		len = v.length;
	}

	/** Constructor2, pass in any one-dimensional, length==2 array*/
	public Vector(double x, double y) {
		// Update the data structures
		double[] d = {x,y};
		v =d;
		len = 2;
	}

	/** Constructor3, Create a new vector by indicating its length*/
	public Vector(int len) {
		// Update the data structures
		v = new double[len];
		this.len = len;
	}

	/** Check iff this == V2, return true on equal, false on inequality */
	public boolean equals(Vector V2) {
		// First compare length
		if(V2.len != len) {return false;}

		//Then compare value
		for(int i=0; i<len; i++) {
			if(V2.v[i] != v[i]) {
				return false;
			}
		}
		return true;
	}

	/**Switch row[i] and row[j] for matrix A and Vector x. (Return 0 on success, 1 on failures)*/
	public int rowPermute(int i, int j) {
		try {
			// Keep function invariants true
			assertInd(i ,j);

			// Switch two rows(elements)
			double temp = v[i];
			v[i] = v[j];
			v[j] = temp;
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

			// Scale row[i](element[i])
			v[j] += v[i]*a;
			return 0;
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return 1;
		}
	}
	
	/**Return the sum of this vector and vector v*/
	public Vector add(Vector vec) {
		// Keep function invariants true
		assert vec.len == len;
		
		// Create data structure for storing the addition result
		Vector sum = new Vector(len);
		
		// Iteratively add two matrixes' (this matrix and matrix m) corresponding values into the sum
		for(int i=0; i<len; i++) {
			sum.v[i] = v[i] + vec.v[i];
		}
		return sum;
	}

	/**Keep function invariants true: 0 <= index < size*/
	public void assertInd(int i, int j) {
		assert i>=0 && i<len;
		assert j>=0 && j<len;
	}
}
