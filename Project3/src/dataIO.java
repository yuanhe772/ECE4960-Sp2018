import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * dataIO.java, ECE4960-P3
 * Created by Yuan He(yh772) on 2018/04/05
 * Platform: Java 8, Eclipse, MacOS
 * Copyright © 2018 Yuan He. All rights reserved.
 * 
 * Project 3, dataIO:
 * Generates measured data, optimal initial guesses; 
 * Implements file operations for file reading;
 * Generates reports.
 */

public class dataIO {

	/* Class invariant:*/
	// The output report's path, and DEFINE values
	static String reportPath = null;
	static final int QN = 1;
	static final int SC = 2;
	static final int NORM = 3;
	static final int UNNORM = 4;
	static final int POW = 5;
	static final int EKV = 6;

	// For measuring memory usage and computational time
	static Runtime runtime = Runtime.getRuntime();

	// For generating initial guess for different iterative matrix solvers
	static final double Is[] = {1e-8, 3e-8, 1e-7, 3e-7, 1e-6, 3e-6, 1e-5, 3e-5};
	static final double Kappa[] = {0.5, 0.6, 0.7, 0.8, 0.9};
	static final double Vth[] = {0.8, 0.9, 1.0, 1.1, 1.2, 1.3, 1.4, 1.5, 1.6, 1.7, 1.8, 1.9, 2.0};


	/* Class methods:*/
	/**Function: Generate measure data for Power Law, y = c * x^m.
	 * 			 The measured data has random error of (0%, 20%), generated with the rule of: 
	 * 			 When random() returns in (0, 0.1), subtract 20% , (0.1, 0.2), subtract 16%...
	 * 			 and in (0.9, 1.0), add 20%
	 * Parameters: int length (the size of data set), double a, double m
	 * Return: a 2-d Arraylist, with one column being the X, and the other one being the Y_measured*/
	public static ArrayList<ArrayList<Double>> powLawData(int length, double c, double m) {
		// Structures for storing measured data
		ArrayList<Double> x = new ArrayList<Double>();
		ArrayList<Double> y = new ArrayList<Double>();
		double xi = 0;
		double yi = 0;

		// Creating measure data with random error
		for(int i=0; i<length; i++) {
			xi = (double)(i+1)*(i+1);
			yi = c*Math.pow(xi,m);
			yi *= (1 + (Math.random() - 0.5)/2.5);
			x.add(xi);
			y.add(yi);
		}

		// Return measure data
		ArrayList<ArrayList<Double>> Measured = new ArrayList<ArrayList<Double>>();
		Measured.add(x);
		Measured.add(y);
		return Measured;
	}

