import java.util.ArrayList;
import java.util.HashMap;

/**
 * directMatrixSolver.java, ECE4960-P3
 * Created by Yuan He(yh772) on 2018/04/05
 * Platform: Java 8, Eclipse, MacOS
 * Copyright © 2018 Yuan He. All rights reserved.
 * 
 * Project 3, direct matrix solver:
 * Solve x out of Ax - V = 0
 * In project 3, the Power Law problem could be transformed into, log(y) = log(c) + m * log(x), 
 * and be combined with this direct matrix solver to render solution for c and m without doing 
 * Newton iterations.
 */

public class directMatrixSolver {

	/** Function: From the the No. r row, find the row that has least non-zero elements, 
	 * 			  and return that row number, for further row permutation and sparsity arrangement
	 * 			  (r=0 when you want to sort the full array)
	 * Parameters: int[] counter, int r
	 * Return: int rowIndex*/
	public static int arrayMin(int[] counter, int r) {
		int min = counter[r];
		int index = r;
		// For this row and below, check which row has the least non-zero elements (more sparsed)
		for(int i=r; i<counter.length; i++) {
			if(counter[i]<=min && counter[i]!=0) {
				min = counter[i];
				index = i;
			}
		}
		return index;
	}

	/** Function: Return the pivot's column in a row in A (Choose the left one when tie occurs)
	 *  Parameters: FullMatrix A, int[] counter, int r
	 *  Return: int r*/
	public static int pivotMin(FullMatrix A, int[] counter, int r) {
		int min = counter[0];
		for(int i=0; i<counter.length; i++) {
			if(counter[i]<=min && A.full[r][i]!=0) {min = counter[i];}
		}
		for(int i=0; i<counter.length; i++) {
			if(counter[i]==min && A.full[r][i]!=0) {return i;}
		}
		return 0;
	}

	/** Function: Permute a row to the right place, to make matrix A having prior rows with more sparsity
	 * 			  The counter[] below is used to keep track of how many non-zero numbers there are in each row,
	 * 			  so that it's easier to decide how to bubble-up each row to have arranged with the correct sparsity
	 * 	Parameters: FullMatrix A, Vector V, int r
	 * 	Return: None*/
	public static void bubbleUp(FullMatrix A, Vector V,int r) {
		int counter[] = new int[A.rank];
		// From this row and below:
		for(int i=r; i<A.rank; i++) {
			for(int j=0; j<A.rank; j++) {
				if(A.full[i][j]!=0) {counter[i]+=1;}
			}
		}
		// Swap this row with the rows below that is more sparsed
		int min = arrayMin(counter, r);
		A.rowPermute(min, r);
		V.rowPermute(min, r);
		return;
	}

	/** Function: Check if row r in A has only 1 non-zero element
	 * 	Parameter: FullMatrix, int r
	 * 	Return: int index (iff row r only has 1 non-zero element, and return the column index of that element)
	 * 					  (iff row r has 0 or more-than-one non-zero element, return -1*/
	public static int soleEle(FullMatrix A, int r) {
		int counter=0;
		int index=0;
		for(int i=0; i<A.rank; i++) {
			if(A.full[r][i]!=0) {
				counter+=1;
				index=i;
			}
		}
		if(counter==1) {return index;}
		return -1;
	}

	/** Function: Search for the pivot in row r, and return its column index
	 * 	Parameters: FullMatrix A, int r
	 * 	Return: the column number of that pivot*/
	public static int pivot(FullMatrix A, int r) {
		// Iff row r has only 1 element, than pivot should be that 1 element
		if (soleEle(A,r)>=0) {
			return soleEle(A,r);
		}

		int[] counter = new int[A.rank];
		double common = 0;
		// Pivot is A[r][i] (traverse all potential pivots)
		for(int i=0; i<A.rank; i++) {
			// For row j:
			for(int j=r+1; j<A.rank; j++) {
				// Potential pivots operated with every row below
				if(A.full[r][i]!=0) {
					common = A.full[j][i]/A.full[r][i];
					for(int k=0; k<A.rank; k++) {
						if(k!=i && A.full[j][i]!=0) {
							// Fill-in if more non-zero elements created
							if(A.full[j][k] - common*A.full[r][k] != 0 && A.full[j][k]==0) {
								counter[i]+=1;
							}
							// Less fill-in if more zero elements created
							else if(A.full[j][k] - common*A.full[r][k] == 0 && A.full[j][k]!=0) {
								counter[i]-=1;
							}
						}
					}
				}
			}
		}
		// After the row for pivoting is chosen, find out the pivot in that row and return it.
		return pivotMin(A,counter,r);
	}

