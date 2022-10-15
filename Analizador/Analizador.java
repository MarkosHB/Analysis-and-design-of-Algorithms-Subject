import java.util.ArrayList;
import java.util.Arrays;

public class Analizador {
	
	public static void main(String arg[]) {
	
		/* 
		 * VARIABLES DE CONTROL DEL TIEMPO
		 */ 
		
		// Maximo de segundos en ejecucion del main
		int tiempoTotal = 8;
		// Total de milisegundos transcurridos
		long tiempoEjecucion = 0;
		// Numero estimado de iteraciones (puede ser mayor si da tiempo)
		int maxSimulaciones = 50;
		// Numero de veces que vamos a probar el algoritmo con entrada n o n2
		int iteraciones = 5;
		
		
		/* 
		 * VARIABLES DE CONTROL DEL TAMAÑO DE ENTRADA
		 */ 
		 
		// Tamaños de entrada al algoritmo (n2 es el doble de n)
		long n = 2, n2 = 2;
		// Tiempo transcurrido para cierto tamaño de entrada
		long tn, tn2;		
		// Arrays de tiempos de ejecucion para entrada n y n2
		ArrayList<Long>tiemposN = new ArrayList<Long>();
		ArrayList<Long>tiemposN2 = new ArrayList<Long>();

		// Cronometro general, para tamaño de entrada n y n2
		Temporizador crono = new Temporizador(1); //Cronometro en milisegs
		Temporizador temporizador= new Temporizador(2); //Cronometro en nanosegs
		Temporizador temporizador2 = new Temporizador(2); //Cronometro en nanosegs
		
		
		/* 
		 * PRINCIPAL BLOQUE DE CODIGO
		 */ 
		
		while (tiempoEjecucion/1000 < tiempoTotal) {
			
			// Aumentamos el tamaño de entrada segun la iteracion
			if (tiemposN.size() < maxSimulaciones) {n += 1;}
			else {n *= 10; maxSimulaciones *= 2;}
			// n2 siempre es el doble de n
			n2 = n*2;

			/*
			 * CALCULO DEL TIEMPO EMPLEADO PARA ENTRADA N
			 */
			 
			tn = 0;
			for (int i = 0; i < iteraciones; i++) {	
				crono.iniciar();
							
				temporizador.iniciar();
				Algoritmo.f(n);
				temporizador.parar();
				tn += temporizador.tiempoPasado();
				temporizador.reiniciar();
				
				crono.parar();
				tiempoEjecucion = crono.tiempoPasado();
				if (tiempoEjecucion/1000 > tiempoTotal) break;
			}
			tn /= iteraciones; // Nos quedamos con la media
			tiemposN.add(tn);
			
			/*
			 * CALCULO DEL TIEMPO EMPLEADO PARA ENTRADA N2
			 */
			
			tn2 = 0;
			for (int i = 0; i < iteraciones; i++) {
				crono.iniciar();
				
				temporizador2.iniciar();
				Algoritmo.f(n2);
				temporizador2.parar();
				tn2 += temporizador2.tiempoPasado();
				temporizador2.reiniciar();
				
				crono.parar();
				tiempoEjecucion = crono.tiempoPasado();
				if (tiempoEjecucion/1000 > tiempoTotal) break;
			}
			tn2 /= iteraciones; // Nos quedamos con la media
			tiemposN2.add(tn2);	
			
			tiempoEjecucion = crono.tiempoPasado();
	
		}
				
		double ratio = ((double) tiemposN2.get(tiemposN2.size()-1))
						/ ((double) tiemposN.get(tiemposN.size()-1));
		System.out.println(masCercano(ratio));
	}
		
