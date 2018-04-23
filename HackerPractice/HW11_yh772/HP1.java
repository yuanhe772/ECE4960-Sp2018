import java.util.Arrays;

/**
 * HP1.java, ECE4960-HW11
 * Created by Yuan He(yh772) on 2018/04/17
 * Platform: Java 8, Eclipse, MacOS
 * Copyright © 2018 Yuan He. All rights reserved.
 * 
 * Note 7, Page 20:
 * solve the discretized Poisson equation
 */
public class HP1 {

	public static void main(String args[]) {
		
		// Both conditions are Dirichlet conditions:
		System.out.println("Both conditions are Dirichlet conditions:");
		double[][] AA = {{-2,1,0,0}, {1,-2,1,0}, {0,1,-2,1}, {0,0,1,-2}};
		double[] VV = {-0.04, 0, 0, 0};
		FullMatrix A = new FullMatrix(AA);
		Vector V = new Vector(VV);
		Vector res = directMatrixSolver.solve(A, V);
		System.out.println("\t[Phi(1), Phi(2), Phi(3), Phi(4)] = "+Arrays.toString(res.v));
		A = new FullMatrix(AA);
		V = new Vector(VV);
		System.out.println("\t|Error| = "+computeError(A, res, V));
		
		// One is Dirichlet condition, the other is Neumann condition:
		System.out.println("One is Dirichlet condition, the other is Neumann condition:");
		AA[3][3] = -1;
		A.full = AA;
		res = directMatrixSolver.solve(A, V);
		System.out.println("\t[Phi(1), Phi(2), Phi(3), Phi(4)] = "+Arrays.toString(res.v));
		A = new FullMatrix(AA);
		V = new Vector(VV);
		System.out.println("\t|Error| = "+computeError(A, res, V));
	}
	
	public static double computeError(FullMatrix A, Vector Res, Vector b) {
		Vector res= A.product(Res);
		return (res.add(b, -1)).norm();
	}
}
