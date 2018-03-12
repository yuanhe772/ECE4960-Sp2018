/**
 * Main.java, ECE4960-P2
 * Created by Yuan He(yh772) on 2018-03-04
 * Platform: Java 8, Eclipse, MacOS
 * Copyright © 2018 Yuan He. All rights reserved.
 */


public class Main {
	public static void main(String args[]) {

		// Generating report:
		Test.createFile("report.txt","\nECE4960-Project 2 Report: ");

		// Modular testing for class Vector.java:
		Test.VectorAddTest();

		// Modular testing for class SparseMatrix.java:
		Test.retrieveTest();
		Test.matrixSetterTest();
		Test.productTest();
		Test.leftMultTest();
		Test.addTest();
		Test.JacobiDecomTest();

		// Convergence testing for Jacobi-Iterative-Solver
		Test.mat1JacobiTest();
		
		// Brief Discussion
		Test.output("\n\n\n\n\nDiscussion:" + "\n\nThe program passed all the tests designed in Test.java, "
				+ "including the SparseMatrix and Vector's functions, and the Jacobi-Solver's convergence"
				+ " test. For Jacobi convergence test, the in-code check has already ensured that the "
				+ "normalized-second-norm error(||Ax - b||/||b||) is decreasing with iterations, A.K.A, "
				+ "this Jacobi-Iterative-Solver is generating a convergent solution."
				+ "\n\nThe modular design and test strategies are further disccussed in Algorithm&Design.pdf"
				+ " and is therefore neglected here. \n\nOne of the abnormalty observed from the output report"
				+ " is that, for different testing, each function's (computational time)/(estimated number "
				+ "of operations) is not constant accross the program, which could be resulted from an "
				+ "under-estimate of the actual runtime time complexity. My estimation of # of operations "
				+ "was derived by manually calculate each testing function's time complexity, and it can "
				+ "improved by adding counters to count the number of operations instead to achieve better"
				+ " estimation precision.");

	}
}
