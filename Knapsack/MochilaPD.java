import java.util.ArrayList;

/**
 * 
 * @author Marcos Hidalgo Baños
 *
 */

public class MochilaPD extends Mochila {
	
	public SolucionMochila resolver(ProblemaMochila pm) {
		SolucionMochila sm = new SolucionMochila();

		ArrayList<Item> items = new ArrayList<>();
		items.add(new Item(0, 0, 0, 0));
		items.addAll(pm.getItems());
		Item it;

		// Inicializamos la tabla de programacion dinamica
		int[][] tabla = new int[items.size()][pm.getPesoMaximo()+1];
		int valor, test;

		// Rellenamos la tabla de programacion dinamica
		for (int i = 0; i < tabla.length; i++) {
			it = items.get(i);
			valor = 0;
			for (int j = 0; j < tabla[i].length; j++) {
				// Aplicamos la ecuacion de recurrencia
				/** Rama1. Filas y columnas a cero */
				if (i==0 || j==0) {
					tabla[i][j] = 0;
				/** Rama2. Actual igual al de fila anterior */
				} else if (j < it.peso) {
					tabla[i][j] = tabla[i-1][j];
				/** Rama3. Maximo k unidades del item */
				} else {
					int k = 0;
					// Vemos el mayor valor de k podemos obtener
					while (k <= it.unidades && j-(k*it.peso) >= 0) {
						test = tabla[i-1][j-(k*it.peso)] + k*it.valor;
						if (valor < test) { // Nos quedamos con el mayor
							valor = test;
						}
						k++;
					}
					tabla[i][j] = valor;
				}
			}
		}

		// Inicializamos la solucion
		sm.solucion = new ArrayList<>();
		for (int i = 0; i < items.size()-1; i++) {
			sm.solucion.add(0);
		}

		// Rellenamos la solucion
		int j = pm.pesoMaximo;
		int i = items.size()-1;
		while (i > 0 && j > 0) { // Desde el ultimo item al primero
			if (tabla[i-1][j] < tabla[i][j]) { // Es parte de la solucion
				sm.solucion.set(i-1, sm.solucion.get(i-1) + 1);
				j = j-items.get(i).peso;
				if (sm.solucion.get(i-1) == items.get(i).unidades) {
					i--; // Si es el ultimo item, pasamos al siguiente
				}
			} else { // No esta en la solucion
				i--;
			}

		}

		// Finalmente, actualizamos el peso y valor total de la solucion
		sm.sumaPesos = 0;
		for (int t = 0; t < sm.solucion.size(); t++) {
			sm.sumaPesos += sm.solucion.get(t) * items.get(t+1).peso;
		}
		sm.sumaValores = tabla[items.size()-1][pm.getPesoMaximo()];
		return sm;
	}
}
