/** Last Update: 02/18/2018; Author: Yuan He (yh772); Platform: Eclipse, Java8 */

/** Implement the compressed matrix and verify that it's consistent with the
 * full matrix, by comparing it's product with vector {5,4,3,2,1} with that of
 * the full matrix.*/

public class HP3 {

	public static void productAx_compressed(HP2 A, int[] x, int[] b) {
		for(int i=0; i < 5; i++) {
			for(int j=0; j < 5; j++) {
				b[i] = b[i] + HP2.retrieveElement(i, j) * x[j]; 
			}
		}
	}

	public static void productAx_full(int[][] A, int[] x, int[] b) {
		for(int i=0; i < 5; i++) {
			for(int j=0; j < 5; j++) {
				b[i] = b[i] + A[i][j] * x[j]; 
			}
		}
	}

	public static void main(String args[]) {

		int[] x = {5,4,3,2,1};
		int[] b_compressed = new int[5];
		int[] b_full = new int[5];

		HP3.productAx_compressed(new HP2(), x, b_compressed);
		HP3.productAx_full(HP2.full_m, x, b_full);

		int flag = 0; // 0 means the compressed matrix == desired full matrix, 1 means inconsistent

		for(int i=0; i<5; i++) {
			System.out.println("Full matrix multiplying vector b = [5,4,3,2,1], the product's "+i+"th element is " + b_full[i]);
			System.out.println("Compressed matrix multiplying vector b = [5,4,3,2,1], the product's "+i+"th element is " + b_compressed[i]);			
			if(b_full[i] != b_compressed[i]) {
				flag = 1;
				break;
			}
		}

		System.out.println("\nTherefore, the compressed matrix's product with vector b = [5,4,3,2,1] "
				+ "is " + (flag == 0? "consistent" : "inconsistent") + " with the full matrix's product.");
	}
}
