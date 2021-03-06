import java.io.*;
import java.util.ArrayList;

/**
 * Test.java, ECE4960-P2
 * Created by Yuan He(yh772) on 2018/03/06
 * Platform: Java 8, Eclipse, MacOS
 * Copyright © 2018 Yuan He. All rights reserved.
 */


public class Test {

	/* Class variants: The testing inputs */
	// The "mat1" SparseMatrix
	static String filePath1[] = {"value.csv", "rowPtr.csv", "colInd.csv"};
	static final SparseMatrix mat1 = Test.readMatrix(filePath1);

	// An all-1 SparseMatrix, with rank of 500 (input files generated by Python)
	static String filePath2[] = {"value1.csv", "rowPtr1.csv", "colInd1.csv"};
	static final SparseMatrix All1 = Test.readMatrix(filePath2);

	// An all-1 diagonal SparseMatrix, with rank of 5000 (input files generated by Python)
	static String filePath3[] = {"value2.csv", "rowPtr2.csv", "colInd2.csv"};
	static final SparseMatrix dia1 = Test.readMatrix(filePath3);

	// An all-2 diagonal SparseMatrix, with rank of 5000 (input files generated by Python)
	static String filePath4[] = {"value3.csv", "rowPtr2.csv", "colInd2.csv"};
	static final SparseMatrix dia2 = Test.readMatrix(filePath4);

	// A known full matrix, and its equivalent sparse matrix
	static double full[][] = {{1,2,0,0,3},{4,5,6,0,0},{0,7,8,0,9},{0,0,0,10,0},{11,0,0,0,12}};
	static double value[] = {1,2,3,4,5,6,7,8,9,10,11,12};
	static int rowPtr[] = {0,3,6,9,10,12};
	static int colInd[] = {0,1,4,0,1,2,1,2,4,3,0,4};
	static SparseMatrix sp = new SparseMatrix(value,rowPtr,colInd) ;

	// The output report's path
	static String reportPath = null;

	// For measuring memory usage and computational time
	static Runtime runtime = Runtime.getRuntime();
	static long start, end;


	/* Class functions: The testing functions */
	/**Function: Test SparseMatrix.retrieveElement()
	 * Parameter: None
	 * Return: None*/
	public static void retrieveTest() {
		// Output this section's title
		output("\n\n\n  SparseMatrix.retrieveElement() Testing, GROUND-TRUTH KNOWN: ");

		/* Verify the function's INPUT invariant:
		 * Check: whether an error is thrown to terminate the program, when input violates
		 * the function's invariants of "input element's index(i,j) should be in-bound" */
		verifyInput("retrieveTest");

		/* Verify the function's OUTPUT correctness with Wilkinson Principal, knowing the ground 
		 * truth of two equivalent full and sparse matrixes, and compare the retrieving output between them*/ 

		// Time-stamp for measuring function's computational time
		start = System.nanoTime();

		output("\n	"+ (compareFullSp(full, sp) ? "PASSED" : "FAILED") 
				+ ": Function's output correctness testing! "
				+ "By comparing the retrieved elements with SparseMatrix's and fullMatrix's retrieving methods");
		// Output the computational costs
		end = System.nanoTime();
		computation("3 * n * n^0.5", 375, end - start);
	}

