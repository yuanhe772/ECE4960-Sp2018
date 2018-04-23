
/**
 * Main.java, ECE4960-P4
 * Created by Yuan He(yh772) on 2018/04/19
 * Platform: Java 8, Eclipse, MacOS
 * Copyright © 2018 Yuan He. All rights reserved.
 * 
 * P4, Main script:
 * The main entry for entire project 4, executes testing for helper functions, 
 * and ODE solvers, and executes circuit simulations
 */
public class Main {
	public static void main(String args[]) throws Exception {
		/* ------------ Task 1 - Task 3: ----------- */

		/* Test helper functions: */
		Test.testHelper();

		/* Test ODE solvers: */
		Test.validateODESolvers("Forward Euler");
		Test.validateODESolvers("RK4");
		Test.validateODESolvers("RK34");


		/* ---------------- Task 4: ---------------- */

		/* Step size = 1ns: */
		Simulation.simulate("Circuit1", 1e-9, "Forward Euler");
		Simulation.simulate("Circuit1", 1e-9, "RK4");
		Simulation.simulate("Circuit1", 1e-9, "RK34");

		/* Step size = 0.2ns: */
		Simulation.simulate("Circuit1", 2e-10, "Forward Euler");
		Simulation.simulate("Circuit1", 2e-10, "RK4");
		Simulation.simulate("Circuit1", 2e-10, "RK34");


		/* ---------------- Task 5: ---------------- */

		/* Step size = 1ns: */ 
		Simulation.simulate("Circuit2", 1e-9, "Forward Euler");
		Simulation.simulate("Circuit2", 1e-9, "RK4");
		Simulation.simulate("Circuit2", 1e-9, "RK34");

		/* Step size = 0.2ns: */
		Simulation.simulate("Circuit2", 2e-10, "Forward Euler");
		Simulation.simulate("Circuit2", 2e-10, "RK4");
		Simulation.simulate("Circuit2", 2e-10, "RK34");
	}
}