	/**Function: Read measured NMOS data (Vgs, Vds, Ids) from outputNMOS.txt file
	 * Parameter: String path
	 * Return: a 2-d ArrayList, with each column being the measured Vgs, Vds, Ids*/
	public static ArrayList<ArrayList<Double>> readNMOS(String path){
		//Construct structures for restoring the file inputs
		ArrayList<Double> Vgs = new ArrayList<Double>();
		ArrayList<Double> Vds = new ArrayList<Double>();
		ArrayList<Double> Ids = new ArrayList<Double>();
		try {
			File file = new File(path);
			if(file.isFile() && file.exists()) { 
				// Create reading buffer
				BufferedReader br = new BufferedReader(new FileReader(file));
				String line = null;

				// Consumes first line in .txt, and ignore it
				br.readLine();

				// Split each line into [Vgs, Vds, Ids], and append them respectively to ArrayLists
				String subStr[] = new String[3];
				while((line = br.readLine()) != null) {
					subStr = line.split("\t");
					Vgs.add(Double.valueOf(subStr[0]));
					Vds.add(Double.valueOf(subStr[1]));
					Ids.add(Double.valueOf(subStr[2]));
				}
				br.close();
			} else {
				System.out.println("File doesn't exist!");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		// Construct the return value, a 2d ArrayList
		ArrayList<ArrayList<Double>> Measured = new ArrayList<ArrayList<Double>>();
		Measured.add(Vgs);
		Measured.add(Vds);
		Measured.add(Ids);
		return Measured;
	}

	/**Function: choose the best initial guess of parameters for EKV out of the given ranges above
	 * Parameters: int QN (or SC), int normalized_or_not, int modeltype, double tolerace
	 * Return: None
	 * @throws Exception */
	public static void initialGuess(int estType, int normType, int modelType, double tolerance) throws Exception {
		output("\n\t"+(normType==NORM ? "NORMALIZED" : "UNNORMALIZED")+(estType==QN ? ", Quasi-Newton:" : ", Secant:"));

		// Structures for iterative solvers results
		double V= 0;
		double guess[] = new double[3];
		double minV = Math.pow(10, 10);
		double minGuess[] = new double[3];

		for(int i=0; i<Is.length; i++) {
			guess[0] = Is[i];
			for(int j=0; j<Kappa.length; j++) {
				guess[1] = Kappa[j];
				for(int k=0; k<Vth.length; k++) {
					guess[2] = Vth[k];
					V = iterativeSolver.iterSolver(estType, normType, modelType, new Vector(guess), tolerance)[3];
					if(V < minV) {
						minV = V;
						minGuess = guess.clone();
					}
				}
			}
		}
		System.out.println(Arrays.toString(minGuess));
		output("\n\t\tOptimal initial guess [Is, Kappa, Vth] = "+Arrays.toString(minGuess)+", V = "+minV);
	}

	/* Methods for report operation*/
	/**Function: Create report, for the purpose of Sequentially streaming 
	 * 			inputs into reports'
	 * Parameters: String filePath, String content
	 * Return: None*/
	public static void createReport(String filePath, String content) {
		reportPath = filePath;
		try {
			FileWriter fw = new FileWriter(filePath,true);
			fw.write(content);
			fw.close();
		} catch (IOException e) {
			System.out.println("writing failure" + e);
		}
	}

	/**Function: Writing "content" into "filePath"
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

	/**Function: output the statistics for computational time and MEM cost
	 * Parameters: long mtime
	 * Return: None*/
	public static void computationCost(long mtime) {
		output("\n\t\t< STATISTICS >: Time cost = " + mtime/1000000 +
				"ms, MEM usage = "+ (runtime.totalMemory()-runtime.freeMemory())/1024/1024 +" Mb");
	}

	/**Function: output the convergence results into 
	 * Parameters: double res[]
	 * Return: None*/
	public static void convergenceObserve(double[] res) {
		// Iff there are > 1 iterations, then convergence could be observed
		if(res[4] != 0 && res[4] != 1) {
			output("\n\t\tConvergence Auto-Checking:\n\t\t\tTerminated by MAX_ITERATION: " + (res[0]==1?"YES":"NO") + 
					", Linear Convergence: " + (res[2]==1?"YES":"NO") + ", Quadratic Convergence: " + (res[1]==1?"YES":"NO"));
		}
		// Iff there are l<= 1 iterations, then convergence couldn't be observed
		else {
			output("\n\t\tConvergence Auto-Checking:\n\t\t\tTerminated by MAX_ITERATION: " + (res[0]==1?"YES":"NO") + 
					", Linear Convergence: " + "TOO_FEW_ITERATION_TO_OBSERVE" + ", Quadratic Convergence: " + "TOO_FEW_ITERATION_TO_OBSERVE");
		}
	}

	/**Function: output the iterative solver's results 
	 * Parameters: double[] res
	 * Return: None*/
	public static void result(double[] res) {
		if(iterativeSolver.MODEL == EKV)
			output("\n\t\tExtracted [Is, kappa, Vth] = ["+res[5]+", "+res[6]+", "+res[7]+"], V = "+res[3]+
					", ||delta|| = "+res[8]+", iteration # = "+(int)res[4]);
		else
			output("\n\t\tExtracted [a, m] = ["+res[5]+", "+res[6]+ "], V = "+res[3] +", ||delta|| = "+res[7]+
					", iteration # = " + (int)res[4]);
	}

}
