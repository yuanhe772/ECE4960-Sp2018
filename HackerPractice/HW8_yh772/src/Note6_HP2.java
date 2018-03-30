import java.util.ArrayList;

/**
 * Lec_HP2.java, ECE4960-HW7
 * Created by Yuan He(yh772) on 2018/03/22
 * Platform: Java 8, Eclipse, MacOS
 * Copyright © 2018 Yuan He. All rights reserved.
 * 
 * Note6, p22:
 * Construct a B-Spline curve with the following anchor points.
 */
public class Note6_HP2 {

	static double xx[] = {1.0, 2.0, 3.0, 4.0, 5.0, 6.0};
	static double yy[] = {0.0, 1.0, 0.0, -1.0, 0.0, 1.0};	

	// NurbsSolver
	public static int nurbsSolver() {
		Vector x= new Vector(xx);
		Vector y= new Vector(yy);
		SparseMatrix coeffMatrix= getCoefficientsMatrix(x, y);
		Vector coeffVector= getCoefficientsVector(x, y);
		return Jacobi.solver(coeffMatrix, coeffVector);
	}

	// Create the "Ax = b"'s "A"
	public static SparseMatrix getCoefficientsMatrix(Vector x, Vector y) {
		ArrayList<Double> value= new ArrayList<Double>();
		ArrayList<Integer> rowPtr= new ArrayList<Integer>();
		ArrayList<Integer> colInd= new ArrayList<Integer>();
		int rowNum= x.len;
		rowPtr.add(0);
		for(int i=0; i<rowNum; i++) {
			if(i==0) {
				double a1= -4/(xx[1]-xx[0]);
				double a2= -2/(xx[1]-xx[0]);
				value.add(a1);
				value.add(a2);
				rowPtr.add(2);
				colInd.add(0);
				colInd.add(1);
				continue;
			}
			else if(i==rowNum-1) {
				double a1= 4/(xx[i]-xx[i-1]);
				double a2= 2/(xx[i]-xx[i-1]);
				value.add(a1);
				value.add(a2);
				rowPtr.add(rowPtr.get(i)+2);
				colInd.add(i-1);
				colInd.add(i);
				continue;
			}
			double a1= 1/(xx[i]-xx[i-1]);
			double a2= 2*(1/(xx[i]-xx[i-1])+1/(xx[i+1]-xx[i]));
			double a3= 1/(xx[i+1]-xx[i]);
			value.add(a1);
			value.add(a2);
			value.add(a3);
			rowPtr.add(rowPtr.get(i)+3);
			colInd.add(i-1);
			colInd.add(i);
			colInd.add(i+1);
		}
		return new SparseMatrix(value, rowPtr, colInd);
	}

	// Create the "Ax = b"'s "b"
	public static Vector getCoefficientsVector(Vector x, Vector y) {
		int len= x.len;
		double[] value= new double[len];
		for(int i=0; i<len; i++) {
			if (i==0) {
				value[i]= -6*(yy[1]-yy[0])/Math.pow((xx[1]-xx[0]), 2);
				continue;
			}
			if (i==len-1) {
				value[i]= 6*(yy[i]-yy[i-1])/Math.pow((xx[i]-xx[i-1]), 2);
				continue;
			} 
			else {
				double term1= (yy[i]-yy[i-1])/Math.pow((xx[i]-xx[i-1]), 2);
				double term2= (yy[i+1]-yy[i])/Math.pow((xx[i+1]-xx[i]), 2);
				value[i]= 3*(term1+term2);
			}
		}
		return new Vector(value);
	}

	public static void main(String args[]) {
		nurbsSolver();
	}

}



