/**
 * Vector.java, ECE4960-P2
 * Created by Yuan He(yh772) on 2018-03-04
 * Platform: Java 8, Eclipse, MacOS
 * Copyright © 2018 Yuan He. All rights reserved.
 * 
 * type Vector:
 * Constructs type Vector, and implement Vector operations.
 */

public class Vector {

	/* Class variants: Data structures for keeping the Vector */
	double[] v;
	int len;


	/* Class methods:*/
	/**Function: Constructor, construct a Vector with an array
	 * @param: double[] a
	 * @return : None*/
	public Vector(double[] a) {
		v = a.clone();
		len = v.length;
	}

	/**Function: Constructor2, construct an empty Vector by only indicating its length
	 * @param: int length
	 * @return: None*/
	public Vector(int len) {
		v = new double[len];
		this.len = len;
	}

	/**Function: Compare two Vectors element to element to see if they are equivalent to each other
	 * @param: this.Vector, Vector B
	 * @return: int indicator, with 0 representing in-equivalent and 1 representing equivalent*/
	public int equals(Vector B) {
		for(int i=0; i<len; i++) {
			if(v[i] != B.v[i])
				return 0;
		}
		return 1;
	}
	
	/**Function: Switch this vector's row[i] and row[j]
	 * @param: row index, int i and int j
	 * @return: 0 on success, 1 on errors and exceptions*/
	public int rowPermute(int i, int j) {
		try {
			// Keep function invariants true
			assertInd(i,j);

			// Exchange two rows
			double temp = v[i];
			v[i] = v[j];
			v[j] = temp;
			return 0;
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return 1;
		}
	}
	
	/**Function: Add a*row[i] to row[j] for Matrix A and Vector x
	 * @param: row index, int i and int j; scalar, double a
	 * @return: 0 on success, 1 on errors and exceptions*/
	public int rowScale(int i, int j, double a) {
		try {
			// Keep function invariants true
			assertInd(i,j);

			// Scale up each row
			v[j] += v[i]*a;
			return 0;
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return 1;
		}
	}

	/**Function: vectorA + d*VectorB
	 * @param: Vector B, double d
	 * @return: Vector sum*/
	public Vector add(Vector B, double d) {
		// Keep function invariants true
		assert B.len == len: "Unmatched matrix sizes!";

		// Iteratively add two vectors' corresponding values into the sum
		Vector sum = new Vector(len);
		for(int i=0; i<len; i++) {sum.v[i] = v[i] + d*B.v[i];}
		return sum;
	}

	/**Function: this.Vector * t
	 * @param: double t (the scaler)
	 * @return: new Vector res*/
	public Vector scale(double t) {
		double res[] = new double[len];
		for(int i=0; i<len; i++) {
			res[i] = (double)v[i]*(double)t;
		}
		return new Vector(res);
	}
	
	/**Function: Calculate the norm of this.Vector
	 * @param: None
	 * @return: double*/
	public double norm() {
		double sum = 0;
		for(int i=0; i<len; i++) {
			sum += v[i] * v[i];
		}
		return Math.pow(sum, 0.5);
	}

	/**Function: Keep function invariants true: 0 <= index < size
	 * @param: int i, int j
	 * @return: None*/
	public void assertInd(int i, int j) {
		assert i>=0 && i<len;
		assert j>=0 && j<len;
	}
}
