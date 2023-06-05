package sortingLists;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;

import javax.swing.SwingWorker;

/**
 * Autor: Marcos Hidalgo Baños
 * 2ºD Ingenieria Informatica
 */

public class WorkerSelection extends SwingWorker<Void, Integer> {

    private int[] a;
    private Panel v;
    private long total;

    public WorkerSelection(List<Integer> lista, Panel vista) {
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

        while (i<N && !isCancelled()) { 
            //buscamos el menor elemento del array a[i..N-1] 
            //y lo intercambiamos con a[i] 
            int menor = i; 
            for (int j=i+1; j<N; j++)  
                if (a[j]<a[menor]) menor = j; 
            int aux = a[i]; 
            a[i] = a[menor]; 
            a[menor] = aux; 

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
		v.writeTextAreaSelection(lista); //se van publicando los que se hayan procesado
	}

    @Override
	public void done() {    
        try {
            this.setProgress(100);      
            v.messageAreaSelection("List sorted in " + total + " ms.");
        } catch (CancellationException ce) {
            //
        }
        
    }

    // barra de progreso
    public void setControlador(PropertyChangeListener ctr) {
		this.addPropertyChangeListener(ctr);
	}
}

