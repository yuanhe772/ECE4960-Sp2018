import java.util.Arrays;

/**
 * HP2.java, ECE4960-HW12
 * Created by Yuan He(yh772) on 2018/04/26
 * Platform: Java 8, Eclipse, MacOS
 * Copyright © 2018 Yuan He. All rights reserved.
 * 
 * Note 7, page 57:
 * Use the following 1D grid with zero Dirichlet boundary conditions at x = 0 and x = 6. 
 * The initial condition is u(x=2) = 10 and u(x) = 0 otherwise. 
 * Solve for u(t, x) for t from 2 to 5.
 */
public class HP2 {

	static double delta_x = 1;
	static double delta_t = 1;
	static double v = 1;
	static double[] uu = {0, 10, 0, 0, 0};
	static Vector u = new Vector(uu); 
	static double ele1 = v/delta_x;
	static double ele2 = 1.0/delta_t;
	static double ele3_1 = ele1 + ele2;
	static double ele3_2 = -ele1 + ele2;

	public static void DirechletWind(String EulerType, String UpDown) {
		// Matrix A:
		double[][] AA = new double[u.len][u.len];
		if(EulerType.equals("Forward")) {
			for (int i=0; i<u.len; i++) {AA[i][i] = ele2;}
		}
		else if (EulerType.equals("Backward") && UpDown.equals("Up")) {
			for (int i=0; i<u.len; i++) {
				AA[i][i] = ele3_2;
				if(i!=0) AA[i][i-1] = ele1;
			}
		}
		else if (EulerType.equals("Forward") && UpDown.equals("Down")) {
			for (int i=0; i<u.len; i++) {
				AA[i][i] = ele3_2;
				if(i!=u.len-1) AA[i][i+1] = -ele1;
			}
		}
		FullMatrix A = new FullMatrix(AA);

		// Matrix B:
		double[][] BB = new double[u.len][u.len];
		if(EulerType.equals("Forward") && UpDown.equals("Up")) {
			for (int i=0; i<u.len; i++) {
				BB[i][i] = ele3_1;
				if(i!=0) BB[i][i-1] = -ele1;
			}
		}
		else if (EulerType.equals("Backward")){
			for (int i=0; i<u.len; i++) {BB[i][i] = ele2;}
		}
		else if (EulerType.equals("Forward") && UpDown.equals("Down")) {
			for (int i=0; i<u.len; i++) {
				BB[i][i] = ele3_2;
				if(i!=u.len-1) {BB[i][i+1] = ele1;}
			}
		}
		FullMatrix B = new FullMatrix(BB);

		// Vector b:
		Vector b = B.product(u);

		u = directMatrixSolver.solve(A, b);
		for(int i=2; i<=5;i++) {
			b = B.product(u);
			u = directMatrixSolver.solve(A, b);
			System.out.println("    When t = "+i+": \n        [u1, u2, u3, u4, u5] = "+Arrays.toString(u.v));
		}
	}


	public static void NeumannUpForward() {
		// Matrix A:
		double[][] AA = new double[u.len][u.len];
		for (int i=0; i<u.len; i++) {
			AA[i][i] = ele2;
		}
		FullMatrix A = new FullMatrix(AA);

		// Matrix B:
		double[][] BB = new double[u.len][u.len];
		for (int i=0; i<u.len; i++) {
			if(i==0) BB[i][i] = ele2;
			else {
				BB[i][i] = ele3_1;
				BB[i][i-1] = -ele1;
			}
		}
		FullMatrix B = new FullMatrix(BB);

		// Vector b
		Vector b = B.product(u);

		u = directMatrixSolver.solve(A, b);
		for(int i=2; i<=10;i++) {
			b = B.product(u);
			u = directMatrixSolver.solve(A, b);
			System.out.println("    When t = "+i+": \n        [u1, u2, u3, u4, u5] = "+Arrays.toString(u.v));
		}
	}

	public static void main(String[] args) {
		/* Direchlet boundary:*/
		System.out.println("Up-winding Forward Euler:");
		DirechletWind("Forward", "Up");

		System.out.println("\nUp-winding Backward Euler:");
		u = new Vector(uu); 
		DirechletWind("Backward", "Up");

		System.out.println("\nDown-winding Forward Euler:");
		u = new Vector(uu); 
		DirechletWind("Forward", "Down");

		System.out.println("\nDown-winding Backward Euler:");
		u = new Vector(uu); 
		DirechletWind("Backward", "Down");

		/* Neumann boundary:*/
		System.out.println("\nNeumann: Upwinding Forward Euler:");
		u = new Vector(uu); 
		NeumannUpForward();
	}
}
