
/**
 * HP2.java, ECE4960-HW13
 * Created by Yuan He(yh772) on 2018/05/03
 * Platform: Java 8, Eclipse, MacOS
 * Copyright © 2018 Yuan He. All rights reserved.
 * 
 * Note 7(2), page 25
 */
public class HP2 {
	
	

	
	// Matrix A:
	double[] valueA = {-2,1, 1,-2,1, 1,-2,1, 1,-2,1, 1,-2,1, 1,-2,1, 1,-2,1, 1,-2,1, 1,-2};
	int[] rowPtrA = {0, 2, 5, 8, 11, 14, 17, 20, 23, 25};
	int[] colIndA = {0, 1, 0, 1, 2, 1, 2, 3, 2,3,4,3,4,5,4,5,6,5,6,7,6,7,8,8,9};
	SparseMatrix A = new SparseMatrix(valueA, rowPtrA, colIndA);
	
	
}
