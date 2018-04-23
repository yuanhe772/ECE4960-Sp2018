
/**
 * NewDirectMatrixSolver.java, ECE4960-HW11
 * Created by Yuan He(yh772) on 2018/04/22
 * Platform: Java 8, Eclipse, MacOS
 * Copyright © 2018 Yuan He. All rights reserved.
 */
public class NewDirectMatrixSolver {
	//Get pivot
	public static int minFillIn(FullMatrix A) {
		//The initial value of pivot is 0
		int pivot= 0;
		int fillIn=2147483647;
		//Check different pivots using row permutation
		for(int i=0; i< A.rank; i++) {
			//If the first element of this row is 0, it cannot be the pivot
			if(A.full[i][0]==0) continue;
			int count=0;
			for(int j=0; j<A.rank;j++) {
				//If the first element of this row is 0, the sparse structure of this row will not change during LU decomposition 
				if(A.full[j][0]==0) continue;
				for(int k=1; k<A.rank; k++) {
					if(A.full[j][k]==0&&A.full[i][k]!=0) count++;
				}
			}
			//Choose the pivot causing minimal fill_ins
			if(count<fillIn) {
				fillIn= count;
				pivot=i;
			} 
		}
		
		return pivot;
	}
	//LU decomposition
	public static FullMatrix LUDecomposition(FullMatrix A, int level) {
		double[][] aValue= A.full;
		//Initialize the matrix M as a unit diagonal matrix
		double[][] mValue= new double[aValue.length][aValue[0].length];
		for(int i=0; i<mValue.length; i++) {
			for(int j=0; j<mValue[0].length; j++) {
				if(j==i) mValue[i][j]=1;
				else mValue[i][j]=0;
			}
		}
		for(int i=level; i<mValue.length; i++) {
			mValue[i][level-1]= -aValue[i][level-1]/aValue[level-1][level-1];
		}
		FullMatrix m= new FullMatrix(mValue);
		return m;
	}
	//Backward substitution
	public static Vector backwardSubstitution(FullMatrix a, Vector b) {
		double[] xValue= new double[b.len];
		double[] bValue= b.v;
		double[][] aValue= a.full;
		for(int i= a.rank-1; i>=0; i--) {
			xValue[i]= bValue[i]/aValue[i][i];
			for(int j=i-1; j>=0; j--) {
				bValue[j]= bValue[j]- xValue[i]*aValue[j][i];
			}
		}
		return new Vector(xValue);
	}
	
	// Integrate all the steps above to sole the equation
	public static Vector solve(FullMatrix a, Vector b) {
		int pivot= minFillIn(a);
		a.rowPermute(pivot+1, 1);
		b.rowPermute(pivot+1, 1);
		//U=M4*M3*M2*M1*A
		FullMatrix u=a;
		Vector b2=b;
		for(int i=1; i<a.rank; i++) {
			FullMatrix mi= LUDecomposition(u, i);
			u= mi.productAB(u);
			b2= mi.product(b2);
		}
		Vector x= backwardSubstitution(u, b2);
		a.rowPermute(pivot+1, 1);
		b.rowPermute(pivot+1, 1);
		return x;
	}

}