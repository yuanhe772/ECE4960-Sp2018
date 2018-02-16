/** Last Update: 02/14/2018; Author: Yuan He (yh772); Platform: Eclipse, Java8 */


public class IntDivided0 {

	public static void intDivided0() {
		int x = 1;
		try { 
			x = x/(1-1);
		}catch(ArithmeticException e) {
			Main.fileChaseFW("second-level.txt","\n\nOUTPUT: \n***trying with 1/0, the Exception was caught"
					+ " as: " + "ArithmeticException: "+e.getMessage());
			Main.fileChaseFW("first-level.txt", "\n***2. Integer / 0: "
					+ "ArithmeticException: "+e.getMessage()+" detected");
		}
	}

}
