/** Last Update: 02/25/2018; Author: Yuan He (yh772); Platform: MacOS, Eclipse, Java8 */


public class Vector {
	double[] v;
	int len;
	
	/**Constructor*/
	public Vector(double[] a) {
		v = a.clone();
		len = v.length;
		}
	
	/**Constructor*/
	public Vector(double x, double y) {
		double[] d = {x,y};
		v =d;
		len = 2;
		}
	
	public boolean equals(Vector V2) {
		if(V2.len != len) {return false;}
		for(int i=0; i<len; i++) {
			if(V2.v[i] != v[i]) {
				return false;
			}
		}
		return true;
	}
	
	/**Switch row[i] and row[j] for matrix A and Vector x*/
	public void rowPermute(int i, int j) {
		if(i<0 || j<0 || i>=len || j>=len) return;
		double temp = v[i];
		v[i] = v[j];
		v[j] = temp;
		return;
	}
	
	/**Add a*row[i] to row[j] for Matrix A and Vector x*/
	public void rowScale(int i, int j, double a) {
		if(i<0 || j<0 || i>=len || j>=len) return;
		v[j] += v[i]*a;
		return;
	}
}
