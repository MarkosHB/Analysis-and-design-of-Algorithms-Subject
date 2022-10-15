public class Algoritmo {
	public static long log2 (long n) {
		double num = Math.log(n);
		double den = Math.log(2);
		double div = num/den;
		return ((long)div);
		
	}
	public static void f(long n) {
		int j=0;
		for (int i = 0; i < log2(n); i++) {
			j = i;
		}
		
	}
}

