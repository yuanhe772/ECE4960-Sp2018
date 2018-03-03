import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

// 课上的full matrix通过row permutation去找每一步的pivot
public class HP2 {

	// r=0 when you want to sort the full array
	/** Return the min's index in an array*/
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

	/** Return the pivot's column in a row in A*/
	public static int pivotMin(FullMatrix A, int[] counter, int r) {
		int min = counter[0];
		int index = 0;
		for(int i=0; i<counter.length; i++) {
			if(counter[i]<=min && A.full[r][i]!=0) {
				min = counter[i];
				index = i;
			}
		}
		return index;
	}

	/** Permute to have the first row with more sparsity*/
	public static void bubbleUp(FullMatrix A, Vector V,int r) {
		int counter[] = new int[A.rank];
		for(int i=r; i<A.rank; i++) {
			for(int j=0; j<A.rank; j++) {
				if(A.full[i][j]>0) {
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
		// pivot is A[r][i] (traverse all potential pivots)
		for(int i=0; i<A.rank; i++) {
			//for row j:
			for(int j=r+1; j<A.rank; j++) {
				//potential pivots operated with every row below
				// pivot is A[r][i]
				if(A.full[r][i]!=0) {
					common = A.full[j][i]/A.full[r][i];
					//The j1-th column
					for(int j1=0; j1<A.rank; j1++) {
						if(j1!=i && A.full[j][i]!=0) {
							if(A.full[j][j1] - common*A.full[r][j1] != 0 && A.full[j][j1]==0) {//多啦
								counter[i]+=1;
							}
							else if(A.full[j][j1] - common*A.full[r][j1] == 0 && A.full[j][j1]!=0) {//少啦
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

//		System.out.println("\n\nBefore bubbling up:\n");
//		for(int i=0; i<A.rank; i++) {
//			for(int j=0; j<A.rank; j++) {
//				System.out.println(A.full[i][j]);
//			}
//		}
//		for(int i=0; i<A.rank; i++) {
//			System.out.println("Vector = "+V.v[i]);
//		}

		bubbleUp(A,V,r);

//		System.out.println("\n\nBefore pivoting\n");
//		for(int i=0; i<A.rank; i++) {
//			for(int j=0; j<A.rank; j++) {
//				System.out.println(A.full[i][j]);
//			}
//		}
//		for(int i=0; i<A.rank; i++) {
//			System.out.println("Vector = "+V.v[i]);
//		}
//		System.out.print("\npivot = "+pivot(A,r)+"\n\n\n");
		
		
		int index = pivot(A,r);
		double common=0;
		//i is each row:
		for(int i=r+1; i<A.rank; i++) {
			common = - A.full[i][index]/A.full[r][index];
			A.rowScale(r, i, common);
			V.rowScale(r, i, common);
		}
		
		
//		for(int i=0; i<A.rank; i++) {
//			for(int j=0; j<A.rank; j++) {
//				System.out.println(A.full[i][j]);
//			}
//		}
//		for(int i=0; i<A.rank; i++) {
//			System.out.println("Vector = "+V.v[i]);
//		}

		return;
	}


	/** Cancel with all pivots*/
	public static void cancelAll(FullMatrix A, Vector V) {
		for(int i=0; i<A.rank; i++) {
			cancel(A,V,i);
		}
	}

	/** Check if this Matrix is now diagnal*/
	public static boolean diagnal(FullMatrix A) {
		int counter[]=new int[A.rank];
		for(int i=0; i<A.rank; i++) {
			for(int j=0; j<A.rank; j++) {
				if(A.full[i][j]!=0) {counter[i]+=1;}
				if(counter[i]>1) {return false; }
			}
		}
		int[] standard= new int[A.rank];
		for(int i=0; i<A.rank; i++) {
			standard[i]=1;
		}

		if(Arrays.equals(counter,standard)) {
			return true;
		}
		return false;
	} 

	/** normalize the diagnal matrix to get the answer for x*/
	public static void normal(FullMatrix A, Vector V, HashMap<Integer, Double> X) {
		for(int i=0; i<A.rank; i++) {
			for(int j=0; j<A.rank; j++) {
				if(A.full[i][j]!=0) {
					X.put(j, V.v[i]/A.full[i][j]);
				}
			}
		}
	}

	/**Check iff Ax-b==0 */
	public static boolean test(FullMatrix A, Vector V, Vector X) {
		return((A.product(X)).equals(V));
	}


	public static void main (String args[]) {

		double[][] AA = {{1,2,0},{0,5,5},{1,0,2}};
		double[] VV = {3,2,1};
		//		double[][] AA = {{1,2,0,0,3},{4,5,6,0,0},{0,7,8,0,9},{0,0,0,10,0},{11,0,0,0,12}};
		//		double[] VV = {5,4,3,2,1};
		FullMatrix A =  new FullMatrix(AA);
		Vector V = new Vector(VV);
		HashMap<Integer, Double> X=new HashMap<Integer, Double>();  
		for(int i=0; i<A.rank; i++) {
			X.put(i,0.0);
		}

		while(!diagnal(A)) {
			cancelAll(A,V);
		}
		
		normal(A,V,X);
		
//		for(int i=0; i<A.rank; i++) {
//			System.out.println(X.get(i));
//		}
		
		double x[] = new double[A.rank];
		for(int i=0; i<A.rank; i++) {
			x[i] = X.get(i);
//			System.out.println("\n"+x[i]);
		}
//		System.out.println("The matrix's answer is "+ test(A,V,new Vector(x)) == true ?"correct":"wrong"+" !");
		System.out.println(test(A,V,new Vector(x)));
	}
}

