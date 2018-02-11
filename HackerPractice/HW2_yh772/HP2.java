/** Last Update: 02/11/2018; Author: Yuan He (yh772); Platform: Eclipse, Java8 */

public class HP2 {

	private static double f(double t) {
		return Math.exp(-t);
	}

	public static void main(String args[]) {

		for(double dt = 0.5; dt<=2; dt += 0.5) {
			System.out.println("\n\ndelta t = " + dt);
			for(double t = 0; t<=20; t += dt) {
				System.out.println("Forward Euler:" + (1-dt) * f(t-dt) + 
						";   Backward Euler:" + (1/(1+dt)) * f(t+dt)+
						";   Original: " + f(t));
			}
		}
	}
}
