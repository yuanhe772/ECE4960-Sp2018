/** Last Update: 02/15/2018; Author: Yuan He (yh772); Platform: Eclipse, Java8 */


public class Signed0 {

	static float p0 = 1/FloatINF.INF;
	static float n0 = 1/FloatINF.NINF;
	static int flag4 = 0;
	static int flag5 = 0;

	public static void check0() {

		try {
			if( 1/p0 < 0 || Math.abs(p0) != 0) {
				flag4 = 1;
				throw new Exception("3");
			}
			if( 1/n0 > 0 || Math.abs(p0) != 0) {
				flag5 = 1;
				throw new Exception("4");
			}
			Main.fileChaseFW("first-level.txt", "\n6. Signed 0 operations: correct. ");
		}catch(Exception e) {
			if(e.getMessage().toString().equals("4")) {
				System.out.println("\nPlease regenerate +0");
			}
			else if(e.getMessage().toString().equals("5")) {
				System.out.println("\nPlease regenerate -0");
			}
			Main.fileChaseFW("first-level.txt", "\n***6. Signed 0 operations: incorrect. ");
		}
	}

	public static void log0() {
		if(flag4 == 1 || flag5 == 1) 
			return;

		double a1 = Math.log(p0);
		double a2 = Math.log(n0);
		double a3 = Math.log(0);

		Main.fileChaseFW("Second-level.txt","\n\n6.1\n\nOUTPUT:" );
		Main.fileChaseFW("Second-level.txt","\nlog(+0) = " + a1);
		Main.fileChaseFW("Second-level.txt","\nlog(-0) = " + a2);
		Main.fileChaseFW("Second-level.txt","\nlog(0) = " + a3);

		if(a1 == Float.NEGATIVE_INFINITY && a2 == Float.NEGATIVE_INFINITY 
				&& a3 == Float.NEGATIVE_INFINITY) {
			Main.fileChaseFW("Second-level.txt", "\n\nCONCLUSION:\n log(-0), log(+0), log(0) are all NINF, correct");
		}
		else {
			Main.fileChaseFW("Second-level.txt", "\n\nCONCLUSION:\n*** One of log(-0), log(+0), log(0) is not NINF, incorrect");
		}
	}

	public static void sinx_x() {
		if(flag4 == 1 || flag5 == 1) 
			return;

		double a1 = Math.sin(p0)/p0;
		double a2 = Math.sin(n0)/n0;
		double a3 = Math.sin(n0)/Math.abs(n0);

		Main.fileChaseFW("Second-level.txt","\n\n6.2\n\nOUTPUT:" );
		Main.fileChaseFW("Second-level.txt","\nsin(+0)/(+0) = " + a1);
		Main.fileChaseFW("Second-level.txt","\nsin(-0)/(-0) = " + a2);
		Main.fileChaseFW("Second-level.txt","\nsin(-0)/|-0| = " + a3);

		if(a1 != a1 && a2 != a2 && a3 != a3) {
			Main.fileChaseFW("Second-level.txt", "\n\nCONCLUSION:\n sin(-0)/0, sin(+0)/0, sin(-0)/|-0| are all NaN, correct");
		}
		else {
			Main.fileChaseFW("Second-level.txt", "\n\nCONCLUSION:\n*** One of sin(-0)/0, sin(+0)/0, sin(-0)/|-0| is not NaN, incorrect");
		}
	}
}
