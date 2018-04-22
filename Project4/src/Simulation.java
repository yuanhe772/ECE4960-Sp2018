/**
 * Simulation.java, ECE4960-P4
 * Created by Yuan He(yh772) on 2018/04/20
 * Platform: Java 8, Eclipse, MacOS
 * Copyright © 2018 Yuan He. All rights reserved.
 * 
 * Project 4, simulation of Task 4 and Task 5:
 * Create circuit simulation log files for different circuits' different 
 * simulations
 */
public class Simulation {

	/* Class invariants: */
	static final double xInit1[] = {0, 0};
	static final double xInit2[] = {2.5, 2.5};
	static final String fTypeCircuit1[] = {"Circuit1-1", "Circuit1-2"};
	static final String fTypeCircuit2[] = {"Circuit2-1", "Circuit2-2"};

	/* Class method: */
	/**Function: Simulate different circuits with different ODE solvers
	 * @param: String Circuit, double stepSize, String solverType
	 * @return: None
	 * @throws Exception */
	public static void simulate(String Circuit, double stepSize, String solverType) throws Exception {
		/* Simulation data initializing*/
		int circuit = Circuit.equals("Circuit1") ? 1:2;
		String circuitType[] = circuit == 1? fTypeCircuit1 : fTypeCircuit2;
		Vector x0 = new Vector(circuit == 1? xInit1 : xInit2);
		Vector x1 = new Vector(x0.len);

		/* Create output log file */
		if(solverType.equals("RK34")) 
			FileIO.createReport("Circuit"+circuit + "_RK34_" + (stepSize == 1e-9 ? "1ns.txt":"02ns.txt"), "");
		else if(solverType.equals("RK4"))
			FileIO.createReport("Circuit"+circuit + "_RK4_" + (stepSize == 1e-9 ? "1ns.txt":"02ns.txt"), "");
		else if(solverType.equals("Forward Euler"))
			FileIO.createReport("Circuit"+circuit + "_forwardEuler_" + (stepSize == 1e-9 ? "1ns.txt":"02ns.txt"), "");		
		else throw new Exception ("Please choose a valid ODE type");

		/* Output initial values of V1, V2*/
		if(circuit == 1)  FileIO.output(0 + "\t" + 0 +"\n");
		else  FileIO.output(2.5 + "\t" + 2.5 +"\n");

		/* Simulate with different ODE solvers*/
		for(double t=0; t<1e-7 ; t += stepSize) {
			// Estimate next x
			if(solverType.equals("RK34"))
				x1 = Solvers.RK34AdaptiveH(x0, t, stepSize, circuitType);
			else if(solverType.equals("RK4"))
				x1 = Solvers.xRK3_xRK4(x0, t, stepSize, circuitType).get(1);
			else
				x1 = Solvers.forwardEuler(x0, t, stepSize, circuitType);
			
			// Update initial x
			x0.v = x1.v.clone();
			FileIO.output(x1.v[0] + "\t" + x1.v[1]+"\n");
		}
	}

}
