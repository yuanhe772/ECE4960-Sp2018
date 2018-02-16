/** Last Update: 02/15/2018; Author: Yuan He (yh772); Platform: Eclipse, Java8 */


public class FloatINF {

	static float INF = (float) (1.0/0);
	static float NINF = (float) (-1.0/0);
	static int flag1 = 0;
	static int flag2 = 0;
	static int count = 0;

	public static void generateINF() throws Exception {

		try{
			if(! FloatINF.checkINF(INF)) {
				flag1 = 1;
				throw new Exception("1");
			}
			if( ! FloatINF.checkINF(NINF)) {
				flag2 = 1;
				throw new Exception("2");
			}

			Main.fileChaseFW("first-level.txt", "\n4. INF and NINF operations: correct. ");

		}catch(Exception e){
			if(e.getMessage().toString().equals("1")) {
				System.out.println("\nPlease regenerate the INF");
			}
			else if(e.getMessage().toString().equals("2")) {
				System.out.println("\nPlease regenerate the NINF");
			}
			Main.fileChaseFW("first-level.txt", "\n***4. INF and NINF operations: incorrect. ");
		}
	}

	private static boolean checkINF(float f) {
		if (f > 0) 
			return 1/f==0 ? true:false;
		else 
			return 1/f==0 ? true:false;
	}

	public static void divideINF(){
		if(flag1 == 1 || flag2 == 1)
			return;

		float a1 = 1/INF;
		float a2 = 1/NINF;

		Main.fileChaseFW("Second-level.txt","\n\n4.1\n\nOUTPUT:" );
		Main.fileChaseFW("Second-level.txt","\n1/INF = " + a1);
		Main.fileChaseFW("Second-level.txt","\n1/NINF = " + a2);

		if(a1 == 0 && a2 == 0) {
			Main.fileChaseFW("Second-level.txt", "\n\nCONCLUSION:\n 1/INF == 0, 1/NINF == -0, correct");
		}
		else {
			Main.fileChaseFW("Second-level.txt", "\n\nCONCLUSION:\n*** 1/INF == 0 || 1/NINF == -0, incorrect");
		}
		return;
	}

	public static void sinINF(){
		if(flag1 == 1 || flag2 == 1)
			return;

		double a1 = Math.sin(INF);
		double a2 = Math.sin(NINF);

		Main.fileChaseFW("Second-level.txt","\n\n4.2\n\nOUTPUT:" );
		Main.fileChaseFW("Second-level.txt","\nsin(INF) = " + a1);
		Main.fileChaseFW("Second-level.txt","\nsin(NINF) = " + a2);

		if(a1 != a1 && a2 != a2) {
			Main.fileChaseFW("Second-level.txt", "\n\nCONCLLUSION:\n sin(INF) and sin(NINF) are NaN, correct");
		}
		else {
			Main.fileChaseFW("Second-level.txt", "\n\nCONCLUSION:\n*** sin(INF) not NaN || sin(NINF) not NaN, incorrect");
		}

		return;
	}

	public static void expINF(){
		if(flag1 == 1 || flag2 == 1)
			return;

		double a1 = Math.exp(INF);
		double a2 = Math.exp(NINF);

		Main.fileChaseFW("Second-level.txt","\n\n4.3\n\nOUTPUT:" );
		Main.fileChaseFW("Second-level.txt","\nexp(INF) = " + a1);
		Main.fileChaseFW("Second-level.txt","\nexp(INF) = " + a2);

		if(a1 == Double.POSITIVE_INFINITY && a2 == 0) {
			Main.fileChaseFW("Second-level.txt", "\n\nCONCLUSION:\n exp(INF) is infinity, exp(NINF) is 0, correct");
		}
		else {
			Main.fileChaseFW("Second-level.txt", "\n\nCONCLUSION:\n*** exp(INF) not infinity || exp(NINF) not 0, correct");
		}

		return;
	}

	public static void propagateINF() {
		if(flag1 == 1 || flag2 == 1)
			return;

		float a1 = INF + INF;
		float a2 = INF - INF;
		float a3 = INF * 3;
		float a4 = NINF + NINF;
		float a5 = NINF - NINF;
		float a6 = NINF * 3;

		Main.fileChaseFW("Second-level.txt","\n\n4.4\n\nOUTPUT:" );
		Main.fileChaseFW("Second-level.txt","\nINF + INF = " + a1 );
		Main.fileChaseFW("Second-level.txt","\nINF - INF = " + a2 );
		Main.fileChaseFW("Second-level.txt","\nINF * 2 = " + a3 );
		Main.fileChaseFW("Second-level.txt","\nNINF + NINF = " + a4 );
		Main.fileChaseFW("Second-level.txt","\nNINF - NINF = " + a5 );
		Main.fileChaseFW("Second-level.txt","\nNINF * 2 = " + a6 );

		if( a1 == Float.POSITIVE_INFINITY && a2!=a2 && a3 == Float.POSITIVE_INFINITY && 
				a4 == Float.NEGATIVE_INFINITY && a5 != a5 && a6 == Float.NEGATIVE_INFINITY)
		{
			Main.fileChaseFW("Second-level.txt", "\n\nCONCLUSION:\n INF || NINF propagation: comply with expectation, correct");
		}
		else {
			Main.fileChaseFW("Second-level.txt", "\n\nCONCLUSION:\n*** INF || NINF propagation: not comply with expectation, incorrect");
		}
	}

	public static void interactINF() {
		if(flag1 == 1 || flag2 == 1)
			return;

		float a1 = INF+NINF;
		float a2 = INF-NINF;
		float a3 = INF*NINF;
		float a4 = INF/NINF;
		float a5 = INF%NINF;
		float a6 = INF*0;
		float a7 = 0*NINF;

		Main.fileChaseFW("Second-level.txt","\n\n4.5\n\nOUTPUT:" );
		Main.fileChaseFW("Second-level.txt","\nINF + NINF = " + a1);
		Main.fileChaseFW("Second-level.txt","\nINF - NINF = " + a2);
		Main.fileChaseFW("Second-level.txt","\nINF * NINF = " + a3);
		Main.fileChaseFW("Second-level.txt","\nINF / NINF = " + a4);
		Main.fileChaseFW("Second-level.txt","\nINF % NINF = " + a5);
		Main.fileChaseFW("Second-level.txt","\nINF * 0 = " + a6);
		Main.fileChaseFW("Second-level.txt","\n0 * NINF = " + a7);

		if(a1!=a1 || a2 == Float.POSITIVE_INFINITY || a3 == Float.NEGATIVE_INFINITY ||
				a4 != a4 || a5!=a5 || a6!=a6 || a7 != a7) {
			Main.fileChaseFW("Second-level.txt", "\n\nCONCLUSION:\n INF || NINF interaction: comply with expectation, correct");
		}else {
			Main.fileChaseFW("Second-level.txt", "\n\nCONCLUSION:\n*** INF || NINF interaction: not comply with expectation, correct");
		}
	}
}
