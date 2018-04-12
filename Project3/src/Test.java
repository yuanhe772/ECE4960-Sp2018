import java.util.ArrayList;
import java.util.HashMap;

/**
 * Test.java, ECE4960-P3
 * Created by Yuan He(yh772) on 2018/04/10
 * Platform: Java 8, Eclipse, MacOS
 * Copyright © 2018 Yuan He. All rights reserved.
 * 
 * Project 3, Testing program:
 * For task 1, 2, 7's validation, and modular testing of helper functions.
 */

public class Test {

	/* Class Variants: The file inputs, and DEFINE values*/
	// The measured data for power law, and NMOS parameter extraction
	static final ArrayList<ArrayList<Double>> powMeasure = dataIO.powLawData(10, 10, -0.5);
	static final ArrayList<ArrayList<Double>> NMOSMeasure = dataIO.readNMOS("outputNMOS.txt");
	static final double VT = 0.026;
	static final int NORM = 3;
	static final int UNNORM = 4;


	/* Class Methods:*/
	/* Testing for helper functions in Class FullMatrix: */
	/**Functions: test the FullMatrix.rowPermute(), FullMatrix.rowScale(), by testing whether the 
	 * 			  calculate matrix equals a known ground-truth. (White-box testing)
	 * Parameters: None
	 * Return: None*/
	public static void testMatPermuteAndScale() {
		// The calculated
		double mat[][] = {{1,1,1},{2,2,2},{3,3,3}};
		FullMatrix Mat = new FullMatrix(mat);
		Mat.rowPermute(0, 1);

		// The ground-truth
		double truePermute[][] = {{2,2,2},{1,1,1},{3,3,3}};
		FullMatrix TruePermute = new FullMatrix(truePermute);
		double trueScale[][] = {{2,2,2},{3,3,3},{3,3,3}};
		FullMatrix TrueScale = new FullMatrix(trueScale);

		// Compare and output
		dataIO.output("\n\t"+ (Mat.equals(TruePermute) == 1? "PASSED" : "FAILED") +
				": FullMatrix.rowPermute()'s output correctness test! White-Box testing: "
				+ "by checking if a rank(3) matrix's Permuted(Calculated) == Permuted(Ground-Truth)");
		Mat.rowScale(0, 1, 1);
		dataIO.output("\n\t"+ (Mat.equals(TrueScale) == 1? "PASSED" : "FAILED") +
				": FullMatrix.rowScale()'s output correctness test! White-Box testing: "
				+ "by checking if a rank(3) matrix's RowScaled(Calculated) == RowScaled(Ground-Truth)");
	}

	/**Functions: test the FullMatrix.product(), by testing whether the Matrix*Vector's product ==
	 * 			  a known true product. (White-box testing)
	 * Parameters: None
	 * Return: None*/
	public static void testMatProduct() {
		// The calculated
		double mat[][] = {{1,1,1},{2,2,2},{3,3,3}};
		FullMatrix Mat = new FullMatrix(mat);
		double vec[] = {1,1,1};
		Vector Vec = new Vector(vec);
		Vector res = Mat.product(Vec);

		// The ground-truth
		double trueRes[] = {3,6,9};
		Vector TrueRes = new Vector(trueRes);

		// Compare and output
		dataIO.output("\n\t"+ (res.equals(TrueRes) == 1? "PASSED" : "FAILED") +
				": FullMatrix.product()'s output correctness test! White-Box testing: "
				+ "by checking if a rank(3) matrix's A*x(Calculated) == b(Ground-Truth)");
	}

	/**Function: test the FullMatrix.inverseRank2(), by comparing the true inverse of a matrix with 
	 * 			 calculated inverse.(White-box testing)
	 * Parameters: None
	 * Return: None*/
	public static void testMatInverse() {
		// The calculated
		double rank2[][] = {{4, 7},{2, 6}};
		FullMatrix inverse2 = new FullMatrix(rank2).inverseRank2();
		double rank3[][] = {{1, 2, 3},{0, 1, 4},{5, 6, 0}};
		FullMatrix inverse3 = new FullMatrix(rank3).inverseRank3();

		// The ground-truth
		double trueInverse2[][] = {{0.6, -0.7},{-0.2, 0.4}};
		double trueInverse3[][] = {{-24, 18, 5},{20, -15, -4}, {-5, 4, 1}};

		// Check the error and output
		dataIO.output("\n\t"+ (inverse2.equals(new FullMatrix(trueInverse2)) == 1? "PASSED" : "FAILED") +
				": FullMatrix.inverseRank2()'s output correctness test! White-Box testing: "
				+ "by checking if a rank(2) matrix's Inverse(Calculated) == Inverse(Ground-Truth)");
		dataIO.output("\n\t"+ (inverse3.equals(new FullMatrix(trueInverse3)) == 1? "PASSED" : "FAILED") +
				": FullMatrix.inverseRank3()'s output correctness test! White-Box testing: "
				+ "by checking if a rank(3) matrix's Inverse(Calculated) == Inverse(Ground-Truth)");
	}

