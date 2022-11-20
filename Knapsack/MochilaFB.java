import java.util.ArrayList;

/**
 * 
 * @author Marcos Hidalgo Baños
 * 		Asumimos que: a) Todos los items tienen
 *         un valor >=1 b) W>0
 */

public class MochilaFB extends Mochila {

	
	public SolucionMochila resolver(ProblemaMochila pm) {
		SolucionMochila sm = new SolucionMochila();
		sm = calcSol(sm, pm.getItems(), new ArrayList<Integer>(), 0, pm.pesoMaximo);
		return sm;
	}

	private SolucionMochila calcSol(SolucionMochila sm, ArrayList<Item> items,
									ArrayList<Integer> res, int iteracion, int maxW) {

		int mejorSuma = 0, mejorPeso = 0;

		if (iteracion == items.size()) {	// Ultima iteracion de la lista de items
			for (int i = 0; i < res.size(); i++) {	// Vemos la solucion que tenemos
				mejorSuma += items.get(i).valor*res.get(i); // Obtenemos su suma de valores
				mejorPeso += items.get(i).peso*res.get(i); // Obtenemos su peso de los items
			}
			if (sm.sumaValores < mejorSuma && maxW >= mejorPeso) {	// Encontramos una mejor solucion
				sm.sumaValores = mejorSuma;	// Actualizamos su suma de valores
				sm.sumaPesos   = mejorPeso; // Actualizamos su peso de los items
				sm.solucion    = new ArrayList<>(res); // Devolvemos la mejor solucion
			}
			return sm;

		} else {
			if (iteracion == 0) {	//Primera iteracion
				res = new ArrayList<Integer>(); // Creamos la solucion que iremos llenando
				for (int i = 0; i < items.size(); i++) { // Inicializamos con ceros dicha solucion
					res.add(0);
				}
			}
			for (int i = 0; i <= items.get(iteracion).unidades; i++) { // Para cada item con unidades
				res.set(iteracion, i);	// Avanzamos la iteracion correspondiente
				sm = calcSol(sm, items, res, iteracion+1, maxW); // Continuamos visitando soluciones
			}
			return sm;
		}
	}
}
