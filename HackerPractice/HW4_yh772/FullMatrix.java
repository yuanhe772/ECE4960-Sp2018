/** Last Update: 02/25/2018; Author: Yuan He (yh772); Platform: Eclipse, Java8 */



public class FullMatrix {

	double[][] full_m;
	int rank;
	
	public FullMatrix(SparseMatrix sp) {
		rank = sp.rowPtr.size() - 1;
		full_m = new double[rank][rank];
		for(int i=0; i<rank; i++) {
			for(int j = 0; j<rank; j++) {
				full_m[i][j] = sp.retrieveElement(i,j);
			}
		}
	}
	
	public FullMatrix(double[][] m) {
		full_m = m.clone();
		rank = m.length;
	}
	
	/**Switch row[i] and row[j] for matrix A and Vector x*/
	public void rowPermute(int i, int j) {
		double temp = 0;
		for(int it=0; it<rank; it++) {
			temp = full_m[i][it];
			full_m[i][it] = full_m[j][it];
			full_m[j][it] = temp;
		}
		return;
	}
	
	/**Add a*row[i] to row[j] for Matrix A and Vector x*/
	public void rowScale(int i, int j, double a) {
		for(int it=0; it<rank; it++) {
			full_m[j][it] += a*full_m[i][it];
		}
		return;
	}
	
	
	/**Return the product of Ax = b*/
	public Vector product(Vector vec) {
		if(vec.len != rank) 
			throw new ArithmeticException ("Matrix*Vector unmatching size error: rank != length");
		double[] r = new double[vec.len];
		for(int i=0; i<rank; i++) {
			for(int j=0; j<rank; j++) {
				r[i] += full_m[i][j]*vec.v[j];
			}
		}		
		return new Vector(r);
	}
	
}