	/* Testing for helper functions in Class Vector: */
	/**Functions: test the Vector.rowPermute(), Vector.rowScale(), Vector.add(), Vector.scale()
	 * 			  by testing whether the calculate Vector equals a known ground-truth. (White-box testing)
	 * Parameters: None
	 * Return: None*/
	public static void testVector() {
		// The calculated
		double vec[] = {1,2,3};
		Vector Vec = new Vector(vec);
		Vec.rowPermute(1, 2);

		// The ground-truth
		double truePermute[] = {1,3,2};
		Vector TruePermute = new Vector(truePermute);
		double trueRowScale[] = {1,3,5};
		Vector TrueRowScale = new Vector(trueRowScale);
		double trueAdd[] = {0,0,0};
		Vector TrueAdd = new Vector(trueAdd);
		double trueScale[] = {2,6,10};
		Vector TrueScale = new Vector(trueScale);

		// Compare and output
		dataIO.output("\n\t"+ (Vec.equals(TruePermute) == 1? "PASSED" : "FAILED") +
				": Vector.rowPermute()'s output correctness test! White-Box testing: "
				+ "by checking if a length(3) Vector's Permuted(Calculated) == Permuted(Ground-Truth)");

		Vec.rowScale(1, 2, 1);
		dataIO.output("\n\t"+ (Vec.equals(TrueRowScale) == 1? "PASSED" : "FAILED") +
				": Vector.rowScale()'s output correctness test! White-Box testing: "
				+ "by checking if a length(3) Vector's RowScaled(Calculated) == RowScaled(Ground-Truth)");

		Vector Add = Vec.add(Vec, -1);
		dataIO.output("\n\t"+ (Add.equals(TrueAdd) == 1? "PASSED" : "FAILED") +
				": Vector.Add()'s output correctness test! White-Box testing: "
				+ "by checking if a length(3) Vector's Sum(Calculated) == Sum(Ground-Truth)");

		Vector Scale = Vec.scale(2);
		dataIO.output("\n\t"+ (Scale.equals(TrueScale) == 1? "PASSED" : "FAILED") +
				": Vector.Scale()'s output correctness test! White-Box testing: "
				+ "by checking if a length(3) Vector's Scaled(Calculated) == Scaled(Ground-Truth)");
	}

	/* Testing for Task 1, the Direct Matrix Solver: */
	/**Function: test the correctness of the direct matrix solver, with an ill-conditioned matrix and a 
	 * 			 matrix having rank-up-to-5, and check whether the ||error|| < 10^-9
	 * Parameters: None
	 * Return: None*/
	public static void testDirectSolver() {
		/* ill-conditioned matrix solution calculation */
		double[][] AA1 = {{1970,1},{1, -0.8631}};
		double[] VV1 = {19720, 7.631};
		FullMatrix A1 = new FullMatrix(AA1);
		Vector V1 = new Vector(VV1);

		// Create HashMap for storing the solution for matrix solver, and initialize it
		HashMap<Integer, Double> X1=new HashMap<Integer, Double>();  
		for(int i=0; i<A1.rank; i++) {X1.put(i,0.0);}

		// Solve the matrix:
		directMatrixSolver.Diagnal(A1, V1);
		directMatrixSolver.backSub(A1, V1, X1);

		/* rank-up-to-4 matrix solution calculation */
		double[][] AA2 = {{1,2,0,0,3},{4,5,6,0,0},{0,7,8,0,9},{0,0,0,10,0},{11,0,0,0,12}};
		double[] VV2 = {5,4,3,2,1};
		FullMatrix A2 = new FullMatrix(AA2);
		Vector V2 = new Vector(VV2);

		// Create HashMap for storing the solution for matrix solver, and initialize it
		HashMap<Integer, Double> X2=new HashMap<Integer, Double>();  
		for(int i=0; i<A2.rank; i++) {X2.put(i,0.0);}

		// Solve the matrix:
		directMatrixSolver.Diagnal(A2, V2);
		directMatrixSolver.backSub(A2, V2, X2);

		// Check the error and output
		dataIO.output("\n\t"+ (directMatrixError(X1) < Math.pow(10, -9)? "PASSED" : "FAILED") +
				": Direct matrix solver's output correctness test! Black-box testing: By testing with the "
				+ "ill-conditioned matrix in Note4, slide 50 to check if || Ax-b || < 10^-9");
		dataIO.output("\n\t"+ (directMatrixError(X2) < Math.pow(10, -9)? "PASSED" : "FAILED") +
				": Direct matrix solver's output correctness test! Black-box testing: By testing with a "
				+ "rank-up-to-4 matrix to check if || Ax-b || < 10^-9");
	}

