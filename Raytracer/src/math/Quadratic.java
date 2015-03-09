package math;

import java.util.Arrays;

public class Quadratic {
	
	private static float[] NO_SOL = new float[0];
	private static float[] ONE_SOL = new float[1];
	private static float[] TWO_SOL = new float[2];
	
	
	//solves ax^2+bx+c = 0
	private static float a;
	private static float b;
	private static float c;
	public static void reset() {
		a = 0;
		b = 0;
		c = 0;
	}
	
	//adds ax^2+bx+c to our equation
	public static void addTerm(float a1, float b1, float c1) {
		a += a1;
		b += b1;
		c += c1;
	}
	
	//adds (tx+c)^2 to our equation
	public static void addTermSquared(float t, float d) {
		a += t * t;
		b += 2*t*d;
		c += d * d;
	}
	
	//if no valid solution, return 
	public static float[] solve() {
		//(-b+-sqrt(b^2-4ac)) / 2a
		float d = b*b - 4 * a * c;
		if (d < 0) {
			return NO_SOL;
		}
		else if (d == 0) {
			ONE_SOL[0] = -b / (2 * a);
			return ONE_SOL;
		}
		else {
			TWO_SOL[0] = (float)(-b - Math.sqrt(d)) / (2 * a);
			TWO_SOL[1] = (float)(-b + Math.sqrt(d)) / (2 * a);
			return TWO_SOL;
		}
	}
	
	public static void main(String[] args) {
		//solve 5x^2+3x+2 = 0, has no solutions
		Quadratic.reset();
		Quadratic.addTerm(5, 3, 2);
		System.out.println("Solution: "+Arrays.toString(Quadratic.solve()));
		
		
		//solve 5x^2-3x-7 = 0, has two solutions (-0.92066, 1.5207)
		Quadratic.reset();
		Quadratic.addTerm(5, -3, -7);
		System.out.println("Solution: "+Arrays.toString(Quadratic.solve()));
		
		//solve 3x^2-12x+12 = 0, has one solution (2)
		Quadratic.reset();
		Quadratic.addTerm(3, -12, 12);
		System.out.println("Solution: "+Arrays.toString(Quadratic.solve()));
		
		Quadratic.reset();
		Quadratic.addTermSquared(3, 2);
		System.out.println("Solution: "+Arrays.toString(Quadratic.solve()));
		
	}
}
