
/**
 * Main.java, ECE4960-P5
 * Created by Yuan He(yh772) on 2018/05/16
 * Platform: Java 8, Eclipse, MacOS
 * Copyright © 2018 Yuan He. All rights reserved.
 * 
 * Main class: the main entry for this project,
 * invokes testing functions, and validation for PDE solvers.
 */
public class Main {

	public static void main(String args[]) {

		/*------------------------- Testing: Helper Functions --------------------------*/
		FileIO.createReport("TestHelperReport.txt", "");
		Test.retrieveTest();
		Test.matrixSetterTest();
		Test.productVecTest();
		Test.testJacobi();


		/*------------------------- Validating: 1D Parabolic PDE, Heat Conduction Equation --------------------------*/
		// Initial conditions, and constants
		double[] initCondition1D = {0, 0, 0, 0, 0, 0, 10, 10, 10, 0, 0, 0, 0, 0, 0};
		double D1 = 0.15;
		double h1 = 1;
		double dt1 = 1;
		double startTime1 = 1;
		double endTime1 = 100;

		// 1D - Forward Euler
		Vector initial1D = new Vector(initCondition1D);
		PDESolver.solveForward(new forward1D(), "1D", initial1D, h1, D1, dt1, startTime1, endTime1);

		// 1D - Backward Euler
		initial1D = new Vector(initCondition1D);
		PDESolver.solve(new backward1D(), "1D", initial1D, h1, D1, dt1, startTime1, endTime1);

		// 1D - Trapezoid Euler
		initial1D = new Vector(initCondition1D);
		PDESolver.solve(new trapezoid1D(), "1D", initial1D, h1, D1, dt1, startTime1, endTime1);


		/*------------------------- Validating: 2D Parabolic PDE, Heat Conduction Equation --------------------------*/
		// Initial conditions, and constants
		double[] initCondition2D = {0,0,0,0,10,0,0,0,0};
		double D2 = 0.15;
		double h2 = 1;
		double dt2 = 1;
		double startTime2 = 1;
		double endTime2 = 30;

		// 2D - Forward Euler
		Vector initial2D = new Vector(initCondition2D);
		PDESolver.solveForward(new forward2D(), "2D", initial2D, h2, D2, dt2, startTime2, endTime2);

		// 2D - Backward Euler
		initial2D = new Vector(initCondition2D);
		PDESolver.solve(new backward2D(), "2D", initial2D, h2, D2, dt2, startTime2, endTime2);

		// 2D - Trapezoid Euler
		initial2D = new Vector(initCondition2D);
		PDESolver.solve(new trapezoid2D(), "2D", initial2D, h2, D2, dt2, startTime2, endTime2);
	}

}