	/**Function: Calculate the direct matrix solver's error by calculating || Ax-b ||
	 * Parameters: HashMap<Integer, Double> X
	 * Return: double error*/
	public static double directMatrixError(HashMap<Integer, Double> X) {
		FullMatrix A = new FullMatrix(X.size());
		Vector V = new Vector(X.size());

		// ill-conditioned matrix solver
		if(X.size() == 2) {
			double[][] AA = {{1970,1},{1, -0.8631}};
			double[] VV = {19720, 7.631};
			A = new FullMatrix(AA);
			V = new Vector(VV);
		}
		// Rank-up-to-4 matrix solver
		else {
			double[][] AA = {{1,2,0,0,3},{4,5,6,0,0},{0,7,8,0,9},{0,0,0,10,0},{11,0,0,0,12}};
			double[] VV = {5,4,3,2,1};
			A = new FullMatrix(AA);
			V = new Vector(VV);
		}

		// Check if the || Ax-b || -> 0
		double error = 0;
		Vector XX = new Vector(A.rank);
		for(int i=0; i<A.rank; i++) {XX.v[i] = X.get(i);}

		// A multiplying with X
		Vector P = A.product(XX);

		// Calculating error
		for(int i=0; i<A.rank; i++) {error += Math.pow(P.v[i] - V.v[i], 2);	}
		return Math.pow(error, 0.5);
	}

