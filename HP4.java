/** Last Update: 02/10/2018; Author: Yuan He (yh772); Platform: Eclipse, Java8 */

public class HP4 {
	// -0 and +0 are the same for IEEE 754, therefore when it comes to
	//compare -0 and +0, transfer it into compare INF and NINF
	
	private static boolean isPositiveZero(double x) {
		return (1/x == Double.POSITIVE_INFINITY) ? true : false;
	}

	private static boolean isNegativeZero(double x) {
		return (1/x == Double.NEGATIVE_INFINITY) ? true : false;
	}

	private static boolean isNaN(double x) {
		return (x == Double.NaN) ? true : false;
	}

	private static boolean isINF(double x) {
		return (x == Double.POSITIVE_INFINITY) ? true : false;
	}

	private static boolean isNINF(double x) {
		return (x == Double.NEGATIVE_INFINITY) ? true : false;
	}

	public static void main (String args[]) {
		double t[] = {1.0, -1.0, Double.MAX_VALUE, -1*Double.MAX_VALUE, 1/Double.POSITIVE_INFINITY, 1/Double.NEGATIVE_INFINITY,
				Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NaN};

		int l = t.length;
		for(int i=0; i<l; i++) {
			System.out.println(t[i] + " isPositiveZero? " + isPositiveZero(t[i]) + "\n" +
					t[i] + " isNegativeZero? "  + isNegativeZero(t[i]) + "\n" + 
					t[i] + " isNINF? " + isNINF(t[i]) + "\n" +
					t[i] + " isINF? " + isINF(t[i]) + "\n"+
					t[i] + " isNaN? " + isNaN(t[i]) + "\n");
		}
	}
}
