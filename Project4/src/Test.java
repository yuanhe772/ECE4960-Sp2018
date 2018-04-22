/**
 * Test.java, ECE4960-P4
 * Created by Yuan He(yh772) on 2018/04/18
 * Platform: Java 8, Eclipse, MacOS
 * Copyright © 2018 Yuan He. All rights reserved.
 * 
 * P4, Test script:
 * Testings for basic functions, and validations for ODE solvers
 */
public class Test {

	/**Function: Validate ODE solvers, using the example in class
	 * 			Including RK34 with adaption, RK4, and Forward-Euler solver
	 * @param: None
	 * @return: None*/
	public static void validateODESolvers(String solverType) {
		/* Testing data initializing*/
		double xx0[] = {2};
		Vector x0 = new Vector(xx0);
		String fType[] = {"ODE Validation"};
		double h = 1;
		Vector x1 = new Vector(x0.len);

		/* Creating log files for testing ODE solvers */
		if(solverType.equals("RK34")) 
			FileIO.createReport("RK34Test.txt", "Result" + "\t\t\t\t" + "Error"+"\n");
		else if(solverType.equals("RK4")) 
			FileIO.createReport("RK4Test.txt", "Result" + "\t\t\t\t" + "Error"+"\n");
		else
			FileIO.createReport("ForwardEulerTest.txt", "Result" + "\t\t\t\t" + "Error"+"\n");

		/* Testing ODE solvers with the 1-d example in previous Hacker Practice*/
		FileIO.output(0 + "\t" + 0 +"\n");
		for(double t=0; t<10 ; t++) {
			// Estimate next x: extract x(RK4) out of xRK3_xRK4
			if(solverType.equals("RK34")) 
				x1 = Solvers.RK34AdaptiveH(x0, t, h, fType);
			else if(solverType.equals("RK4")) 
				x1 = Solvers.xRK3_xRK4(x0, t, h, fType).get(1);
			else
				x1 = Solvers.forwardEuler(x0, t, h, fType);
			
			// Update the initial x
			x0.v = x1.v.clone();
			FileIO.output(x1.v[0] + "\t" + Solvers.relativeErr(x1, t, fType)+"\n");
		}
	}
	
	/* Testing for helper functions: */
	/**Functions: Test helper functions: Vector.add(), Vector.scale(), Vector.norm()
	 * 			  by checking whether the calculated output equals a known ground-truth. 
	 * 			  (White-box testing)
	 * Parameters: None
	 * Return: None*/
	public static void testHelper() {
		FileIO.createReport("TestHelper.txt", "Testing helper functions: ");
		
		// The calculated
		double vec[] = {1,2,3};
		Vector Vec = new Vector(vec);
		double Norm = Vec.norm();
		Vector Scale = Vec.scale(-1);
		Vector Add = Vec.add(Scale, 1);

		// The ground-truth
		double TrueNorm = Math.pow(14,0.5);
		double trueScale[] = {-1,-2,-3};
		Vector TrueScale = new Vector(trueScale);
		double trueAdd[] = {0,0,0};
		Vector TrueAdd = new Vector(trueAdd);
		
		// Compare and output
		FileIO.output("\n\t"+ (Norm == TrueNorm? "PASSED" : "FAILED") +
				": Vector.norm()'s output correctness test! White-Box testing: "
				+ "by checking if a known Vector's norm(Calculated) == norm(Ground-Truth)");
		FileIO.output("\n\t"+ (Scale.equals(TrueScale) == 1? "PASSED" : "FAILED") +
				": Vector.Scale()'s output correctness test! White-Box testing: "
				+ "by checking if a length(3) Vector's Scaled(Calculated) == Scaled(Ground-Truth)");
		FileIO.output("\n\t"+ (Add.equals(TrueAdd) == 1? "PASSED" : "FAILED") +
				": Vector.Add()'s output correctness test! White-Box testing: "
				+ "by checking if a length(3) Vector's Sum(Calculated) == Sum(Ground-Truth)");		
	}
}
