import java.util.ArrayList;
import java.util.Arrays;

/**
 * iterativeSolver.java, ECE4960-P3
 * Created by Yuan He(yh772) on 2018/04/05
 * Platform: Java 8, Eclipse, MacOS
 * Copyright © 2018 Yuan He. All rights reserved.
 * 
 * Project 3, iterative solver:
 * The core program for project3, implements the iterative solver
 * Integrated with the gradientEsti script (which calculates the delta V and Hessian Matrix)
 */

public class iterativeSolver {

	/* Class Variants: The file inputs, and DEFINE values*/
	static final int QN = 1;
	static final int SC = 2;
	static final int NORM = 3;
	static final int UNNORM = 4;
	static final int POW = 5;
	static final int EKV = 6;
	static int MODEL = 1;
	static boolean printEnable = true;
	static int MAX_ITERATION = 2000;

	// The measured data for power law, and NMOS parameter extraction
	static final ArrayList<ArrayList<Double>> powMeasure = dataIO.powLawData(10, 10, -0.5);
	static final ArrayList<ArrayList<Double>> NMOSMeasure = dataIO.readNMOS("outputNMOS.txt");
	static final double VT = 0.026;


	/* Class Methods:*/
	/**Function: the Loss Function(the objective function), V = sum (YModel - YMeasure)^2
	 * 			 Where the Y_model is the calculated Ids (with the given parameters and measured Vgs, Vds)
	 * 			 Y_measure is the measured Ids.
	 * Parameters: Vector para = [Is, kappa, Vth], int normalType = either NORM or UNNORM
	 * Return: double V
	 * @throws Exception */
	public static double V(Vector para, int normalType) throws Exception {
		// The structures for calculating the loss function
		double YModel = 0;
		double YMeas = 0;
		double V = 0;
		int dataSize = 0;

		// Iff the current model is NMOS ekv model, the loss function would be:
		if(MODEL == EKV) {
			dataSize = NMOSMeasure.get(0).size();
			// Iff data not normalized
			if(normalType == UNNORM) {
				for(int i=0; i<dataSize; i++) {
					YModel = yEKVModel(para, NMOSMeasure.get(0).get(i), NMOSMeasure.get(1).get(i));
					YMeas = NMOSMeasure.get(2).get(i);
					V += Math.pow(YModel - YMeas,2);
				}
			}
			// Iff data normalized
			else if(normalType == NORM) {
				for(int i=0; i<dataSize; i++) {
					YModel = yEKVModel(para, NMOSMeasure.get(0).get(i), NMOSMeasure.get(1).get(i));
					YMeas = NMOSMeasure.get(2).get(i);
					V += Math.pow((YModel - YMeas)/YMeas,2);
				}
			}
			else throw new Exception("\nWrong gradient estimation type!!! Please input: QN or SC.");
		}
		// Iff the current model is Power Law model, the loss function would be:
		else if(MODEL == POW) {
			dataSize = powMeasure.get(0).size();
			for(int i=0; i<dataSize; i++) {
				YModel = para.v[0]*Math.pow(powMeasure.get(0).get(i), para.v[1]);
				YMeas = powMeasure.get(1).get(i);
				V += Math.pow(YModel - YMeas,2);
			}
		}
		else throw new Exception("\nWrong model type!!! Please input: EKV or POW.");
		return V;
	}

	/**Function: calculate a single value of Y_model, with given parameters, and a pair of (Vgs, Vds)
	 * Parameters: Vector para = [Is, kappa, Vth], double Vgs, double Vds
	 * Return: double res, ekvModel's output*/
	public static double yEKVModel(Vector para, double Vgs, double Vds) {
		double expo1 = Math.exp((para.v[1]*(Vgs - para.v[2])) / (2*VT));
		double expo2 = Math.exp((para.v[1]*(Vgs - para.v[2]) - Vds) / (2*VT));
		return para.v[0] * (Math.log(1+expo1) * Math.log(1+expo1) - Math.log(1+expo2) * Math.log(1+expo2));
	}