	/**Function: Test SparseMatrix.matrixSetter()
	 * Parameter: None
	 * Return: None*/
	public static void matrixSetterTest() {
		// Output this section's title
		output("\n\n\n  SparseMatrix.matrixSetter() Testing, GROUND-TRUTH KNOWN: ");

		/* Verify the function's INPUT invariant:
		 * Check: whether an error is thrown to terminate the program, when input violates
		 * the function's invariants of "input element's index(i,j) in-bound of matrix rank" */
		verifyInput("matrixSetterTest");

		/* Verify the function's OUTPUT correctness, knowing the ground truth*/
		/* Check1: set SparseMatrix sp's elements to an empty SparseMatrix "sp2" from scratch, 
		 * and compare it with an equivalent known full matrix "full", considering that 
		 * SparseMatrix.retrieveElement() passed the test*/ 

		// Time-stamp for measuring function's computational time
		start = System.nanoTime();

		// Create an empty sparse matrix
		SparseMatrix sp2 = new SparseMatrix(sp.rank);
		for(int i=0; i<sp2.rank; i++) {
			for(int j=0; j<sp2.rank; j++) {sp2.matrixSetter(i, j, sp.retrieveElement(i, j)[1]);}
		}

		// Output test result
		output("\n	"+ (compareFullSp(full, sp2) ? "PASSED" : "FAILED") + ": Function's output correctness testing! "
				+ "By comparing the matrix created by matrixSetter() from another sparse matrix, and an equivalent full matrix");

		/* Check2: change values in this newly created sparse matrix and the full matrix
		 * then check if they equal each other*/

		// change several zero elements into non-zero elements
		sp2.matrixSetter(0, 2, 10);
		full[0][2] = 10;
		sp2.matrixSetter(1, 3, 20);
		full[1][3] = 10;
		sp2.matrixSetter(2, 0, 30);
		full[2][0] = 10;

		// change several non-zero elements into zero elements
		sp2.matrixSetter(0, 0, 0);
		full[0][2] = 0;
		sp2.matrixSetter(1, 0, 0);
		full[1][3] = 0;
		sp2.matrixSetter(4, 0, 0);
		full[2][0] = 0;

		// Check sparse matrix's data structure - no zeroes in value[], value.len = colInd.len
		// Because retrieving the correct value does not guarantee a correct data structure 
		output("\n	"+ (!sp2.value.contains(0) && sp2.value.size()==sp2.colInd.size() ? "PASSED" : "FAILED") + 
				": Function's output correctness testing! The modified sparse martrix satisfies"
				+ " SparseMatrix's data structure invariants");

		// Check sparse matrix value's correctness
		output("\n	"+ (compareFullSp(full, sp2) ? "PASSED" : "FAILED") + ": Function's output correctness testing! "
				+ "The sparse matrix modified by matrixSetter() and the full matrix modified with 2D-array operation"
				+ " have same values for each element");

		// Output the computational costs
		end = System.nanoTime();
		computation("n^2.5 + 9 * n^2 + 6 * n^1.5 + 13 * n + 16 * n^0.5", 275567, end - start);
	}

	/**Function: Test SparseMatrix.poduct()
	 * Parameter: None
	 * Return: None*/
	public static void productTest() {
		//Output this section's title
		output("\n\n\n  SparseMatrix.product() Testing, GROUND-TRUTH UNKNOWN: ");

		/* Verify the function's INPUT invariant:
		 * Check: whether an error is thrown to terminate the program, when input violates
		 * the function's invariants of "SparseMatrix's rank == Vector's length" */
		verifyInput("productTest");

		/* Verify the function's OUTPUT correctness, without knowing the ground truth*/ 
		/* Check 1: when Vector is an all-1, check whether |A*X1 - X2| == 0, 
		 * where Vector x2 has each element being the sum of A's row*/ 

		// Time-stamp for measuring function's computational time
		start = System.nanoTime();

		// Sum up each row of the mat1
		Vector rowSum = new Vector(mat1.rank);
		for(int i=0; i<mat1.rank; i++) {
			for(int j=0; j<mat1.rank; j++) {rowSum.v[i] += mat1.retrieveElement(i, j)[1];}
		}

		// Create a Vector with its values being all 1
		Vector all1 = new Vector(mat1.rank);
		for(int i=0; i<mat1.rank; i++) {all1.v[i] = 1;}

		// Create a Vector containing the product of matrix * VectorAll1
		Vector Ax1 = mat1.product(all1);

		// Output whether the product is correct (by telling whether ||rowSum - Ax1|| == 0)
		output("\n	"+ (vecNorm(rowSum, Ax1)==0 ? "PASSED" : "FAILED") +
				": Function's output correctness testing! By checking ||mat1RowSum - mat1 * All1Vector|| == 0");

		/* Check 2: when SparseMatrix is an all-1, check whether |A*X1 - X2| == 0, 
		 * where Vector X2 has each element being the sum of Vector X1's all element, and X1 is an arbitrary Vector*/ 

		Vector X1 = new Vector(All1.rank);
		Vector X2 = new Vector(All1.rank);
		double X1Sum = (1+All1.rank)*All1.rank / 2;
		for(int i=1; i<All1.rank+1; i++) {
			X1.v[i-1] = i;
			X2.v[i-1] = X1Sum;
		}

		// The product of all-1 matrix and Vector X1
		Vector All1xX1 = All1.product(X1); 

		// Output whether the product is correct (by telling whether ||VectorColSum - All1Matrix x Vector|| == 0)
		output("\n	"+ (vecNorm(All1xX1, X2)==0 ? "PASSED" : "FAILED") +
				": Function's output correctness testing! By checking ||VectorColSum - All1Matrix * Vector|| == 0");

		// Output the computational costs
		end = System.nanoTime();
		computation("6 * n^1.5 + 6 * n^0.5", 67082039, end - start);
	}

