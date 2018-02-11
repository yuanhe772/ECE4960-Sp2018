/** Last Update: 02/10/2018; Author: Yuan He (yh772); Platform: Eclipse, Java8 */

public class HP3 {
	public static void main(String args[]) {
		// Observe overflow handling in integers //
		long i = 1, intFactorial = 1; // If change primitive type into long, would work
		for (i = 2; i < 20; i++) { 
			intFactorial *= i;
			System.out.println(i +" "+ intFactorial);
		}
		for (i= 20; i > 1; i--) { 
			intFactorial /= i;
			System.out.println(i +" "+ intFactorial); // not symmetrical!
		}
	}
}
