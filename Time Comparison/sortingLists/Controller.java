package sortingLists;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.SwingWorker.StateValue;

/**
 * Autor: Marcos Hidalgo Baños
 * 2ºD Ingenieria Informatica
 */

public class Controller implements ActionListener, PropertyChangeListener {
    
    private Panel vista;
	
    private WorkerSelection wSelec;
	private WorkerBubble wBubble;
	
    private List<Integer> lista;
    private int tam = -1;

	public Controller(Panel panel) {
        this.vista = panel;
    }

    public void generarLista(List<Integer> lista) {
        Random r = new Random();
        for (int i = 0; i < tam; i++) {
            lista.add(r.nextInt(99999));
        }
    }

    public void clearAll() {
        vista.clearArea();
        vista.clearAreaBubble();
        vista.clearAreaSelection();
        vista.messageArea("");
        vista.messageAreaSelection("");
        vista.messageAreaBubble("");
        vista.establecerProgresoSelection(0);
        vista.establecerProgresoBubble(0);
    }

    @Override
	public void actionPerformed(ActionEvent e) {
		
        if(e.getActionCommand().equals(vista.crear())) {

            clearAll();

            try { // Intentamos ver si hay algo escrito ...
                tam = vista.getTam();
            } // ... pero si no hay, pedimos que introduzca algo 
            catch (NumberFormatException er) { 
                vista.comment("enter a number between 1 and 60000");
            }
            
            if (tam < 1 || tam > 60000) { // Caso en fuera del rango
                vista.comment("enter a number between 1 and 60000");
                vista.clearSize(); // Quitamos el antiguo valor
                tam = -1; // Decimos que no hay valor disponible
            }
            else { // El resto de casos validos
                vista.comment("number correct");
                vista.messageArea("List created");

                tam = vista.getTam();
                lista = new ArrayList<>(tam);
                generarLista(lista);
                vista.writeTextArea(lista);

                vista.enableSortButton();
                vista.disableCreateButton();
            }            
            
        }
        else if (e.getActionCommand().equals(vista.ordenar())) {

            vista.enableCancelButton();

            wSelec = new WorkerSelection(lista, vista);
            vista.messageAreaSelection("Sorting the list");
            wSelec.setControlador(this);
            wSelec.addPropertyChangeListener(this);
            wSelec.execute();

            wBubble = new WorkerBubble(lista, vista);
            vista.messageAreaBubble("Sorting the list");
            wBubble.setControlador(this);
            wBubble.addPropertyChangeListener(this);
            wBubble.execute();

            // si ya lo hemos ordenado, no dar la opcion de volver a hacerlo
            vista.disableSortButton();

            // Comprobar que hno hayamos cancelado
            if (wBubble.isCancelled() && wSelec.isCancelled()) {
                vista.disableCancelButton();
                vista.enableCreateButton();
            } 

            tam = -1; // Decimos que no hay valor disponible
        }
        else if (e.getActionCommand().equals(vista.cancelar())) {
            wSelec.cancel(true);
            wBubble.cancel(true);
            vista.enableCreateButton();
            vista.disableSortButton();
            vista.disableCancelButton();

            vista.messageAreaSelection("Sort cancelled");
            vista.messageAreaBubble("Sort cancelled");
        }
	}

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        
        if (evt.getPropertyName().equals("progress")) {
            if (evt.getSource() == wSelec) {
                vista.establecerProgresoSelection((Integer) evt.getNewValue());
            }
            else if (evt.getSource() == wBubble) {
                vista.establecerProgresoBubble((Integer) evt.getNewValue());
            }			
		} 
        else if (evt.getPropertyName().equals("state")) {
            // Ambos han acabado
           if (wBubble.getState() == StateValue.DONE 
                && wSelec.getState() == StateValue.DONE) {
                vista.disableCancelButton();
                vista.enableCreateButton();
           }
        }
    }
}