	/**Function: Test SparseMatrix.leftMult()
	 * Parameter: None
	 * Return: None*/
	public static void leftMultTest() {
		// Output this section's title
		output("\n\n\n  SparseMatrix.leftMult() Testing, GROUND-TRUTH UNKNOWN: ");

		/* Verify the function's INPUT invariant:
		 * Check: whether an error is thrown to terminate the program, when input violates
		 * the function's invariants of "SparseMatrix1's rank == SparseMatrix2's rank" */
		verifyInput("leftMultTest");

		// Time-stamp for measuring the computational time of function output
		start = System.nanoTime();

		/* Verify the function's OUTPUT correctness, without knowing the ground truth*/ 
		/* Check: when left diagonal matrix sp1's elements are all-1, sp1 left-multiply any 
		 * SparseMatrix sp2's product would equal sp2 itself*/ 

		// Product of diagonal matrix and Mat1
		SparseMatrix diaProduct = dia1.leftMult(mat1);

		// Output whether the product is correct (by telling whether ||mat1 - MatrixDiagonal x Vector|| == 0)
		output("\n	"+ (matNorm(diaProduct, mat1)==0 ? "PASSED" : "FAILED") 
				+ ": Function's output correctness testing! By checking ||mat1 - MatrixDiagonal1 * mat1|| == 0");

		// Output the computational costs
		end = System.nanoTime();
		computation("9 * n^2 + n^1.5 + 3 * n", 8375530, end - start);
	}

	/**Function: Test SparseMatrix.add()
	 * Parameter: None
	 * Return: None*/
	public static void addTest() {
		// Output this section's title
		output("\n\n\n  SparseMatrix.add() Testing, GROUND-TRUTH UNKNOWN: ");

		/* Verify the function's INPUT invariant:
		 * Check: whether an error is thrown to terminate the program, when input violates
		 * the function's invariants of "SparseMatrix1's rank == SparseMatrix2's rank" */;
		 verifyInput("addTest");

		 /* Verify the function's OUTPUT correctness, without knowing the ground truth*/ 
		 /* Check1: let "dia2" be an all-2 diagonal matrix, then Matrix1 + Matrix1 = dia2 * Martix1, 
		  * under the condition that leftMultTest() is passed. */ 

		 // Time-stamp for measuring function's computational time
		 start = System.nanoTime();

		 // Sum of mat1 and Mat1
		 SparseMatrix sum = mat1.add(mat1,1);

		 // Output whether the sum is correct (by telling whether ||(mat1 + mat1*1) - (dia2 * mat1)|| == 0)
		 output("\n	"+ (matNorm(sum, dia2.leftMult(mat1))==0 ? "PASSED" : "FAILED") 
				 + ": Function's output correctness testing! By checking ||(mat1 + mat1*1) - (MatrixDiagonal2 * mat1)|| == 0");

		 /* Check2: if Matrix1 + Matrix1*(-1) = 0 */

		 // Product of diagonal matrix and Mat1
		 SparseMatrix sub = mat1.add(mat1,-1);

		 // Output whether the sum is correct (by telling whether ||mat1 + mat1*(-1)|| == 0)
		 output("\n	"+ (matNorm(sub)==0 ? "PASSED" : "FAILED") 
				 + ": Function's output correctness testing! By checking ||mat1 + mat1*(-1)|| == 0");

		 // Output the computational costs
		 end = System.nanoTime();
		 computation("24 * n^2.5 + 38 * n^2", 134259078, end - start);
	}

