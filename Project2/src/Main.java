/**
 * Main.java, ECE4960-P2
 * Created by Yuan He(yh772) on 2018-03-04
 * Platform: Java 8, Eclipse, MacOS
 * Copyright © 2018 Yuan He. All rights reserved.
 */


public class Main {
	public static void main(String args[]) {
		// Generate report:
		Test.createFile("report.txt","\nECE4960-Project 2 Report: ");

		// Modular testing for class SparseMatrix:
		Test.retrieveTest();
		Test.matrixSetterTest();
		Test.productTest();
		Test.leftMultTest();
		Test.addTest();
		Test.JacobiDecomTest();

		// Modular testing for class Vector:
		Test.VectorAddTest();

		// Convergence testing for Jacobi-Iterative-Solver
		Test.mat1JacobiTest();
	}
}
