////////////////////////////////////////////////////////////////////////////////////////////
// ALUMNO: Marcos Hidalgo Baños
// GRUPO: 2º D Ingenieria Infomatica
////////////////////////////////////////////////////////////////////////////////////////////

public class OrdenacionRapida extends Ordenacion {
  
	public static <T extends Comparable<? super T>> void ordenar(T v[]) {
	    ordRapidaRec(v, 0, v.length-1);
	}

	// Debe ordenar ascendentemente los primeros @n elementos del vector @v con 
	// una implementación recursiva del método de ordenación rápida.	
	public static <T extends Comparable<? super T>> void ordRapidaRec(T v[], int izq, int der) {
	    // A completar por el alumno
	    if (izq < der) {
	    	int p = partir(v, v[izq], izq, der);
	    	ordRapidaRec(v, izq, p-1);
	    	ordRapidaRec(v, p+1, der);
	    }
	}
	   
   	public static <T extends Comparable<? super T>> int partir(T v[], T pivote, int izq, int der) {
	    int inf = izq;
		int sup = der;

		while (inf < sup) {
	    	while (v[inf].compareTo(pivote) <= 0 && inf < v.length-1) {
				inf++;
	    	}
	    	while (0 < v[sup].compareTo(pivote) && 0 < sup) {
				sup--;
	    	}
	    	if (inf < sup) {
	    		intercambiar(v, inf, sup); // metodo de Ordenacion.java
	    	}
	    }

		if (0 < pivote.compareTo(v[sup])) {
			intercambiar(v, izq, sup); // metodo de Ordenacion.java
		}

	    return sup;
   	}

	// Pequeños ejemplos para pruebas iniciales.
	public static void main (String args[]) {
	
		// Un vector de enteros
		Integer vEnt[] = {3,8,6,5,2,9,1,1,4};
		ordenar(vEnt);
		System.out.println(vectorAString(vEnt));

		// Un vector de caracteres
		Character vCar[] = {'d','c','v','b'};
		ordenar(vCar);
		System.out.println(vectorAString(vCar));

	}	
    
}
