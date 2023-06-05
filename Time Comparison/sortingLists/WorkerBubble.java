package sortingLists;

import java.util.List;
import java.util.concurrent.CancellationException;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import javax.swing.SwingWorker;

/**
 * Autor: Marcos Hidalgo Baños
 * 2ºD Ingenieria Informatica
 */

public class WorkerBubble extends SwingWorker <Void, Integer> {

    private int[] a;
    private Panel v;
    private long total;

    public WorkerBubble(List<Integer> lista, Panel vista) {
        List<Integer> aux = new ArrayList<>(lista);
        a = aux.stream().mapToInt(Integer::intValue).toArray();
        v = vista;
    }

    @Override
    protected Void doInBackground() throws Exception {

        long startTime = System.currentTimeMillis(); //inicio de la ordenación

        int N = a.length;
        int progreso = 0;
        this.setProgress(progreso);
        int i = 0;

        while (i<N && !isCancelled()){
            //recorremos el array a[i..N-1] desde la posición N-1 a la i. 
            //Comparamos los elementos dos a dos y los intercambiamos si 
            //están desordenados 
            for (int j=N-1; j>i; j--){ 
                if (a[j]<a[j-1]){ 
                    int aux = a[j]; 
                    a[j] = a[j-1]; 
                    a[j-1] = aux; 
                } 
            } 
            //Inv: a[0..i] tiene los i+1 menores elementos de a[0..N-1] 
            //Inv: a[0..i] está ordenado 
            publish(a[i]);
            progreso += (i/N)*100;
            //firePropertyChange("progress", this.getProgress(), progreso);
            setProgress(progreso);

            i++;
        }

        total = (System.currentTimeMillis()-startTime);//fin de la ordenación
        
        return null;
    }

    @Override
    public void process(List<Integer> lista) {
		v.writeTextAreaBubble(lista); //se van publicando los que se hayan procesado
	}

    @Override
	public void done() {
        try {
            this.setProgress(100);  
            v.messageAreaBubble("List sorted in " + total + " ms.");
        } catch (CancellationException ce) {
            //
        }
    }

     // barra de progreso
     public void setControlador(PropertyChangeListener ctr) {
		this.addPropertyChangeListener(ctr);
	}
}