	/**Function: the core function of this script, an iterative solver with line search to solve a 
	 * 			 nonlinear matrix (in this case, to extract parameters)
	 * Parameters: int type (QN or SC), int normType (Normalized data or not), Vector paraGuess[], double tolerance
	 * Return: double[] convResult = new double [8];
	 * 								 convResult[0] = Iterative solver's MAX_ITERATION termination indicator (1 - terminated, 0 - not terminated)
	 * 								 convResult[1] = V's quadratic convergence indicator (1 - converged, 0 - fail to converge)
	 * 								 convResult[2] = V's linear convergence indicator (if more than 90% iterations ||V2||<||V1||, considered linear-convergence)
	 * 								 convResult[3] = The final loss function's value
	 * 								 convResult[4] = How many iterations it took for converging
	 * 								 convResult[5-7]=The extracted parameters
	 * 								 convResult[8] = The final || delta ||
	 * @throws Exception */
	public static double[] iterSolver(int estType, int normType, int modelType, Vector paraGuess, double tolerance) 
			throws Exception {
		/* Method Variable Initialization: */
		// Create delta V vector, Hessian V matrix, and the inversed Hessian V matrix
		Vector delV = new Vector(paraGuess.len);
		FullMatrix hessV = new FullMatrix(paraGuess.len);
		FullMatrix hessInverse = new FullMatrix(paraGuess.len);	

		// Create structures for iterative solver, and its convergence check
		Vector deltaPara = new Vector(paraGuess.v);
		Vector preprevPara = new Vector(paraGuess.v);
		Vector prevPara = new Vector(paraGuess.len);
		Vector currPara = new Vector(paraGuess.v);
		int count = 0;
		MODEL = modelType;
		int lineConv = 1;

		// Create scalar, and norms for line search
		double t = 1;
		double norm1 = 0;
		double norm2 = 0;

		// Iterative solver, two tolerances: 1. the loss function's value;  2. || deltaPara / para ||
		while(V(currPara, normType) > Math.pow(10, -7) && increMag(deltaPara, currPara) > tolerance) {
			count += 1;
			// Assign delV and hessV according to different gradient estimation type
			if(estType == QN) {
				delV = gradientEsti.delQN(currPara, normType);
				hessV = gradientEsti.hessianQN(currPara, normType);
			}
			else if(estType == SC) {
				delV = gradientEsti.delSC(currPara, normType);
				hessV = gradientEsti.hessianSC(currPara, normType);
			}
			else throw new Exception("\nWrong gradient estimation type!!! Please input: QN or SC.");

			// Calculate Hessian matrix's inverse, and delta parameter
			if(MODEL == EKV) 
				hessInverse = hessV.inverseRank3();
			else if(MODEL == POW) 
				hessInverse = hessV.inverseRank2();
			deltaPara = (hessInverse.product(delV)).scale(-1);

			// Line search to determine the scalar t
			t = 1;
			norm1 = V(currPara.add(deltaPara, t), normType);
			norm2 = V(currPara.add(deltaPara, t/2), normType);
			while(norm1 > norm2) {
				norm1 = norm2;
				t/=2;
				norm2 = V(currPara.add(deltaPara, t/2), normType);}

			// Update parameters by para = para + t*deltaPara, and update sensitivity Vector
			currPara = currPara.add(deltaPara, t);

			// Convergence observation:
			if(printEnable == true) {
				System.out.println("The "+count + "th iteration of model "+(MODEL==POW ? "Power Law ":"EKV ") + "with "+
						(estType==1?"Quasi-Newton":"Secant") +
						" method:\n    "+(MODEL==POW ? "[c, m] = " : "[Is, kappa, Vth] = ") + Arrays.toString(currPara.v)  + 
						",\tsensitivity = " + Arrays.toString(paraSensi(deltaPara, currPara, normType).v) + 
						",\t|| V || = " + V(currPara, normType) + 
						",\t|| relative delta || = "+increMag(deltaPara, prevPara));
			}

			// update the previous V, previous parameters, and the linear convergence indicator
			preprevPara = prevPara;
			prevPara = currPara;
			lineConv += lineConv(prevPara, currPara, normType) == 0? 1:0;

			// Terminate the iterative solver, and return
			if(count > MAX_ITERATION) {
				// Iff Power Law is running, return a para[] of length == 2
				if(MODEL == POW) {
					double convResult[] = {1, quadConv(preprevPara, prevPara, currPara, normType), (lineConv/count < 0.1) ? 1:0, 
							V(currPara, normType), count, currPara.v[0], currPara.v[1], increMag(deltaPara, currPara)};
					return convResult;
				}
				// Iff EKV is running, return a para[] of length == 3
				else {
					double convResult[] = {1, quadConv(preprevPara, prevPara, currPara, normType), (lineConv/count < 0.1) ? 1:0, 
							V(currPara, normType), count, currPara.v[0], currPara.v[1], currPara.v[2], increMag(deltaPara, currPara)};
					return convResult;
				}
			}
		}

		// Converge and return the result
		if(MODEL == POW) {
			// Iff Power Law is running, return a para[] of length == 2
			double convResult[] = {0, quadConv(preprevPara, prevPara, currPara, normType), (lineConv/count < 0.1) ? 1:0, 
					V(currPara, normType), count, currPara.v[0], currPara.v[1], increMag(deltaPara, currPara)};
			return convResult;
		}
		else{
			// Iff EKV is running, return a para[] of length == 3
			double convResult[] = {0, quadConv(preprevPara, prevPara, currPara, normType), (lineConv/count < 0.1) ? 1:0, 
					V(currPara, normType), count, currPara.v[0], currPara.v[1], currPara.v[2], increMag(deltaPara, currPara)};
			return convResult;
		}
	}

