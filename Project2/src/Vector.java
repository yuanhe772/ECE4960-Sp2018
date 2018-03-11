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

	/**Function: Constructor, construct a Vector by passing in an array
	 * Parameters: double[] a*/
	public Vector(double[] a) {
		// Update the data structures
		v = a.clone();
		len = v.length;
	}

	/**Function: Constructor2, construct an empty Vector by indicating its length
	 * Parameters: */
	public Vector(int len) {
		// Update the data structures
		v = new double[len];
		this.len = len;
	}

	/**Function: vectorA + d*VectorB
	 * Parameters: Vector B, double d
	 * Return: Vector sum*/
	public Vector add(Vector B, double d) {
		// Keep function invariants true
		assert B.len == len: "Unmatched matrix sizes!";

		// Create data structure for storing the adding result
		Vector sum = new Vector(len);

		// Iteratively add two vectors' corresponding values into the sum
		for(int i=0; i<len; i++) {
			sum.v[i] = v[i] + d*B.v[i];
		}
		return sum;
	}

	/**Keep function invariants true: 0 <= index < size*/
	public void assertInd(int i, int j) {
		assert i>=0 && i<len;
		assert j>=0 && j<len;
	}
}
