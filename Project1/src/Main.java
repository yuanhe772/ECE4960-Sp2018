/** Last Update: 02/15/2018; Author: Yuan He (yh772); Platform: Eclipse, Java8 */

import java.io.*;

public class Main {

	public static void main(String args[]) throws Exception{

		// Generating reports
		fileChaseFW("first-level.txt","First Level Report");
		fileChaseFW("second-level.txt","Second Level Report");


		// Experiments

		//1.
		fileChaseFW("second-level.txt","\n\n\n\n\n1. Integer overflow:");
		IntOverflow.factorial();

		//2.
		fileChaseFW("second-level.txt","\n\n\n\n\n2. Integer / 0:");
		IntDivided0.intDivided0();

		//3.
		fileChaseFW("second-level.txt","\n\n\n\n\n3. Single-precision float Overflow:");
		FloatOverflow.multiply10();
		FloatOverflow.floatIncrement();

		//4.
		fileChaseFW("second-level.txt","\n\n\n\n\n4. INF and NINF operations:");
		FloatINF.generateINF();
		FloatINF.divideINF();
		FloatINF.sinINF();
		FloatINF.expINF();
		FloatINF.propagateINF();
		FloatINF.interactINF();

		//5.
		fileChaseFW("second-level.txt","\n\n\n\n\n5. NaN operations:");
		NaN.generateNaN();
		NaN.propagateNaN();
		NaN.interactNaN();

		//6.
		fileChaseFW("second-level.txt","\n\n\n\n\n6. Signed 0 operations:");
		Signed0.check0();
		Signed0.log0();
		Signed0.sinx_x();

		//7.
		fileChaseFW("second-level.txt","\n\n\n\n\n7. Gradual Overflow:");
		Gradual.generateXY();
		Gradual.sinx_x();

		//8.
		fileChaseFW("second-level.txt","\n\n\n\n\n*8.  Pi with more than 30 digits: ");
		fileChaseFW("second-level.txt","\n\n  Therefore,  Pi with more than 30 digits = " + Pi.pi());
	}


	public static void fileChaseFW(String filePath, String content) {
		try {
			FileWriter fw = new FileWriter(filePath,true);
			fw.write(content);
			fw.close();
		} catch (IOException e) {
			System.out.println("writing failure" + e);
		}
	}
}
