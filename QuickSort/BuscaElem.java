////////////////////////////////////////////////////////////////////////////////////////////
// ALUMNO: Marcos Hidalgo Baños
// GRUPO: 2º D Ingenieria Infomatica
////////////////////////////////////////////////////////////////////////////////////////////

import java.util.Scanner;

public final class BuscaElem{
	
	public static <T extends Comparable<? super T>> T kesimo(T v[], int k) {
		T[] c = v.clone();
		return kesimoRec(c,0,c.length-1,k);
	}

	public static <T extends Comparable<? super T>> T kesimoRec(T c[], int izq, int der, int k) {
		// A IMPLEMENTAR POR EL ALUMNO
		if (izq < der) {
			int p = OrdenacionRapida.partir(c, c[izq], izq, der);
			if (k == p) {
				return c[k];
			} else if (k < p) {
				return kesimoRec(c, izq, p-1, k);
			} else {
				return kesimoRec(c, p+1, der, k);
			}
		}
		return c[k];
    }
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int maxvector;
		int i,k;
		
		Scanner sc = new Scanner(System.in);
		System.out.print("Introduce el numero de posiciones del vector: ");
		maxvector=sc.nextInt();
		Integer v[]=new Integer[maxvector];

		System.out.print("Introduce "+maxvector+" enteros separados por espacios: ");
		for (i=0;i<maxvector;i++) v[i]=sc.nextInt();
		System.out.print("Introduce la posicion k deseada (de 1-"+maxvector+"): ");k=sc.nextInt();
		Integer elem=kesimo(v,k-1);
		System.out.print("El elemento "+k+"-esimo es: "+elem);
	}

}
