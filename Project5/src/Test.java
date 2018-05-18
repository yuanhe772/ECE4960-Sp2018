
/**
 * Test.java, ECE4960-P5
 * Created by Yuan He(yh772) on 2018/05/18
 * Platform: Java 8, Eclipse, MacOS
 * Copyright © 2018 Yuan He. All rights reserved.
 * 
 * Testing the helper functions in thr progress of implementing this project.
 */
public class Test {

	// A known full matrix, and its equivalent sparse matrix
	static double full[][] = {{1,2,0,0,3},{4,5,6,0,0},{0,7,8,0,9},{0,0,0,10,0},{11,0,0,0,12}};
	static double value[] = {1,2,3,4,5,6,7,8,9,10,11,12};
	static int rowPtr[] = {0,3,6,9,10,12};
	static int colInd[] = {0,1,4,0,1,2,1,2,4,3,0,4};
	static SparseMatrix sp = new SparseMatrix(value,rowPtr,colInd) ;

	/**Function: Test SparseMatrix.retrieveElement()
	 * Parameter: None
	 * Return: None*/
	public static void retrieveTest() {
		// Output this section's title
		FileIO.output("\n\n\n  SparseMatrix.retrieveElement() Testing: ");

		// Retreive elements from a SparseMatrix and compare it with an identical FullMatrix
		boolean flag = true;
		int i,j = 0;
		for(i=0; i<sp.rank; i++) {
			for(j=0; j<sp.rank; j++) {
				// Compare the values one-by-one
				if(full[i][j]!=sp.retrieveElement(i, j)[1]) {
					flag = false;
					break;
				}
			}
		}

		// Time-stamp for measuring function's computational time
		FileIO.output("\n	"+ (flag ? "PASSED" : "FAILED") 
				+ ": Function's output correctness testing! "
				+ "By comparing if the retrieved elements of a SparseMatrix is identical to a known FullMatrix.");

	}

	/**Function: Test SparseMatrix.matrixSetter()
	 * @param: None
	 * @return: None*/
	public static void matrixSetterTest() {
		// Output this section's title
		FileIO.output("\n\n\n  SparseMatrix.matrixSetter() Testing: ");

		// Create an empty sparse matrix
		SparseMatrix sp2 = new SparseMatrix(sp.rank);
		for(int i=0; i<sp2.rank; i++) {
			for(int j=0; j<sp2.rank; j++) {sp2.matrixSetter(i, j, sp.retrieveElement(i, j)[1]);}
		}

		// Output test result
		FileIO.output("\n	"+ (compareFullSp(full, sp2) ? "PASSED" : "FAILED") + ": Function's output correctness testing! "
				+ "By comparing if the matrix created by matrixSetter() is identical to a known FullMatrix.");
	}

	/**Function: Test SparseMatrix.poduct()
	 * Parameter: None
	 * Return: None*/
	public static void productVecTest() {
		//Output this section's title
		FileIO.output("\n\n\n  SparseMatrix.product() Testing: ");

		// The calculated
		double[] b = {1,1,1,1,1};
		Vector B = new Vector(b);
		Vector res = sp.productVec(B);

		// The validation
		double[] validation = {6,15,24,10,23};
		Vector Validation = new Vector(validation);

		// Comparing
		boolean flag = true;
		for(int i=0; i<B.len; i++) {
			if(res.v[i] != Validation.v[i])
				flag = false;
		}

		// Output test result
		FileIO.output("\n	"+ (flag ? "PASSED" : "FAILED") + ": Function's output correctness testing! "
				+ "By comparing if the product of Matrix*Vector equals to the known ground truth.");

	}

	/**Functions: Tests the Jacobi Iterative Matrix solver
	 * Parameters: None
	 * Return: None*/
	public static void testJacobi() {
		//Output this section's title
		FileIO.output("\n\n\n  Jacobi.Solver() Testing: ");

		// The calculated
		double valueX[] = {-4,1,1,4,-4,1,1,-4,1,1,-4,1,1,1,-4};
		int rowPtrX[] = {0,3,6,9,12,15};
		int colIndX[] = {0,1,4,0,1,2,1,2,3,2,3,4,0,3,4};
		SparseMatrix A = new SparseMatrix(valueX, rowPtrX, colIndX);
		double b[] = {1,0,0,0,0};
		Vector B = new Vector(b);
		Vector res = Jacobi.solver(A, B);

		// The ground-truth
		double[] xTruth = {-0.3793103119831519, -0.4083483960392904, -0.11615241724631017, -0.056261322732868616, -0.1088929016802376};
		Vector XTruth = new Vector(xTruth);

		// Comparing
		boolean flag = true;
		for(int i=0; i<B.len; i++) {
			if(res.v[i] != XTruth.v[i])
				flag = false;
		}

		// Output
		FileIO.output("\n	"+ (flag ? "PASSED" : "FAILED") + ": Function's output correctness testing! "
				+ "By comparing if the calculated X is identical to a known ground truth.");
	}

	/**Function: Compare two types of matrixes, one is full, the other is sparse, return true on equivalence
	 * Parameter: double[][] full, SparseMatrix sp
	 * return : boolean*/
	public static boolean compareFullSp(double[][] full, SparseMatrix sp) {
		int i,j = 0;
		for(i=0; i<sp.rank; i++) {
			for(j=0; j<sp.rank; j++) {

				// Compare the values one-by-one
				if(full[i][j]!=sp.retrieveElement(i, j)[1])
					break;
			}
		}
		return i+j == 2*sp.rank;
	}
}
