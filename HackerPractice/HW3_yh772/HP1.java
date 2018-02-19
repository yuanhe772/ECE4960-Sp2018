/** Last Update: 02/18/2018; Author: Yuan He (yh772); Platform: Eclipse, Java8 */

/** Calculation for 5 different Quadrature Schemes, and their errors*/

public class HP1 {

	static double h = 0.1;

	public static double f(double x) {
		return Math.exp(x);
	}

	public static double Rectangle() {
		double I = 0;
		for(double i = -1; i<=1; i += h) {
			I = I + h* f(i);
		}
		return I;
	}

	public static double Trapezoid() {
		double I = 0;
		for(double i = -1; i<=1; i+= h ) {
			I = I + h*(0.5*f(i) + 0.5*f(i+h));
		}
		return I;
	}

	public static double Midpoint() {
		double I = 0;
		for(double i = -1; i<=1; i+=h ) {
			I = I + h*f((i+i+h)*0.5);
		}
		return I;
	}

	public static double Simpson() {
		double I = 0;
		for(double i = -1; i<=1; i += h) {
			I = I + h/6*(f(i) + 4*f(i+0.5*h) + f(i+h));
			//System.out.println(I);
		}
		return I;
	}


	public static double TwoGaussian(){
		double I = 0;
		for(double i = -1; i<=1; i+=h) {
			I = I + h*0.5*(f(i+0.5*h - h/(2*Math.sqrt(3))) + f(i+0.5*h + h/(2*Math.sqrt(3))));
		}
		return I;
	}


	//	public static double ThreeGaussian(){
	//		double I = 0;
	//		for(double i = -1; i<=1; i+=h) {
	//			I = I + h * ((5/18)*f(i+0.5*h - h*Math.sqrt(3)/(Math.sqrt(5)*2)) + 
	//					8/18*f(i + 0.5*h) + 5/18*f(i+0.5*h + h*Math.sqrt(3)/(Math.sqrt(5)*2)));
	//		}
	//		return I;
	//	}

	public static void main(String args[]) {

		String[] name = {"Rantangle", "Trapezoid", "Midpoint", "Simpson", "Two-Point Gaussian"};
		double[] value = {Rectangle(), Trapezoid(), Midpoint(), Simpson(),TwoGaussian()};

		for (int i = 0; i<5; i++) {
			System.out.println("The integration for exp(x) using " + name[i] + " quadrature scheme is "
					+ value[i] + ", the error would then be " + Math.abs(value[i] - 2.3504));
		}
	}
}
