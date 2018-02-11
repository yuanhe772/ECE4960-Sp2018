/** Last Update: 02/10/2018; Author: Yuan He (yh772); Platform: Eclipse, Java8 */

public class HP2 {
	public static void main(String args[]) {
		// Genera8ng NaN and INF in double
		double x = 0.0, y = 0.0, doubleResult1, doubleResult2; 
		doubleResult1 = 1/x; // Output: Infinity
		doubleResult2 = y/x; // Output: NaN
		System.out.println("double-Result 1 = " + doubleResult1 + "\ndouble-Result 2 = " + doubleResult2);
		
		// Observe NaN and INF handling in integers //
		long m = 0, n = 0, intResult1, intResult2; 
		intResult1 = 1/m; // Output: ArithmeticExcp: / by zero
		intResult2 = m/n; // Output: ArithmeticExcp: / by zero
		System.out.println("int-Result 1 = " + intResult1 + "; int-Result 2 = " + intResult2);
	}
}