	/* Calculation of increment vector magnitude, parameter sensitivity, and quadratic convergence observation*/
	/**Function: Increment Vector magnitude || delta || = sum (delta para / para)^2
	 * Parameters: Vector deltaPara = [delta Is, delta kappa, delta Vth], Vector oldPara = [Is, kappa, vth]
	 * Return: double magnitude*/
	public static double increMag(Vector deltaPara, Vector oldPara) {
		double mag=0;
		for(int i=0; i<oldPara.len; i++) {
			mag += Math.pow((deltaPara.v[i]/oldPara.v[i]),2);
		}
		return mag;
	}

	/**Function: Parameter sensitivity paraSensi = ((perturbedV - originalV)/originalV) / (deltaPara/para),
	 * 			 with the numerator being the difference of V, and the denominator being the difference of parameter,
	 * 			 Reflecting how a perturbed parameter can change the objective function V
	 * Parameters: Vector deltaPara = [delta Is, delta kappa, delta Vth], Vector para = [Is, kappa, vth]
	 * Return: Vector paraSensitivity = [Is sensitivity, kappa sensitivity, Vth sensitivity]
	 * @throws Exception */
	public static Vector paraSensi(Vector deltaPara, Vector para, int normType) throws Exception {
		// Structure for storing sensitivity vector
		double sens[] = new double[para.len];

		// Calculating the sensitivity,
		Vector perturbed = new Vector(para.len);
		double numerator = 0;
		double denominator = 0;
		for(int i=0; i<para.len; i++) {
			// The parameter vector that being perturbed once with the deltaPara
			perturbed.v = para.v.clone();
			perturbed.v[i] += deltaPara.v[i];

			// The calculation of sensitivity
			numerator = V(perturbed, normType)/ V(para, normType);
			denominator = (deltaPara.v[i] + para.v[i]) / para.v[i];
			sens[i] = numerator / denominator;
		}
		return new Vector(sens);
	}

	/**Function: check if an iterative solver is quadratically converging, by looking at:
	 * 			 When k -> infinity, |V(k) - V*| / |V(k-1) - V*|^2 == CONSTANT,
	 * 			 where V* is the loss function for true parameters, so V* == 0 in this case.
	 * 			 Therefore the quadratic convergence formula above could be degenerated into: 
	 * 			 k -> infinity, |V(k)| / |V(k-1)|^2 == CONSTANT.
	 * 			 The iteration time is limited, so if |prevQuad - currQuad| / currQuad < 0.1%,
	 * 			 Then consider it is quadratically converging
	 * Parameters: Vector preprevPara, Vector prevPara, Vector currPara, int normType
	 * Return: int convergence, 1 for convergence, and 0 for fail to converge
	 * @throws Exception */
	public static int quadConv(Vector preprevPara, Vector prevPara, Vector currPara, int normType) throws Exception {
		// Calculate the previous and current Quadratic convergence indicator
		double prevQuad = V(prevPara, normType) / (V(preprevPara, normType)*V(preprevPara, normType));
		double currQuad = V(currPara, normType) / (V(prevPara, normType)*V(prevPara, normType));

		// check if |delta Quad| / currQuad is within tolerance
		if(Math.abs(prevQuad - currQuad)/currQuad < Math.pow(10,-7))
			return 1;
		else return 0;
	}

	/**Function:help check in each iteration, if V is linearly converging to 0
	 * Parameters: Vector prevPara, Vector currParaint normType 
	 * Return: int convergence, where 1 for convergence and 0 for fail to converge
	 * @throws Exception */
	public static int lineConv(Vector prevPara, Vector currPara, int normType) throws Exception {
		int convRes = V(currPara, normType) < 1.00001*V(prevPara, normType)? 1 : 0;
		return convRes;
	}

	/**Function: disable or enable the output of detailed logs
	 * Parameters: boolean en
	 * Return: None*/
	public static void logModeEnable(boolean en) {
		printEnable = en;
	}

	/**Function: change the MAX_ITERATION
	 * Parameters: int iter
	 * Return: None*/
	public static void changeMaxIter(int iter) {
		MAX_ITERATION = iter;
	}
}