	/**Function: Test SparseMatrix.JacobiDecom()
	 * Parameter: None
	 * Return: None*/
	public static void JacobiDecomTest() {
		// Output this section's title
		output("\n\n\n  SparseMatrix.JacobiDecom() Testing, GROUND-TRUTH UNKNOWN: ");

		/* Verify the function's INPUT invariant:
		 * Check: whether an error is thrown to terminate the program, when the input violates
		 * the function invariants that "the three input matrixes are of the same size" */
		verifyInput("JacobiDecomTest");

		/* Verify the function's OUTPUT correctness, knowing the ground truth,
		 * Check: Jacobi-Decompose matrix mat1 into M1 = (-L + D  -U), and then Jacobi-Decompose M into
		 * M2 = (-L2 + D2 + -U2) to see if M2 equals to the original matrix mat1*/ 

		// Time-stamp for measuring function's computational time
		start = System.nanoTime();

		// Create a group of sparse matrixes for decomposition:
		SparseMatrix L1 = new SparseMatrix(mat1.rank);
		SparseMatrix D1 = new SparseMatrix(mat1.rank);
		SparseMatrix U1 = new SparseMatrix(mat1.rank);
		mat1.JacobiDecom(L1, D1, U1);

		// Compose (-L1 + D1 - U1) into M1, considering that SparseMatrix.add() has passed its testing
		SparseMatrix M1 =(L1.add(D1, 1)).add(U1, 1);

		// Create another group of sparse matrixes for another decomposition:
		SparseMatrix L2 = new SparseMatrix(mat1.rank);
		SparseMatrix D2 = new SparseMatrix(mat1.rank);
		SparseMatrix U2 = new SparseMatrix(mat1.rank);
		M1.JacobiDecom(L2, D2, U2);

		// Compose (-L2 + D2 - U2) into M2, considering that SparseMatrix.add() has passed its testing
		SparseMatrix M2 =(L2.add(D2, 1)).add(U2, 1);

		// Output whether the JacobiDecom() satisfies self-regression transform
		output("\n	"+ (matNorm(mat1, M2)==0 ? "PASSED" : "FAILED") + ": Function's output correctness testing! "
				+ "By Jacobi-decomposing mat1 into M1 = (-L + D - U), and then Jacobi-decomposing M1 into M2 = "
				+ "(-L2 + D2 -U2), then check whether ||mat1 - M1|| = 0 (Namely check whether twice Jacobi-"
				+ "Decomposition would equal itself.)");		

		// Output the computational costs
		end = System.nanoTime();
		computation("60 * n^2.5 + 60 * n^2 + 3 * n^1.5", 335446237, end - start);
	}


	/**Function: Test SparseMatrix.JacobiDecom()
	 * Parameter: None
	 * Return: None*/
	public static void VectorAddTest() {
		// Output this section's title
		output("\n\n\n  Vector.add() Testing, GROUND-TRUTH KNOWN: ");

		/* Verify the function's INPUT invariant:
		 * Check: whether an error is thrown to terminate the program, when the input violates
		 * the function invariants that "the Vectors are of the same length" */
		verifyInput("VectorAddTest");

		/* Verify the function's OUTPUT correctness, knowing the ground truth,
		 * Check: With Wilkinson Principle, considering that the ground truth known, whether 
		 * Vector v + v*1 ==> all values doubled in v */ 

		// Time-stamp for measuring function's computational time
		start = System.nanoTime();

		// The ground-truth known vector, and its sum with itself
		double vec[] = {1,2,3,4,5,6,7,8,9,10};
		Vector v = new Vector(vec);
		Vector sum = v.add(v,1);

		// Check for sum's value
		int i=0;
		for(i=0; i<v.len; i++) {if(sum.v[i] != 2*v.v[i]) break;}

		// Output whether the JacobiDecom() satisfies self-regression transform
		output("\n	"+ (i==v.len ? "PASSED" : "FAILED") + ": Function's output correctness testing! "
				+ "By checking whether the values in Vector + Vector*1 is twice as much as the original Vector.");		

		// Output the computational costs
		end = System.nanoTime();
		computation("60 * n^2.5 + 60 * n^2 + 3 * n^1.5", 335446237, end - start);
	}

