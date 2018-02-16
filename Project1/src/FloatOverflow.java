/** Last Update: 02/15/2018; Author: Yuan He (yh772); Platform: Eclipse, Java8 */


public class FloatOverflow {

	public static void multiply10() {

		float init = (float) 1.2345678;
		float prev = 0;
		float f = init;
		int n = 0;
		int weight = 10;

		Main.fileChaseFW("second-level.txt","\n\n3.1 Single-precision float multiplying 10^n: ");
		Main.fileChaseFW("second-level.txt","\n\nOUTPUT: ");

		while(f!=(f/weight)) {
			if(n%7 == 3)
				Main.fileChaseFW("second-level.txt", "\nn = " + n + "   " + "float number= " + f);
			prev = f;
			f = f*weight;
			n++;
		}

		Main.fileChaseFW("Second-level.txt","\n\nCONCLUSION:\n*** Single-precision float Overflow: " + init + " multiplying with 10^n, "
				+ "overflows at n = "+ (n-1) + ", float number = " + prev);

		Main.fileChaseFW("first-level.txt","\n***3.1 Single-precision float Overflow: " + init + " multiplying with 10^n, "
				+ "overflows at n = "+ (n-1) + ", float number = " + prev);

	}

	public static void floatIncrement() {

		float f = Float.MAX_VALUE;
		float prev = f;

		while (f>prev) {
			prev = f;
			f = f + 1;
		}

		Main.fileChaseFW("first-level.txt","\n***3.2 Single-precision float Overflow: " + "Float.MAX_VALUE incrementing with 1, "
				+ "overflows when it stops incrementing, float number = " + prev);
		Main.fileChaseFW("second-level.txt","\n\n\n3.2 Single-precision float Overflow: " + "\n\nCONCLUSION:\n*** Float.MAX_VALUE incrementing with 1, "
				+ "overflows when it stops incrementing, float number = " + prev);

	}
}
