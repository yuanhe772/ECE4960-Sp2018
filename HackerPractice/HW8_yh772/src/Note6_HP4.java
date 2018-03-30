import java.util.Arrays;
/**
 * lec_HP4.java, ECE4960-HW7
 * Created by Yuan He(yh772) on 2018/03/23
 * Platform: Java 8, Eclipse, MacOS
 * Copyright © 2018 Yuan He. All rights reserved.
 * 
 * Note6: Generate v with distribution function of p(v) from random()
 */
public class Note6_HP4 {
	// Class varibles
	double u = 0; // u = F(v)
	double v = 0; // v is the variable in p(v)

	// Generate v
	public double generateV() {
		u = Math.random();
		v = -1/0.2 * Math.log(1-u);
		return v;
	}

	public static void main(String args[]) {
		
		double lambda = 0.2;
		
		// Create variables
		Note6_HP4 generator = new Note6_HP4();
		double V[] = new double[1000];
		double pV[] = new double[1000];
		double fV[] = new double[1000];
		
		// Create V
		for(int i=0; i<V.length; i++) {V[i] = generator.generateV();}

		// Sort V
		Arrays.sort(V);	

		// Assign pV, fV
		for(int i=0; i<V.length; i++) {
			pV[i] = lambda*Math.exp(-lambda*V[i]);
			fV[i] = 1 - Math.exp(-lambda*V[i]);
		}

		// Average each bin
		double averagePV[] = new double[(int) ((int) 10/0.5)];
		double averageFV[] = new double[(int) ((int) 10/0.5)];
		for(int i=0; i<averagePV.length; i++) {
			for(int j=i*50; j<i*50+50;j++) {
				averagePV[i] += pV[j];
				averageFV[i] += fV[i];
			}
			averagePV[i] /= 50;
			averageFV[i] /= 50;
		}

		// p(v) vs v:
		System.out.println(Arrays.toString(averagePV));

		// F(v) vs v:
		System.out.println(Arrays.toString(averageFV));

		// Then plot them in Python
	}
}
