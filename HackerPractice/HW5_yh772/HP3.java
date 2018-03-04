
/**
 * @author Yuan He(yh772)
 * Last Update: 2018-03-03
 * Platform: Java 8, Eclipse, MacOS
 * 
 * Hacker Practice Purpose:
 * Calculate the upper bounds of ||A||1 and ||A||∞ in the full-matrix and 
 * sparse-matrix formats.
 */
import java.util.ArrayList;


public class HP3 {

	/**Calculate the upper bounds for full matrix fm, with Option = "1" for Maximum 
	 * column vector sum, and Option = "INF" for Maximum row vector sum*/
	public static double FullUpper(FullMatrix fm, String Option) {
		double max=0;
		double Sum=0;
		for(int i=0; i<fm.rank; i++) {
			Sum=0;
			for(int j=0; j<fm.rank; j++) {
				if(Option.equals("1")) Sum += fm.full[j][i];
				else Sum += fm.full[i][j];
			}
			max = (Sum > max) ? Sum : max;
		}
		System.out.println("Upperbound of " + (Option.equals("1")? "||A|| 1":"||A|| INF") +
				" (Maximum column vector sum) is " + max+".");
		return max;
	}


	/**Calculate the upper bounds for sparse matrix sp, with Option = "1" for Maximum 
	 * column vector sum, and Option = "INF" for Maximum row vector sum*/
	public static double SparseUpper(SparseMatrix sp, String Option) {
		double max=0;
		double Sum=0;
		for(int i=0; i<sp.rank; i++) {
			Sum=0;
			for(int j=0; j<sp.rank; j++) {
				if(Option.equals("1")) Sum += sp.retrieveElement(j,i);
				else Sum += sp.retrieveElement(i,j);
			}
			max = (Sum > max) ? Sum : max;
		}
		System.out.println("Upperbound of " + (Option.equals("1")? "||A|| 1":"||A|| INF") + 
				" (Maximum row vector sum) is " + max+".");
		return max;
	}


	public static void main(String args[]) {
		/** Instantiate a Full Matrix:*/
		double[][] full_matrix = {{1,2,0,0,3},{4,5,6,0,0},{0,7,8,0,9},{0,0,0,10,0},{11,0,0,0,12}};
		FullMatrix fm = new FullMatrix(full_matrix);

		/** Instantiate a Sparse Matrix:*/
		ArrayList<Double> value = new ArrayList<Double>();
		ArrayList<Integer> rowPtr = new ArrayList<Integer>();
		ArrayList<Integer> colInd = new ArrayList<Integer>();
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
		
		FullUpper(fm, "1");
		FullUpper(fm, "INF");
		SparseUpper(sp, "1");
		SparseUpper(sp, "INF");
		
	}

}
