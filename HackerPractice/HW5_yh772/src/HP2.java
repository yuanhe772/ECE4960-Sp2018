
/**
 * @author Yuan He(yh772)
 * Last Update: 2018-02-27
 * Platform: Java 8, Eclipse, MacOS
 * 
 * Hacker Practice Purpose:
 * Use the full matrix format and only row permutation (easier to implement in 
 * a short time, and a good check for your sparse matrix later on), perform the 
 * minimal fill-in algorithm for choosing the sequence of pivoting to solve:
 */
import java.util.HashMap;

public class HP2 {

	/** Return the min's index in an array, r=0 when you want to sort the full array*/
	public static int arrayMin(int[] counter, int r) {
		int min = counter[r];
		int index = r;
		for(int i=r; i<counter.length; i++) {
			if(counter[i]<=min && counter[i]!=0) {
				min = counter[i];
				index = i;
			}
		}
		return index;
	}

	/** Return the pivot's column in a row in A (Choose the left ones when tie occurs)*/
	public static int pivotMin(FullMatrix A, int[] counter, int r) {
		int min = counter[0];
		for(int i=0; i<counter.length; i++) {
			if(counter[i]<=min && A.full[r][i]!=0) {
				min = counter[i];
			}
		}
		for(int i=0; i<counter.length; i++) {
			if(counter[i]==min && A.full[r][i]!=0) {
				return i;
			}
		}
		return 0;
	}

	/** Permute to have the first row with more sparsity*/
	public static void bubbleUp(FullMatrix A, Vector V,int r) {
		int counter[] = new int[A.rank];
		for(int i=r; i<A.rank; i++) {
			for(int j=0; j<A.rank; j++) {
				if(A.full[i][j]!=0) {
					counter[i]+=1;
				}
			}
		}
		int min = arrayMin(counter, r);
		A.rowPermute(min, r);
		V.rowPermute(min, r);
		return;
	}

	/** Check if row r in A has only 1 element*/
	public static int soleEle(FullMatrix A, int r) {
		int counter=0;
		int index=0;
		for(int i=0; i<A.rank; i++) {
			if(A.full[r][i]!=0) {
				counter+=1;
				index=i;
			}
		}
		if(counter==1) {
			return index;
		}
		return -1;
	}

	/** Look for the pivot in A, and return its col index*/
	public static int pivot(FullMatrix A, int r) {
		if (soleEle(A,r)>=0) {
			return soleEle(A,r);
		}
		int[] counter = new int[A.rank];
		double common = 0;
		for(int i=0; i<A.rank; i++) {// pivot is A[r][i] (traverse all potential pivots)
			for(int j=r+1; j<A.rank; j++) {//for row j:
				if(A.full[r][i]!=0) {//potential pivots operated with every row below
					common = A.full[j][i]/A.full[r][i];
					for(int j1=0; j1<A.rank; j1++) {//The j1-th column
						if(j1!=i && A.full[j][i]!=0) {
							if(A.full[j][j1] - common*A.full[r][j1] != 0 && A.full[j][j1]==0) {//Fill-in!
								counter[i]+=1;
							}
							else if(A.full[j][j1] - common*A.full[r][j1] == 0 && A.full[j][j1]!=0) {//Less fill-in!
								counter[i]-=1;
							}
						}
					}
				}
			}
		}
		return pivotMin(A,counter,r);
	}

	/** Cancel with one pivot*/
	public static void cancel(FullMatrix A, Vector V,int r) {
		bubbleUp(A,V,r);
		int index = pivot(A,r);
		double common=0;
		for(int i=r+1; i<A.rank; i++) {//i is each row:
			common = - A.full[i][index]/A.full[r][index];
			A.rowScale(r, i, common);
			V.rowScale(r, i, common);
		}
		return;
	}


	/** Cancel with all pivots*/
	public static void cancelAll(FullMatrix A, Vector V) {
		for(int i=0; i<A.rank; i++) {
			cancel(A,V,i);
		}
	}

	/** Diagnalize the LU matrix*/
	public static void Diagnal(FullMatrix A, Vector V) {
		cancelAll(A,V);
		for(int i=0; i<A.rank; i++) {
			for(int j=i; j<A.rank; j++) {
				if(A.full[j][i]!=0) {
					A.rowPermute(i, j);
					V.rowPermute(i, j);
					break;
				}
			}
		}
	}

	/**Back substitution, from left to right*/
	public static void backSub(FullMatrix A, Vector V, HashMap<Integer, Double> X) {
		for(int i=4; i>=0; i--) {//For each X(i)
			X.put(i, V.v[i]/A.full[i][i]);
			for(int j=i-1; j>=0; j--) {//For previous rows
				if(A.full[j][i]!=0) {
					V.v[j] = V.v[j] - X.get(i) * A.full[j][i];
					A.full[j][i]=0;
				}
			}
		}
		System.out.println("\nThe X computed from the matrix would be:");
		for(int i=0; i<A.rank; i++) {
			System.out.println("X("+i+") = "+X.get(i));
		}
	}


	/**Check iff Ax-b==0 */
	public static void error(HashMap<Integer, Double> X) {
		double[][] AA = {{1,2,0,0,3},{4,5,6,0,0},{0,7,8,0,9},{0,0,0,10,0},{11,0,0,0,12}};
		double[] BB = {5,4,3,2,1};
		FullMatrix A = new FullMatrix(AA);
		Vector B = new Vector(BB);
		double error = 0;
		double x[] = new double[A.rank];
		for(int i=0; i<A.rank; i++) {
			x[i] = X.get(i);
		}
		Vector XX = new Vector(x);
		Vector P = A.product(XX);
		System.out.println("\nA multipying with computed X:");
		for(int i=0; i<A.rank; i++) {
			System.out.println("b("+i+") = "+P.v[i]);
			error += Math.pow(P.v[i] - B.v[i], 2);	
		}
		error = Math.pow(error, 0.5);
		System.out.println("\nThe second norm error of (Ax-b) for computing this function group is:\n"+error);
	}


	/**Main function*/
	public static void main (String args[]) {

		//Initializing:
		//		double[][] AA = {{1,2,0},{0,5,4},{1,0,2}};
		//		double[] VV = {3,2,1};
		double[][] AA = {{1,2,0,0,3},{4,5,6,0,0},{0,7,8,0,9},{0,0,0,10,0},{11,0,0,0,12}};
		double[] VV = {5,4,3,2,1};
		FullMatrix A =  new FullMatrix(AA);
		Vector V = new Vector(VV);
		HashMap<Integer, Double> X=new HashMap<Integer, Double>();  
		for(int i=0; i<A.rank; i++) {
			X.put(i,0.0);
		}


		//Solving the function group:
		Diagnal(A, V);
		backSub(A,V,X);
		

		//Check the solution with Ax-b =?= 0
		error(X);

	}
}

