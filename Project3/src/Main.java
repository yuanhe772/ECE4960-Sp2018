import java.util.Arrays;

/**
 * main.java, ECE4960-P3
 * Created by Yuan He(yh772) on 2018/04/05
 * Platform: Java 8, Eclipse, MacOS
 * Copyright © 2018 Yuan He. All rights reserved.
 * 
 * Project 3, the main script:
 * For executing the testings for helper functions, and task 1-7 listed on P3's hand-out, 
 * and auto-generate project 3's .txt-form report
 */

public class Main {

	/*Class variants, the DEFINE value*/
	static final int QN = 1;
	static final int SC = 2;
	static final int NORM = 3;
	static final int UNNORM = 4;
	static final int POW = 5;
	static final int EKV = 6;

	public static void main(String args[]) throws Exception {

		/* Generating report:*/
		dataIO.createReport("Auto-Report.txt", "\nECE4960-Project 3 Report:");

		/* ------------------------------ helper functions pre-checking ------------------------------ */
		dataIO.output("\n\n\n  Pre-Testing Helper Functions:");

		// Test functions in Class FullMatrix:
		Test.testMatPermuteAndScale();
		Test.testMatProduct();
		Test.testMatInverse();

		// Test functions in Class Vector:
		Test.testVector();


		/* ---------------------------------------- Task 1: ------------------------------------------ */
		dataIO.output("\n\n\n  TASK 1: Validation for Direct Matrix Solver:");

		// Direct matrix solver check, with ill-conditioned matrix in Note4, slide 50, and a matrix with rank == 5
		Test.testDirectSolver();


		/* ---------------------------------------- Task 2: ------------------------------------------ */
		dataIO.output("\n\n\n  TASK 2: Parameter Extraction with Power Law(Iterative Matrix Solver) and "
				+ "Linear Regression(Direct Matrix Solver):");

		// Linear Regression(Direct Matrix Solver):
		dataIO.output("\n\tLinear Regression(Direct Matrix Solver):");
		long startTime = System.nanoTime();
		Vector amLR = directMatrixSolver.directSolver(POW);
		double lossLR = iterativeSolver.V(amLR, UNNORM);
		long endTime = System.nanoTime();
		dataIO.output("\n\t\tExtracted [a, m] = "+Arrays.toString(amLR.v) + ", V = "+lossLR);
		dataIO.computationCost(endTime - startTime);

		// Power Law(Iterative Matrix Solver):
		dataIO.output("\n\tPower Law(Iterative Matrix Solver):");
		double tolerancePow = Math.pow(10, -7);
		double initialGuessPow[] = {20, -1};
		startTime = System.nanoTime();
		double resPL[] = iterativeSolver.iterSolver(QN, UNNORM, POW, new Vector(initialGuessPow), tolerancePow);
		endTime = System.nanoTime();
		dataIO.output("\n\t\tStep Size = 0.1% of parameter\n\t\tInitial [a, m] = "+Arrays.toString(initialGuessPow));
		dataIO.result(resPL);
		dataIO.convergenceObserve(resPL);
		dataIO.computationCost(endTime - startTime);

		// Result OBSERVATION:
		dataIO.output("\n\n\t< OBSERVATION >:\n\t\tLinear Regression:\n\t\t\tPROS: V is relatively small (<3), and could be "
				+ "considered to be correctly extracting the parameters; Predictable computational cost and "
				+ "performance (Time cost in (15ms, 90ms), MEM usage = 2MB, V in (0.3, 2.5))\n\t\t\tCONS: "
				+ "although average case better than iterative solver, its best case is worse"
				+ ";\n\t\tPower Law:"
				+ "\n\t\t\tPROS: Through OBSERVATION, the best case of Power Law is better than Linear regression, "
				+ "in both aspects of performance (smaller V) and cost (faster computation)."
				+ "\n\t\t\tCONS: Unpredictable computational cost and performance (Time cost in (4ms, 990ms), MEM usage"
				+ " in (2MB, 5MB), V in (0.1, 125)), due to occasional failures in convergence caused by fluctuating "
				+ "measured data and fixed initial guess.");


		/* ---------------------------------------- Task 3: ----------------------------------------- */
		dataIO.output("\n\n\n  TASK 3: Plot S(measure):\n\tAvailable in the Visual_Report_of_Task_3_5_7.pdf");


		/* ---------------------------------------- Task 4: ----------------------------------------- */
		dataIO.output("\n\n\n  TASK 4: Extract EKV's parameters with iterative solver:");

		// Quasi-Newton estimation: stepSize/parameter = 0.01%
		dataIO.output("\n\tQuasi-Newton method, with 2nd-order central-difference: f(x + h) - f(x - h) / 2h:");
		double tolerance1 = Math.pow(10, -12);
		double initGuessQN[] = {3.0E-6, 0.6, 1.6};
		double resQN[] = iterativeSolver.iterSolver(QN, UNNORM, EKV, new Vector(initGuessQN), tolerance1);
		dataIO.output("\n\t\tStep Size = 0.01% of parameter\n\t\tInitial guess from Task 6 [Is, Kappa, Vth] = "
				+Arrays.toString(initGuessQN));
		dataIO.result(resQN);
		dataIO.convergenceObserve(resQN);

		// Secant estimation: stepSize/parameter = 0.01%
		dataIO.output("\n\tSecant method, with 2nd-order central-difference: f(x + h) - f(x - h) / 2h:");
		double initGuessSC[] = {3.0E-6, 0.5, 1.0};
		double resSC[] = iterativeSolver.iterSolver(SC, UNNORM, EKV, new Vector(initGuessSC), tolerance1);
		dataIO.output("\n\t\tStep Size = 0.01% of parameter\n\t\tInitial guess from Task 6 [Is, Kappa, Vth] = "
				+Arrays.toString(initGuessSC));
		dataIO.result(resSC);
		dataIO.convergenceObserve(resSC);

		// Result OBSERVATION:
		dataIO.output("\n\n\t< OBSERVATION >:\n\t\tQuasi-Newton:\n\t\t\tPROS: Quadratic Convergence, and more "
				+ "robust against far-away initial guesses; In this case, when convergent, smaller V than "
				+ "Secant method.\n\t\t\tCONS: Complex and costly inner-loop implementation;"
				+ "\n\t\tPower Law:\n\t\t\tPROS: straight-forward and less costly inner-loop implementation"
				+ "\n\t\t\tCONS: At most Linear Convergence, and not Quadratic Convergence. \n\t\t\tIn this case, "
				+ "the optimal initial guess generated from Task 6 is very good, so it only took one iteration "
				+ "to converge, so linear and quadratic convergence couldn't be observed. "
				+ "\n\t\t\tIn other cases, based on the experience of when I didn't have Task 6 to get "
				+ "the optimal initial guesses, and when Quasi-Newton and Secant are given the same initial "
				+ "guesses and step sizes, Secant is at most linearly convergent, and Quasi-newton method always"
				+ " converges faster and is more robust "
				+ "against bad initial guesses. \n\t\t\tThe Secant method is also observed to be more greedy "
				+ "and more 'local optimal' (prone to converge to an answer that's near the initial guess). "
				+ "Therefore it needs an initial guess to be very close to the 'true answer' to avoid wrong convergence.");


		/* ---------------------------------------- Task 5: ----------------------------------------- */
		dataIO.output("\n\n\n  TASK 5.1: Plot S(model)/S(measure):\n\tAvailable in the Visual_Report_of_Task_3_5_7.pdf");

		// Normalized data, Quasi-Newton estimation: stepSize/parameter = 0.00001%
		dataIO.output("\n\n\n  TASK 5.2: Repeat Task 4 with normalized data");
		dataIO.output("\n\tNORMALIZED: Quasi-Newton method, with 2nd-order central-difference: f(x + h) - f(x - h) / 2h:");
		gradientEsti.changePerturb(0.0000001);
		double tolerance2 = 1.75*Math.pow(10, -14);
		double initQNNormal[] = {2e-06, 6e-01, 9e-01};
		double NormResQN[] = iterativeSolver.iterSolver(QN, NORM, EKV, new Vector(initQNNormal), tolerance2);
		dataIO.output("\n\t\tStep Size = 0.00001% of parameter\n\t\tInitial [Is, Kappa, Vth] = "
				+Arrays.toString(NormResQN));
		dataIO.result(NormResQN);
		dataIO.convergenceObserve(NormResQN);

		// Normalized data, Secant estimation: stepSize/parameter = 0.01%
		dataIO.output("\n\tNORMALIZED: Secant method, with 2nd-order central-difference: f(x + h) - f(x - h) / 2h:");
		gradientEsti.changePerturb(0.0001);
		double initSCNormal[] = {2.35E-6, 0.59, 1.11};
		double NormResSC[] = iterativeSolver.iterSolver(SC, NORM, EKV, new Vector(initSCNormal), tolerance2);
		dataIO.output("\n\t\tStep Size = 0.01% of parameter\n\t\tInitial [Is, Kappa, Vth] = "
				+Arrays.toString(NormResSC));
		dataIO.result(NormResSC);
		dataIO.convergenceObserve(NormResSC);

		// Result OBSERVATION:
		dataIO.output("\n\n\t< OBSERVATION >:\n\t\tThe optimal initial guesses from Task 6 doesn't work for Task 5, "
				+ "either causes converging too slow or raises NAN. \n\t\tSo I chose the initial guesses that are very close"
				+ " to the result extracted from Task 4. \n\t\tAfter converging, the ||V|| is much larger than ||V||"
				+ " in task 4, because Without normalizing, the error will only be dominated by the large Ids data points. \n\t\t"
				+ "The fit is therefore good for large Ids data points, without a guarantee that the small Id values "
				+ "are also well fitted since they don't contribute much to ||V||. \n\t\tWith normalization, the small "
				+ "Id errors contributes much more than before, so the ||V|| is much larger in this scenario.");


		/* ---------------------------------------- Task 6: ----------------------------------------- */
		dataIO.output("\n\n\n  TASK 6: Optimized Initial Guess Search:");

		//disable the print-outs, and change the MAX_ITERATION to minimize computational time
		iterativeSolver.logModeEnable(false);
		iterativeSolver.changeMaxIter(2);

		// Unnormalized data, Quasi-Newton:
		dataIO.initialGuess(QN, UNNORM, EKV, tolerance1);

		// Unnormalized data, Secant:
		dataIO.initialGuess(SC, UNNORM, EKV, tolerance1);

		// Normalized data, Quasi-Newton:
		dataIO.initialGuess(QN, NORM, EKV, tolerance1);

		// Normalized data, Secant:
		dataIO.initialGuess(SC, NORM, EKV, tolerance1);

		// Result OBSERVATION:
		dataIO.output("\n\n\t< OBSERVATION >:\n\t\tThe result of task 6 is used in task 4, making both of the "
				+ "Quasi-Newton and Secant Method converges faster than using the other initial guesses I randomly "
				+ "chose. \n\t\tFor each search, the MAX_ITERATION is set "
				+ "to 2, because through observation, the search doesn't have to wait until the solver to fully "
				+ "converge to render a reasonable initial guess. \n\t\tMAX_ITERATION == 2 is good enough for Quasi-Newton "
				+ "method to quadratically converge to a much smaller V after the second iteration, and for "
				+ "Secant method to get a sense of how much the magnitude of V would be when it finally converges. "
				+ "\n\t\tTherefore MAX_ITERATION is set to 2 to ensure computational efficiency.");


		/* ---------------------------------------- Task 7: ----------------------------------------- */
		dataIO.output("\n\n\n  TASK 7.1: Visualization:\n\tAvailable in the Visual_Report_of_Task_3_5_7.pdf");
		
		// Test the parameters extracted from task 4, Quasi-Newton method, by looking at model's approximation
		dataIO.output("\n\n\n  TASK 7.2: Validation for iterative solver using Quasi-Newton method: ");
		double paraQN[] = {resQN[5], resQN[6], resQN[7]};
		Test.testQNIterativeSolver(new Vector(paraQN));

		// Result OBSERVATION:
		dataIO.output("\n\n\t< OBSERVATION >:\n\t\tThe test is implemented by seperating the Vgs into three "
				+ "sections, and respectively calculate their approximaions under different scenarios. \n\t\tThe "
				+ "accumulated relative error || error% || of the complete data set is < 10^-3; \n\t\tTogether "
				+ "with the visual validation of Fig.[4] in Visual_Report_of_Task_3_5_7.pdf, it could be concluded"
				+ " that the the iterative solver is implemented correctly..");
	}
}