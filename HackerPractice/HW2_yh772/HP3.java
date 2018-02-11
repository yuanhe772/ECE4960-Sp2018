/** Last Update: 02/10/2018; Author: Yuan He (yh772); Platform: Eclipse, Java8 */

public class HP3 {

	private static double f(double x) {
		return Math.pow(x, 3);
	}

	private static double f1(double x, double h) {
		return ((f(x+h) - f(x)) / h)  ;
	}

	private static double f2(double x, double h) {
		return ((f(x+2*h) - f(x)) / (2*h));
	}

	private static double f3(double x, double h) {
		return (((-1)/(2*h)) * f(x+2*h) - (3/(2*h))*f(x) + (2/h)*f(x+h));
	}

	private static double err(double x) {
		return Math.abs((3-x));
	}

	private static double A(double x, double h) {
		return (f(x+h)-f(x)) / h;
	}

	private static double yita2(double x, double h) {
		return (A(x,4*h)-A(x,2*h))/(A(x,2*h)-A(x,h));
	}

	public static void main(String args[]) {
		for(double h=Math.pow(2,-4); h>=Math.pow(2, -40); h/=2) {

			System.out.println("\nRelative Error:");

			System.out.println("h = "+h+":\nerr3 = " + err(f1(1,h)) +
					"\nerr4 = " + err(f2(1,h))+
					"\nerr5 = " + err(f3(1,h))+
					" \nyita-1 = " + err(f1(1,2*h))/err(f1(1,h)) + 
					"\nyita-2 = " + yita2(1,h)); 

		}
	}



}