	/*
	 * FUNCION MASCERCANO
	 * Determina, en base a un ratio previamente ya calculado, cual es la clase de complejidad
	 * que mas se parece a dichos resultados
	 */
	public static String masCercano(double ratio) {
		Temporizador temporizador = new Temporizador(2);
		int itConstanteLog = 10;
		int itLinearNlogn = 10;
		int itExpFact = 5;
		
		if (ratio < 1.5) { // Constante o logaritmico
			long n = 200000;
			long tn = 0, t1 = 0, tlogn = 0;
			
			for (int i = 0; i < itConstanteLog; i++) {
				// Probamos con el algoritmo
				temporizador.reiniciar();
				temporizador.iniciar();
				Algoritmo.f(n);
				temporizador.parar();
				tn += temporizador.tiempoPasado();
				
				// Probamos con uno constante
				temporizador.reiniciar();
				temporizador.iniciar();
				_1(n);
				temporizador.parar();
				t1 += temporizador.tiempoPasado();
				
				// Probamos con uno logaritmico
				temporizador.reiniciar();
				temporizador.iniciar();
				LOGN(n);
				temporizador.parar();
				tlogn += temporizador.tiempoPasado();
				
				// Incrementamos el tamaño de entrada
				n *= 10;
				
			}
			tn /= itConstanteLog; // Nos quedamos con la media
			t1 /= itConstanteLog; // Nos quedamos con la media
			tlogn /= itConstanteLog; // Nos quedamos con la media
			
			if (((double)tn)/tlogn < 0.1) return "LOGN";
			else return "1";
			
		} else if (ratio < 3.0) { // Linear o NlogN
			long n = 100000;
			long tn = 0, tnlogn = 0;
			
			for (int i = 0; i < itLinearNlogn; i++) {
				// Probamos con el algoritmo
				temporizador.reiniciar();
				temporizador.iniciar();
				Algoritmo.f(n);
				temporizador.parar();
				tn += temporizador.tiempoPasado();
				
				// Probamos con uno NLOGN
				temporizador.reiniciar();
				temporizador.iniciar();
				NLOGN(n);
				temporizador.parar();
				tnlogn += temporizador.tiempoPasado();
				
				// Incrementamos el tamaño de entrada
				n *= 110;
				n /= 100;
			}
			tn /= itLinearNlogn; // Nos quedamos con la media
			tnlogn /= itLinearNlogn; // Nos quedamos con la media
			
			if (((double)tn)/tnlogn < 0.1) return "N";
			else return "NLOGN";

		} else if (ratio < 6.0) { // Cuadratico
			return "N2";
			
		} else if (ratio < 10.0) { // Cubico
			return "N3";
			
		}  else {	// Exponencial o factorial
			long tn = 0, tf = 0;
			
			for (int i = 0; i < itExpFact; i++) {
				// Probamos con el algoritmo
				temporizador.reiniciar();
				temporizador.iniciar();
				Algoritmo.f(10);
				temporizador.parar();
				tn += temporizador.tiempoPasado();
				
				// Probamos con uno factorial
				temporizador.reiniciar();
				temporizador.iniciar();
				FN(10);
				temporizador.parar();
				tf += temporizador.tiempoPasado();
			}
			tn /= itExpFact;
			tf /= itExpFact;
			
			if (((double)tn) / ((double) tf) < 0.1) return "2N";
			else return "NF";
		}
	}	
	
	/* 
	 * FUNCIONES AUXILIARES
	 * El siguiente conjunto de funciones representan a los diferentes
	 * ordenes de complejidad mediante una operacion conocida,
	 * que sabemos pertenece exactamente a dicha complejidad.
	 * 
	 */
		
	public static void _1(long n) {
		int j=0;
	}
	 
	public static long log2 (long n) {
		double num = Math.log(n);
		double den = Math.log(2);
		double div = num/den;
		return ((long)div);
		
	}
	
	public static void LOGN (long n) {
		int j=0;
		for (int i = 0; i < log2(n); i++) {
			j = i;
		}
	}
	
	public static void NLOGN (long n) {
		int j = 0;
		
		for (int i = 0; i < n * log2(n); i++) {
			j=1;
		}
	}
	
	public static void N (long n) {
		int j = 0;
		for (int i = 0; i < n; i++) {
			j=i;
		}
	}
	
	public static void N2 (long n) {
		int j = 0;
		for (int i = 0; i < n*n; i++) {
			j=i;
		}
	}
	
	public static void N3 (long n) {
		int j = 0;
		for (int i = 0; i < n*n*n; i++) {
			j=i;
		}
	}
		
	public static int fact (long n) {
		int f = 1;
		for (int i = 1; i <= n; i++) {
			f *= i;
		}
		return f;
	}

	public static void FN(long n) {
		int j=0;
		for (int i = 0; i<fact(n);i++) {
			j=i;
		}
	}
	
	public static void _2N (long n) {
		int j = 0;
		for (int i = 0; i < Math.pow(2, n); i++) {
			j=i;
		}
	}
}