	/** Function: Cancel with one pivot in one row
	 * 	Parameters: FullMatrix A, Vector V, int r
	 * 	Return: None*/
	public static void cancel(FullMatrix A, Vector V,int r) {
		bubbleUp(A,V,r);
		int index = pivot(A,r);
		double common=0;
		// For each row below r, cancel them!
		for(int i=r+1; i<A.rank; i++) {
			common = - A.full[i][index]/A.full[r][index];
			A.rowScale(r, i, common);
			V.rowScale(r, i, common);
		}
		return;
	}


	/** Function: Cancel each row
	 * 	Parameters: FullMatrix A, Vector V
	 * 	Return: None*/
	public static void cancelAll(FullMatrix A, Vector V) {
		for(int i=0; i<A.rank; i++) {
			cancel(A,V,i);
		}
	}

	/** Function: Upper-Diagnalize the LU matrix, to provide convenience for back-subtraction
	 *  Parameters: FullMatrix A, Vector V
	 *  Return: None*/
	public static void Diagnal(FullMatrix A, Vector V) {
		// Firstly cancel every row
		cancelAll(A,V);

		// Check if the low-diagonal element is non-zero
		for(int i=0; i<A.rank; i++) {
			for(int j=i; j<A.rank; j++) {

				// Then permute the rows to make the matrix upper-diagonal
				if(A.full[j][i]!=0) {
					A.rowPermute(i, j);
					V.rowPermute(i, j);
					break;
				}
			}
		}
	}

	/**Function: Back substitution, from left to right (instead of from bottom to top to reduce computation)
	 * Parameters: FullMatrix A, Vector V, HashMap<Integer, Double> X
	 * Return: None*/
	public static void backSub(FullMatrix A, Vector V, HashMap<Integer, Double> X) {
		// For each X(i)
		for(int i=V.len-1; i>=0; i--) {
			X.put(i, V.v[i]/A.full[i][i]);
			// Need to back-subtract previous rows in the upper-diagonal matrix
			for(int j=i-1; j>=0; j--) {
				if(A.full[j][i]!=0) {
					V.v[j] = V.v[j] - X.get(i) * A.full[j][i];
					A.full[j][i]=0;
				}
			}
		}
	}


	/**Function: the core direct matrix solver function, to solve Ax - V = 0,
	 * 			 with the intuition on lecture note 6, page 6 and 7
	 * Parameters: int modelType*/
	public static Vector directSolver(int modelType){
		// Input the measured data for Power Law valiation:
		ArrayList<ArrayList<Double>> measure = iterativeSolver.powMeasure;

		// Change the model type into "modelType", to use the appropriate loss function
		iterativeSolver.MODEL = modelType;

		// Create the matrix A and Vector V with measured data
		double[][] AA = new double[2][2];
		double[] VV = new double[2];
		double logXi = 0;
		double logYi = 0;
		for(int i=0; i<measure.get(0).size(); i++) {
			logXi = Math.log(measure.get(0).get(i));
			logYi = Math.log(measure.get(1).get(i));
			AA[0][0] += Math.pow(logXi, 2);
			AA[1][0] += logXi;
			AA[0][1] += logXi;
			VV[0] += logXi * logYi;
			VV[1] += logYi;
		}
		AA[1][1] = measure.get(0).size();

		FullMatrix A =  new FullMatrix(AA);
		Vector V = new Vector(VV);

		// Create HashMap for storing the solution for matrix solver, and initialize it
		HashMap<Integer, Double> X=new HashMap<Integer, Double>();  
		for(int i=0; i<A.rank; i++) {X.put(i,0.0);}

		// Solve the matrix:
		Diagnal(A, V);
		backSub(A,V,X);

		// Returning X, with the first element being c, and second element being m
		double Xres[] = new double[2];
		Xres[0] = Math.pow(Math.E, X.get(1));
		Xres[1] = X.get(0);
		return new Vector(Xres);
	}
}
