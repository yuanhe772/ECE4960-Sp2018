/** Last Update: 02/10/2018; Author: Yuan He (yh772); Platform: Eclipse, Java8 */

public class HP5 {
	
	private static double x = 1.234567890123456 * Math.pow(10, -307); // normalized smallest number
	// The normalized number is above 4.9407*10^(-324)

	private static void gradualUnderflow1(double x) {
		// Decrease the normalized number to the range of denormals
		for (int i=1; i<20; i++) {
			x /= 10.0;
			System.out.println(x);  // or printf/cout with 16-digit format
		}
	}
	
	private static boolean gradualUnderflow2() {
		return ((1.0 * Math.pow(10, -320) - 2.0 * Math.pow(10, -320))!= 0);
	}

	public static void main(String args[]) {
		gradualUnderflow1(x);
		System.out.println("\nAnother implementation of testing the existence of gradual underflow:\n" + "(1.0 * 10^-320) - (2.0 * 10^-320) != 0 ?  " + gradualUnderflow2());
	}

}
