/** Last Update: 02/14/2018; Author: Yuan He (yh772); Platform: Eclipse, Java8 */


public class IntOverflow {

	public static void factorial() {
		try { 
			int i = 1, factorial = 1;
			Main.fileChaseFW("Second-level.txt","\n\nOUTPUT:" );
			while(IntOverflow.help_fact(i, factorial) ==0 ) {
				if(i%4 == 0)
					Main.fileChaseFW("second-level.txt","\n i = " + i + "   factorial = " + factorial);
				i++;
				factorial = factorial * i;
			}

			Main.fileChaseFW("second-level.txt","\n\nCONCLUSION:\n***"
					+ "with factorial calculation, overflow occurs at: n="+i+", factorial = "
					+factorial);

			Main.fileChaseFW("first-level.txt","\n***1. Integer overflow: "
					+ "with factorial calculation, overflow occurs at: n="+i+", factorial = "
					+factorial);

		}catch(Exception e) {
			System.out.println("Exception caught: " + e.getMessage());
		}

	}

	private static int help_fact(int i, int factorial) {
		double f = (double) factorial;
		while(f!=1) {
			f = f/i;
			i--;
			if((int)f==0) {
				return i;// meaning overflows here
			}
		}
		return 0;// meaning does not overflow
	}

}
