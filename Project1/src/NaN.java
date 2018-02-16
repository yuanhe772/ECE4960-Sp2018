/** Last Update: 02/15/2018; Author: Yuan He (yh772); Platform: Eclipse, Java8 */


public class NaN {

	static float NaN = FloatINF.INF * 0;
	static int flag3 = 0;

	public static void generateNaN() throws Exception {

		try{
			if(!detectNaN (NaN)) {
				flag3 = 1;
				throw new Exception("3");
			}
			Main.fileChaseFW("first-level.txt", "\n5. NaN operations: correct. ");

		}catch(Exception e){
			if(e.getMessage().toString().equals("3")) {
				System.out.println("\nPlease regenerate NaN");
			}
			Main.fileChaseFW("first-level.txt", "\n***5. NaN operations: incorrect. ");

		}
	}

	private static boolean detectNaN(float f) {
		return f==f ? false:true;
	}

	public static void propagateNaN() {
		if(flag3 == 1)
			return;

		float a1 = (NaN + NaN);
		float a2 = (NaN - NaN);
		float a3 = (NaN*2);
		float a4 = (NaN + NaN);

		Main.fileChaseFW("Second-level.txt","\n\n5.1\n\nOUTPUT:" );
		Main.fileChaseFW("Second-level.txt","\nNaN + NaN = " + a1);
		Main.fileChaseFW("Second-level.txt","\nNaN - NaN = " + a2);
		Main.fileChaseFW("Second-level.txt","\nNaN * 2 = " + a3);
		Main.fileChaseFW("Second-level.txt","\nNaN * NaN = " + a4);

		if(a1 != a1 && a2 != a2 && a3!=a3 && a4!=a4) {
			Main.fileChaseFW("Second-level.txt", "\n\nCONCLLUSION:\n Propagations of NaN are still NaN, correct");
		}
		else {
			Main.fileChaseFW("Second-level.txt", "\n\nCONCLUSION:\n*** Propagations of NaN aren't NaN, incorrect");
		}

	}

	public static void interactNaN() {
		if(flag3 == 1)
			return;

		float a1 = (NaN+FloatINF.NINF);
		float a2 = (NaN-FloatINF.NINF);
		float a3 = (NaN*FloatINF.NINF);
		float a4 = (NaN/FloatINF.NINF);
		float a5 = (NaN%FloatINF.NINF);
		float a6 = (NaN*0);

		Main.fileChaseFW("Second-level.txt","\n\n5.2\n\nOUTPUT:" );
		Main.fileChaseFW("Second-level.txt","\nNaN + NINF = " + a1);
		Main.fileChaseFW("Second-level.txt","\nNaN + INF = " + a2);
		Main.fileChaseFW("Second-level.txt","\nNaN * NINF = " + a3);
		Main.fileChaseFW("Second-level.txt","\nNaN / NINF = " + a4);
		Main.fileChaseFW("Second-level.txt","\nNaN % NINF = " + a5);
		Main.fileChaseFW("Second-level.txt","\nNaN * 0 = " + a6);

		if(a1 != a1 && a2 != a2 && a3!=a3 && a4!=a4 && a5!=a5 && a6!=a6) {
			Main.fileChaseFW("Second-level.txt", "\n\nCONCLLUSION:\n Interactions with NaN are still NaN, correct");
		}
		else {
			Main.fileChaseFW("Second-level.txt", "\n\nCONCLUSION:\n*** Interactions with NaN aren't NaN, incorrect");
		}
	}
}