	/**Function: Test mat1 with the Jacobi Iterative solver
	 * Parameter: None
	 * Return: None*/
	public static void mat1JacobiTest() {
		// Output this section's title
		output("\n\n\n  Jacobi.solver() Testing: ");

		// Initiate vectors b
		double bb1[] = new double[mat1.rank];
		double bb2[] = new double[mat1.rank];
		double bb3[] = new double[mat1.rank];
		for(int i=0; i<mat1.rank; i++) {
			bb1[i] = 0.0;
			bb2[i] = 0.0;
			bb3[i] = 1.0;
		}
		bb1[0] = 1.0;
		bb2[4] = 1.0;
		Vector b1 = new Vector(bb1);
		Vector b2 = new Vector(bb2);
		Vector b3 = new Vector(bb3);

		// Time-stamp for measuring the operating the Jacobi-Iterative-Solver on the mat1
		start = System.nanoTime();

		// The number of iterations for each Vector b
		output("\n	"+ "b = b1:");	
		int i1 = Jacobi.solver(mat1, b1);
		output("\n	"+ "b = b2:");	
		int i2 = Jacobi.solver(mat1, b2);
		output("\n	"+ "b = b3:");	
		int i3 = Jacobi.solver(mat1, b3);

		// Iff the Javobi-Solver convergent for
		output("\n\n	"+ (i1*i2*i3 != 0 ? "PASSED" : "FAILED") + ": Jacobi-Iterative-Solver convergence test!");		

		// Output the computational costs
		end = System.nanoTime();
		computation("54 * n^2.5 + 61 * n^2 + 9 * n^1.5 + (i1+i2+i3) * (24 * n^2.5 + 36 * n^2))", 
				(302122300 + (i1+i2+i3)*64164078), end - start);
	}


