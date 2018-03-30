import java.util.Arrays;

/**
 * HP5.java, ECE4960-HW7
 * Created by Yuan He(yh772) on 2018/03/21
 * Platform: Java 8, Eclipse, MacOS
 * Copyright © 2018 Yuan He. All rights reserved.
 * 
 * Note6, p10:
 * Use the Quasi Newton method to perform the parameter extraction 
 * for the power-law function y = axm with the known measurements:
 */
public class Note6_HP1 {

	static double x[] = {1.0, 4.5, 9.0, 20, 74, 181};
	static double y[] = {3.0, 49.4, 245, 1808, 22000, 73000};	


	// f1(x)
	public static double fx1(Vector am) {
		double a = am.v[0];
		double m = am.v[1];
		double sumX2M = 0;
		double sumXMY = 0;
		for(int i=0; i<x.length; i++) {
			sumX2M += Math.pow(x[i], 2*m);
			sumXMY += Math.pow(x[i], m)*y[i];
		}
		return (sumX2M*a - sumXMY);
	}

	// f2(x)
	public static double fx2(Vector am) {
		double a = am.v[0];
		double m = am.v[1];
		double sumLnXX2M = 0;
		double sumLnXXMY = 0;
		for(int i=0; i<x.length; i++) {
			sumLnXX2M += Math.log(x[i])*Math.pow(x[i], 2*m);
			sumLnXXMY += Math.log(x[i])*Math.pow(x[i], m)*y[i];
		}
		return (sumLnXX2M*Math.pow(a,2) - sumLnXXMY*a);
	}

	// f(x) = f1(x) + f2(x)
	public static Vector fx(Vector am) {
		Vector fx = new Vector(am.len);
		fx.v[0] = fx1(am);
		fx.v[1] = fx2(am);
		return fx;
	}

	// gradient: d(f1)/d(m)
	public static double df1dm(Vector am) {
		double a = am.v[0];
		double m = am.v[1];
		double sumX2MLnX = 0;
		double sumXMYLnX = 0;
		for(int i=0; i<x.length; i++) {
			sumX2MLnX += Math.pow(x[i], 2*m)*Math.log(x[i]);
			sumXMYLnX += Math.pow(x[i], m)*y[i]*Math.log(x[i]);
		}
		return (sumX2MLnX*2*a - sumXMYLnX);
	}

	// gradient: d(f2)/d(m)
	public static double df2dm(Vector am) {
		double a = am.v[0];
		double m = am.v[1];
		double sum1 = 0;
		double sum2 = 0;
		for(int i=0; i<x.length; i++) {
			sum1 += Math.pow(Math.log(x[i]),2)*Math.pow(x[i], 2*m);
			sum2 += Math.pow(Math.log(x[i]),2)*Math.pow(x[i], m)*y[i];
		}
		return 2*Math.pow(a, 2)*sum1-a*sum2;
	}

	// gradient: d(f1)/d(a)
	public static double df1da(Vector am) {
		double m = am.v[1];
		double sum = 0;
		for(int i=0; i<x.length; i++) {
			sum += Math.pow(x[i], 2*m);
		}
		return sum;
	}

	// gradient: d(f2)/d(a)
	public static double df2da(Vector am) {
		double a = am.v[0];
		double m = am.v[1];
		double sum1 = 0;
		double sum2 = 0;
		for(int i=0; i<x.length; i++) {
			sum1 += Math.pow(x[i], 2*m)*Math.log(x[i]);
			sum2 += Math.pow(x[i], m)*y[i]*Math.log(x[i]);
		}
		return (sum1*2*a - sum2);
	}

	// Take reverse of Jacobian matrix
	public static FullMatrix JReverse(Vector am) {
		double a = df1da(am);
		double b = df1dm(am);
		double c = df2da(am);
		double d = df2dm(am);
		double scaler = 1/(a*d - b*c);

		double jReverse[][] = {{d*scaler,-b*scaler},{-c*scaler,a*scaler}};

		return new FullMatrix(jReverse);
	}

	// delta x
	public static Vector dx(Vector am) {
		FullMatrix JReverse = JReverse(am);
		Vector fx = fx(am);
		return (JReverse.product(fx)).scale(-1);
	}

	// The loss function for y, with form of || ax^m - y ||
	public static double error(Vector am) {
		double a = am.v[0];
		double m = am.v[1];
		double sum = 0;
		for(int i=0; i<x.length; i++) {
			sum += Math.pow(a*Math.pow(x[i], m) - y[i],2);
		}
		return sum;
	}

	// Quasi-Newton solver
	public static void Newton(Vector am) {
		//		int cou = 0;
		double t = 0;
		double norm1 = 0;
		double norm2 = 0;
		while(dx(am).secondNorm()>Math.pow(10, -7) && error(am)>Math.pow(10, -7)) {
			t = 1;
			norm1 = Math.abs(error(am.add(dx(am),t)));
			norm2 = Math.abs(error(am.add(dx(am),t/2)));
			while(norm1>norm2) {
				norm1 = norm2;
				t /= 2;
				norm2 = Math.abs(error(am.add(dx(am),t/2)));
			}
			Vector d=dx(am);
			am = am.add(d,t);
			System.out.println("[a, m] = "+Arrays.toString(am.v)+
					"     || dx(am) ||= "+dx(am).secondNorm()+"     t = "+t+"     || V(a, m) || ="
					+error(am));
			//			cou+=1;
			//			if(cou>620)break;
		}
	}


	public static void main(String args[]) {	
		double amam[] = {80,2};
		Newton(new Vector(amam));

	}

}