	/* Testing for Task7, the Iterative Matrix Solver: */
	/**Function: Validation with the help of approximation:
	 * 			 For Vgs < Vth, Y(model) should be an expo() of Vgs with Kappa < 1 and nearly insensitive to VDS;
	 * 			 For Vgs > Vth and Vds > V_Dsat, Y(model) should be quadratic to Vgs and insensitive to VDS;
	 * 			 For Vgs > Vth and Vds < V_Dsat, Y(model) should be quadratic to Vds;
	 * 			 And check for the accumulated 2nd-norm error of ||Y(model) - Y(approximate)||
	 * 			 Assume ||Y(model) - Y(approximate)|| < 10^-4 as success, vice versa as failure
	 * Parameters: Vector para
	 * Return: None */
	public static void testQNIterativeSolver(Vector para) {
		int dataSize = NMOSMeasure.get(0).size();
		double yModel = 0;
		double yApprox = 0;
		double V_Dsat = 0;
		double error1 = 0;
		double error2 = 0;
		double error3 = 0;

		for(int i=0; i<dataSize; i++) {
			V_Dsat = para.v[1] * (NMOSMeasure.get(0).get(i)-para.v[2]);
			yModel = iterativeSolver.yEKVModel(para, NMOSMeasure.get(0).get(i), NMOSMeasure.get(1).get(i));
			// Vgs < Vth, and Kappa < 1
			if(NMOSMeasure.get(0).get(i) < para.v[2] && para.v[1]<1) {
				yApprox = approximate1(para, NMOSMeasure.get(0).get(i), NMOSMeasure.get(1).get(i));
				error1 += Math.pow((yModel - yApprox), 2);
			}
			// Vgs > Vth, and Vds > V_Dsat
			if(NMOSMeasure.get(0).get(i) > para.v[2] && NMOSMeasure.get(1).get(i) > V_Dsat) {
				yApprox = approximate2(para, NMOSMeasure.get(0).get(i), NMOSMeasure.get(1).get(i));
				error2 += Math.pow((yModel - yApprox), 2);
			}
			// Vgs > Vth, and Vds < V_Dsat
			if(NMOSMeasure.get(0).get(i) > para.v[2] && NMOSMeasure.get(1).get(i) < V_Dsat) {
				yApprox = approximate3(para, NMOSMeasure.get(0).get(i), NMOSMeasure.get(1).get(i));
				error3 += Math.pow((yModel - yApprox), 2);
			}
		}

		// The || error% || of Vgs < Vth, Y(model)'s magnitude is dominated around 10^-7
		error1 = Math.pow(error1, 0.5);
		error1 /= Math.pow(10, -7);

		// The || error% || of Vgs > Vth, Y(model)'s magnitude is dominated around 10^-3
		error2 = Math.pow(error2, 0.5);
		error2 /= 3*Math.pow(10, -3);
		error3 = Math.pow(error3, 0.5);
		error3 /= 4*Math.pow(10, -3);

		// The accumulative || error% ||:
		double error = error1 + error2 + error3;

		// Output whether the product is correct (by telling whether || (Y(model) - Y(approximate)) / Y(model) || < tol)
		dataIO.output("\n\t"+ (error1 < 6*Math.pow(10, -5)? "PASSED" : "FAILED") +
				": Approximation1 validation test of iterative solver using Quasi-Newton! Black-Box testing: "
				+ "By checking when Vgs < Vth: || error1% || < 10^-5");
		dataIO.output("\n\t"+ (error2 < 6*Math.pow(10, -3)? "PASSED" : "FAILED") +
				": Approximation2 validation test of iterative solver using Quasi-Newton! Black-Box testing: "
				+ "By checking when Vgs > Vth, Vds > Vdsat: || error2% || < 10^-3");
		dataIO.output("\n\t"+ (error3 < 2*Math.pow(10, -3)? "PASSED" : "FAILED") +
				": Approximation3 validation test of iterative solver using Quasi-Newton! Black-Box testing: "
				+ "By checking when Vgs > Vth, Vds < Vdsat: || error3% || < 10^-3");
		dataIO.output("\n\t"+ (error < 7*Math.pow(10, -3)? "PASSED" : "FAILED") +
				": The complete data set's approximation validation test of iterative solver! Black-Box testing: "
				+ "By checking if the accumulated error% that combines the previous three sections, || error% || < 10^-3");
	}

	/**Function: approximation for Vgs < Vth, x1 = Kappa*(Vgs-Vth) / VT, x2 = (Kappa*(Vgs-Vth)-Vds) / VT,
	 * 			log^2(1+x1) and log^2(1+x2) could be approximated with e^x1 and e^x2
	 * Parameters: Vector para
	 * Return: double approximation */
	public static double approximate1(Vector para, double Vgs, double Vds) {
		double x1= para.v[1] * (Vgs - para.v[2]) / VT;
		double x2= (para.v[1] * (Vgs - para.v[2]) - Vds) / VT;
		return para.v[0] * (Math.exp(x1) - Math.exp(x2));
	}

	/**Function: approximation for Vgs > Vth, and Vds > V_Dsat,
	 * 			log^2(1+x1) and log^2(1+x2) could be approximated with e^x1 and (x2/2)^2
	 * Parameters: Vector para
	 * Return: double approximation */
	public static double approximate2(Vector para, double Vgs, double Vds) {
		double x1= para.v[1] * (Vgs - para.v[2]) / VT;
		double x2= (para.v[1] * (Vgs-para.v[2])-Vds) / VT;
		return para.v[0] * (Math.pow(x1/2, 2) - Math.exp(x2));
	}

	/**Function: approximation for Vgs > Vth, and Vds < V_Dsat,
	 * 			log^2(1+x1) and log^2(1+x2) could be approximated with (x1/2)^2 and (x2/2)^2
	 * Parameters: Vector para
	 * Return: double approximation */
	public static double approximate3(Vector para, double Vgs, double Vds) {
		double x1= para.v[1] * (Vgs - para.v[2]) / VT;
		double x2= (para.v[1] * (Vgs-para.v[2])-Vds) / VT;
		return para.v[0] * (Math.pow(x1/2, 2) - Math.pow(x2/2, 2));
	}
}