	// Helper functions:
	/** Function: Creating violations (assertion errors) for each SparseMatrix Function's input invariant,
	 * 			  to make sure that the assertion errors are indeed thrown to terminate the program when 
	 * 			  there are invariant violations
	 * Parameter: String funcName
	 * Return: void*/
	public static void verifyInput(String funcName) {
		// The expected error message
		String error = null;
		String I[] = new String[2];

		// Containing the testing case
		int arr[] = new int[2];

		if(funcName.equals("productTest") || funcName.equals("leftMultTest") 
				|| funcName.equals("addTest") || funcName.equals("VectorAddTest")) {
			error = "Unmatched matrix sizes!";
			arr[0] = 5;
			arr[1] = 4;
		}
		else if(funcName.equals("retrieveTest") || funcName.equals("matrixSetterTest")) {
			error = "IndexOutOfBound!";
			arr[0] = 5;
			arr[1] = -1;
		}
		else if(funcName.equals("JacobiDecomTest")) {
			error = "Wrong input matrix for Jacobi Decomposation!";
			arr[0] = 4;
			arr[1] = 5;
		}

		// Create an assertion error
		for(int i=0; i<arr.length; i++) {
			try {
				if(funcName.equals("productTest")) {new SparseMatrix(arr[i]).product(new Vector(arr[arr.length-1-i]));}
				else if(funcName.equals("leftMultTest")) {new SparseMatrix(arr[i]).leftMult(new SparseMatrix(arr[arr.length-1-i]));}
				else if(funcName.equals("addTest")) {new SparseMatrix(arr[i]).add(new SparseMatrix(arr[arr.length-1-i]),1);}
				else if(funcName.equals("retrieveTest")) {sp.retrieveElement(arr[i], arr[i]);}
				else if(funcName.equals("matrixSetterTest")) {sp.matrixSetter(arr[i], arr[i], 0);}
				else if(funcName.equals("JacobiDecomTest")) {sp.JacobiDecom(new SparseMatrix(i), new SparseMatrix(i), new SparseMatrix(arr.length-1-i));}
				else if(funcName.equals("VectorAddTest")) {new Vector(arr[i]).add(new Vector(arr[arr.length-1-i]),1);}
			}

			// Assign the error message to String I[];
			catch(AssertionError e) {I[i] = e.getMessage();}
		}

		// Output whether the AssertionError is caught
		output("\n	"+ (I[0].equals(error) && I[1].equals(error) ? "PASSED" : "FAILED") 
				+ ": Function's input invariants verification testing!");
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

	/**Function: Compute the second norm of two Vectors' difference
	 * Parameter: Vector A, B
	 * Return: double err*/
	public static double vecNorm(Vector A, Vector B) {
		// Make sure A and B are of the same size
		assert A.len == B.len;

		// Accumulate each the difference
		double err = 0;
		for(int i=0; i<A.len; i++) {	err += Math.pow(A.v[i] - B.v[i], 2);}

		// Return the second norm difference
		return Math.pow(err, 0.5);
	}

	/**Function: Compute the second norm of two SparseMatrixes' difference
	 * Parameter: SparseMatrix A, B
	 * Return: double err*/
	public static double matNorm(SparseMatrix A, SparseMatrix B) {
		// Make sure A and B are of the same size
		assert A.rank == B.rank;

		// Accumulate each the difference
		double err = 0;
		for(int i=0; i<A.rank; i++) {	
			for(int j=0; j<A.rank; j++) {err += Math.pow(A.retrieveElement(i,j)[1] - B.retrieveElement(i,j)[1], 2);}}

		// Return the second norm difference
		return Math.pow(err, 0.5);
	}

	/**Function: Compute the second norm of one SparseMatrixe
	 * Parameter: SparseMatrix A
	 * Return: double norm*/
	public static double matNorm(SparseMatrix A) {
		// Accumulate each the difference
		double value = 0;
		for(int i=0; i<A.rank; i++) {	
			for(int j=0; j<A.rank; j++) {value += Math.pow(A.retrieveElement(i,j)[1],2);}}

		// Return the second norm difference
		return Math.pow(value, 0.5);
	}


	// Helper functions - File operations:
	/**Function: Create report
	 * Parameters: String filePath, String content
	 * Return: None*/
	public static void createFile(String filePath, String content) {
		reportPath = filePath;
		try {
			FileWriter fw = new FileWriter(filePath,true);
			fw.write(content);
			fw.close();
		} catch (IOException e) {
			System.out.println("writing failure" + e);
		}
	}

	/**Function: Writing "content" into "filePath", for the purpose of generating reports'
	 * Parameter: String content
	 * Return: None*/
	public static void output(String content) {
		try {
			FileWriter fw = new FileWriter(reportPath,true);
			fw.write(content);
			fw.close();
		} catch (IOException e) {
			System.out.println("writing failure" + e);
		}
	}

	/**Function: statistics for computational cost: computational time per operation, and measured memory usage
	 * Parameters: String eNUM(the equation for operation estimation), int eNum, long mtime
	 * Return: None*/
	public static void computation(String eNUM, int eNum, long mtime) {
		output("\n		<STATISTICS>: \n		"+mtime/eNum + " ns/operation, " + "with Measured time = " + mtime+
				"ns, Estimated # of operations = "+eNUM + " = " + eNum + ".\n		Current MEM usage = "+ 
				(runtime.totalMemory()-runtime.freeMemory()) +" bytes");
	}

	/**Function: Read the three .csv files, return a Sparse Matrix constructed from them
	 * Parameter: String[] filePathOfCSV
	 * Return: SparseMatrix constructedFromCSV*/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static SparseMatrix readMatrix(String[] filePath) {

		// Create data structures for Sparse Matrix, being "value", "rowPtr", "colInd" respectively
		ArrayList<ArrayList> L = new ArrayList<ArrayList>();
		L.add(new ArrayList<Double>());
		L.add(new ArrayList<Integer>());
		L.add(new ArrayList<Integer>());

		try {
			// Each element in filePath[] corresponds to "value", "rowPtr", "colInd" respectively
			for(int i=0; i<L.size(); i++) {
				File file = new File(filePath[i]);
				if(file.isFile() && file.exists()) { 
					BufferedReader br = new BufferedReader(new FileReader(file));
					String line = null;

					// Iff current file is "value"
					if(i==0) while ((line = br.readLine()) != null) {L.get(i).add(Double.valueOf(line));}

					// Iff current file is "rowPtr", decrement by 1 to apply to class SparseMatrix
					else if(i==1) while ((line = br.readLine()) != null) {L.get(i).add(Integer.valueOf(line)-1);}

					// Iff current file is "colInd", decrement by 1 to apply to class SparseMatrix
					else while ((line = br.readLine()) != null) {L.get(i).add(Integer.valueOf(line)-1);}

					br.close();
				} else System.out.println("File doesn't exist!");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return new SparseMatrix(L.get(0), L.get(1), L.get(2));
	}
}
