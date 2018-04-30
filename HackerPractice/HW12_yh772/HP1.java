
/**
 * HP1.java, ECE4960-HW12
 * Created by Yuan He(yh772) on 2018/04/24
 * Platform: Java 8, Eclipse, MacOS
 * Copyright © 2018 Yuan He. All rights reserved.
 * 
 * Note 7, page 41:
 * 
 * Use the following 1D grid with zero Dirichlet boundary conditions at x = 0 and x = 4. 
 * The initial condition is n(x=1) = n(x=3) = 0 and n(x=2) = 10. 
 * Solve for n(x) for t from 2 to 5.
 */
public class HP1 {

	public static void main(String[] args) {
		double h = 1;
		double deltaT = 1;
		double D = 1;
		double ele1 = 1.0/deltaT + 2.0*D/(h*h);
		double ele2 = -D / (h*h);
		double ele3 = 1.0/deltaT;

		/* 0 Direchlet boundary:*/

		// Matrix A:
		double[] valueA = {ele1, ele2, ele2, ele1, ele2, ele2, ele1};
		int[] rowPtrA = {0, 2, 5, 7};
		int[] colIndA = {0, 1, 0, 1, 2, 1, 2};
		SparseMatrix A = new SparseMatrix(valueA, rowPtrA, colIndA);

		// Matrix B:
		double[] valueB = {ele3, ele3, ele3};
		int[] rowPtrB = {0, 1, 2, 3};
		int[] colIndB = {0, 1, 2};
		SparseMatrix B = new SparseMatrix(valueB, rowPtrB, colIndB);

		// Vector n:
		double[] valueN = {0, 10, 0};
		Vector n = new Vector(valueN);
		Vector b = B.product(n);

		System.out.println("Direchlet boundary:");
		for(int i=2; i<=5;i++) {
			b = B.product(n);
			n = Jacobi.solver(A,b);
			System.out.println("    When t = "+i+": \n        n1 = "+n.v[0]+", n2 = "+n.v[1]+", n3 = "+n.v[2]);
		}

		/* 0 Neumann boundary:*/

		// Matrix A:
		double[] valueAA = {ele1-1, ele2, ele2, ele1, ele2, ele2, ele1-1};
		SparseMatrix AA = new SparseMatrix(valueAA, rowPtrA, colIndA);
		double[] valueNN = {0, 10, 0};

		// Vector N:
		Vector nn = new Vector(valueNN);
		b = B.product(nn);

		System.out.println("Neumann boundary:");
		for(int i=2; i<=5;i++) {
			b = B.product(nn);
			nn = Jacobi.solver(AA,b);
			System.out.println("    When t = "+i+": \n        n1 = "+nn.v[0]+", n2 = "+nn.v[1]+", n3 = "+nn.v[2]);
		}
	}
}
