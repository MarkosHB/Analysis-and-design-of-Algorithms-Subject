
import java.util.ArrayList;



/**
 * 
 * @author Marcos Hidalgo Banos
 *
 */

public class MochilaAV extends Mochila {

	public SolucionMochila resolver(ProblemaMochila pm) {
		SolucionMochila sm = new SolucionMochila();

		// Obtenemos los objetos del listado
		Item[] items = new Item[pm.items.size()];
		// Junto con su indice correspondiente
		int indices[] = new int[pm.items.size()];
		for (int i = 0; i < pm.items.size(); i++) {
			items[i] = pm.items.get(i);
			indices[i] = i;
		}

		// Ordenamos dichos arrays mediante mergesort
		mergeSort(items, indices, 0, items.length-1);

		// Inicializamos la solucion
		sm.sumaPesos = 0; sm.sumaValores = 0;
		sm.solucion = new ArrayList<>();
		for (int i = 0; i < items.length; i++) {
			sm.solucion.add(0);
		}

		int it = 0;
		// Rellenamos la solucion
		while (it < items.length) {
			if (sm.sumaPesos + items[it].peso <= pm.pesoMaximo) { // Es parte de la solucion
				sm.sumaPesos += items[it].peso;
				sm.solucion.set(indices[it], sm.solucion.get(indices[it])+1);
				sm.sumaValores += items[it].valor;
				if (sm.solucion.get(indices[it]) == items[it].unidades) {
					it++; // Si es el ultimo item, pasamos al siguiente
				}
			} else { // No esta en la solucion
				it++;
			}
		}

		return sm;
	}
	
	private void mergeSort(Item[] items, int[] indices, int inf, int sup) {
		if (inf < sup){
			mergeSort(items,indices,inf,(inf+sup)/2);
			mergeSort(items,indices,(inf+sup)/2+1,sup);
			mezclar(items,indices,inf,(inf+sup)/2,sup);
		}
	}

	private void mezclar(Item[] items, int[] indices, int inf, int medio, int sup) {
		int i = inf; int j = medio+1;
		Item[] b = new Item[sup-inf+1];
		int[] idx = new int[sup-inf+1];
		int k = 0;

		while (i <= medio && j <= sup){
			Double densidad1 = (double) items[i].valor/items[i].peso;
			Double densidad2 = (double)items[j].valor/items[j].peso;
			if (densidad1 >= densidad2) {
					b[k] = items[i];
					idx[k] = indices[i];
					i++;
			} else {
				b[k] = items[j];
				idx[k] = indices[j];
				j++;
			} 
			k++;
		}
		while (i <= medio) {
			b[k] = items[i];
			idx[k] = indices[i];
			i++; k++;
		}
		while (j <= sup) {
			b[k] = items[j];
			idx[k] = indices[j];
			j++; k++;
		}
		k = 0;
		for (int f = inf; f <= sup; f++) {
			items[f] = b[k];
			indices[f] = idx[k];
			k++;
		}
	}	
}
