/** Last Update: 02/15/2018; Author: Yuan He (yh772); Platform: Eclipse, Java8 */


public class Gradual {

	static float normal = Float.MIN_NORMAL;
	static float x = normal/2;
	static float y = normal;
	static float prev = 0;

	public static void generateXY() {
		while ( x>0 && y>0 && x-y!=0 && y/x!=1) {
			prev = x;
			x = x/10;
			y = y/10;
		}
		x=prev;

		Main.fileChaseFW("Second-level.txt","\n\n7.1\n\nOUTPUT:" );
		Main.fileChaseFW("Second-level.txt","\n x and y are generated from decrementing the smallest "
				+ "normalized single-precision float number as: " + "x = " + x + ", y = " + y );

		if(XsubY()==0 && XdividedY()==0){
			Main.fileChaseFW("first-level.txt", "\n7. Gradual Overflow: correct. ");
		}
		else {
			Main.fileChaseFW("first-level.txt", "\n7. ***Gradual Overflow: incorrect. ");
		}
	}

	public static int XsubY() {
		if(x-y == 0) {
			Main.fileChaseFW("first-level.txt", "\n***7.2 Equal error: wrong value for x and y.");
			return 1;
		}
		Main.fileChaseFW("Second-level.txt","\n\n7.2\n\nOUTPUT:" + "\nX - Y = " + (x-y) );
		return 0;
	}

	public static int XdividedY() {
		if(x/y == 1){
			Main.fileChaseFW("first-level.txt", "\n***7.3 Divide error: wrong value for x and y.");	
			return 1;
		}
		Main.fileChaseFW("Second-level.txt","\n\n7.3\n\nOUTPUT:" + "\nX / Y = " + (x/y));
		return 0;
	}

	public static void sinx_x(){
		x = normal;
		float prev = (float) (Math.sin(1.23456789012345*x)/x);
		float prevfloat = 0;
		float curr = (float) (Math.sin(1.23456789012345*x)/x);

		Main.fileChaseFW("Second-level.txt","\n\n7.4\n\nOUTPUT: ");
		while ( x>0 ) {
			prevfloat = x;
			prev = (float) (Math.sin(1.23456789012345*x)/x);
			Main.fileChaseFW("Second-level.txt","\nx = "+ x +"    "
					+ "sin(1.2345..*x)/x = " + Math.sin(1.23456789012345*x)/x);
			x = x/10;
			curr = (float) (Math.sin(1.23456789012345*x)/x);
		}
		Main.fileChaseFW("Second-level.txt", "\n\nCONCLLUSION:\nWhen normalized smallest single-precision "
				+ "float divided by 10^n, denormalized bottom is " + prevfloat);
	}
}
