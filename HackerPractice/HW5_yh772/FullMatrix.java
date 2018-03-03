/** Last Update: 02/27/2018; Author: Yuan He (yh772); Platform: MacOS, Eclipse, Java8 */



public class FullMatrix {


		double[][] full;
		int rank;


		/**Constructor*/
		public FullMatrix(double[][] m) {
			full = m.clone();
			rank = m.length;
		}

		
		/**Switch row[i] and row[j] for matrix A and Vector x*/
		public int rowPermute(int i, int j) {
			try {
				double temp = 0;
				for(int it=0; it<rank; it++) {
					temp = full[i][it];
					full[i][it] = full[j][it];
					full[j][it] = temp;
				}
				return 0;
			}catch(Exception e) {
				return 1;
			}
		}

		/**Add a*row[i] to row[j] for Matrix A and Vector x*/
		public int rowScale(int i, int j, double a) {
			
			try {
				for(int it=0; it<rank; it++) {
					full[j][it] += a*full[i][it];
				}
				return 0;
			}catch(Exception e) {
				return 1;
			}
		}

		/**Return the product of Ax = b*/
		public Vector product(Vector vec) {
			if(vec.len != rank) 
				throw new ArithmeticException ("Matrix*Vector unmatching size error: rank != length");
			double[] r = new double[vec.len];
			for(int i=0; i<rank; i++) {
				for(int j=0; j<rank; j++) {
					r[i] += full[i][j]*vec.v[j];
				}
			}		
			return new Vector(r);
		}
	
}
