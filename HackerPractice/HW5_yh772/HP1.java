/** Last Update: 02/27/2018; Author: Yuan He (yh772); Platform: MacOS, Eclipse, Java8 */



public class HP1 {

	public static double y(double e) {
		double y = (19700-199*99)/((98.01+e)*100 - 99*99);
		return y;
	}
	
	
	public static double x(double y) {
		double x = (199-99*y)/100;
		return x;
	}
	
	public static void main(String args[]) {
		
		double x;
		double y;
		
		for(double i=Math.pow(10, -1); i>=Math.pow(10, -9);i/=10 ) {
			y = y(i);
			x = x(y);
			System.out.println("x = "+ x +"   y = " + y);
			double[][] m = {{100,99},{99,98.01-i}};
			FullMatrix fm = new FullMatrix(m);
			Vector result = fm.product(new Vector(x,y));
			Vector diff = new Vector(result.v[0]-199, result.v[1]-197);
			double norm = Math.pow(diff.v[0],2) + Math.pow(diff.v[1], 2);
			System.out.println("second-norm = "+ norm);
			
		}	
	}
}
