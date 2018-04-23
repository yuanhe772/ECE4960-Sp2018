
/**
 * HP2.java, ECE4960-HW11
 * Created by Yuan He(yh772) on 2018/04/22
 * Platform: Java 8, Eclipse, MacOS
 * Copyright © 2018 Yuan He. All rights reserved.
 * 
 * Note 7, Page 34:
 * Give h = 10μm, solve first the homogeneous Poisson equation (reverse-biased at 1V).
 */
public class HP2 {
	
	public static void main(String[] args) {
		// The constants
		Vector Psi= new Vector(9);
		double ni= 1.5e10;
		double Psi_0=1.0;
		double ND= 1e15/ni;
		double NA= 1e17/ni;
		double LD= 2.4e-3;
		double h= 1e-5/LD;

		// Calculate the Poisson Eq.
		double norm= Norm(Psi, Psi_0, h, ND, NA);
		Vector deltaPsi= new Vector(9);
		double deltaPsiNorm= 1;
		while(norm>Math.pow(10, -7) && deltaPsiNorm>Math.pow(10, -7)) {
			deltaPsi = DeltaPsi(Psi, Psi_0, h, ND, NA);
			deltaPsiNorm = deltaPsi.norm();
			Psi=Psi.add(deltaPsi, 1);
		}
	}
	
	// Calculate the Phi
	public static Vector phi(Vector Psi, double Psi_0, double h, double ND, double NA) {
		double[] v= Psi.v;
		double fValue[] = new double[9];
		fValue[0] = v[1]+v[0]*(-4)+v[3]+h*h*(Math.exp(-v[0])-Math.exp(v[0])+ND)+2*Psi_0; 
		fValue[1] = (v[0]+v[1]*(-4)+v[2]+v[4]+h*h*(Math.exp(-v[1])-Math.exp(v[1])+ND)+Psi_0);
		fValue[2] = (v[1]+v[2]*(-4)+v[5]+h*h*(Math.exp(-v[2])-Math.exp(v[2])-NA));
		fValue[3] = (v[0]+v[3]*(-4)+v[4]+v[6]+h*h*(Math.exp(-v[3])-Math.exp(v[3])+ND));
		fValue[4] = (v[1]+v[3]+v[4]*(-4)+v[5]+v[7]+h*h*(Math.exp(-v[4])-Math.exp(v[4])-NA));
		fValue[5] = (v[2]+v[5]*(-4)+v[4]+v[8]+h*h*(Math.exp(-v[5])-Math.exp(v[5])-NA));
		fValue[6] = (v[3]+v[6]*(-4)+v[7]+h*h*(Math.exp(-v[6])-Math.exp(v[6])-NA));
		fValue[7] = (v[4]+v[6]+v[7]*(-4)+v[8]+h*h*(Math.exp(-v[7])-Math.exp(v[7])-NA));
		fValue[8] = (v[5]+v[7]+v[8]*(-4)+h*h*(Math.exp(-v[8])-Math.exp(v[8])-NA));
		return new Vector(fValue);
	} 
	
	// Calculate the Jacobian matrix in SparseMatrix form
	public static SparseMatrix Jacobian(Vector psi, double psi0, double h) {
		// Construct sparse matrix structures
		int[] colInd = {0,1,3,0,1,2,4,1,2,5,0,3,4,6,1,3,4,5,7,2,4,5,8,3,6,7,4,6,7,8,5,7,8};
		int[] rowPtr = {0,3,7,10,14,19,23,26,30,33};
		double[] value= new double[33];
		
		double[] Psi_v= psi.v;
		int i = 0;
		int k =0;
		while(i<=32) {
			if(i==0 || i==4 || i==8 || i==11 || i==16 || i==21 || i==24 || i==28 || i==32) {
				value[i] = -4-h*h*(Math.exp(-Psi_v[k])+Math.exp(Psi_v[k]));
				k += 1;
			}
			else {
				value[i] = 1.0;
			}
			i += 1;
		}
		return new SparseMatrix(value, rowPtr, colInd);
	}
	
	// Calculate the delta Psi using iterative solver
	public static Vector DeltaPsi(Vector psi, double psi0, double h, double ND, double NA) {
		Vector phi= phi(psi, psi0, h, ND, NA);
		SparseMatrix jacobian= Jacobian(psi, psi0, h);
		return Jacobi.solver(jacobian, phi);
	}
	
	// Calculate the norm
	public static double Norm(Vector psi, double psi0, double h, double ND, double NA) {
		Vector v= phi(psi, psi0, h, ND, NA);
		return v.norm();
	}
}
