/** Last Update: 02/25/2018; Author: Yuan He (yh772); Platform: Eclipse, Java8 */



public class Vector {

	double[] v;
	int len;
	
	
	public Vector(double[] a) {
		v = a.clone();
		len = v.length;
		}
	
	
	/**Switch row[i] and row[j] for matrix A and Vector x*/
	public void rowPermute(int i, int j) {
		if(i<1 || j<1 || i>=len || j>=len) return;
		double temp = v[i];
		v[i] = v[j];
		v[j] = temp;
		return;
	}
	
	
	/**Add a*row[i] to row[j] for Matrix A and Vector x*/
	public void rowScale(int i, int j, double a) {
		if(i<1 || j<1 || i>=len || j>=len) return;
		v[j] += v[i]*a;
		return;
	}
	
	
}
