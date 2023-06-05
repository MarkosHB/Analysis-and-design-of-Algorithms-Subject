package sortingLists;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * Autor: Marcos Hidalgo Baños
 * 2ºD Ingenieria Informatica
 */

public class Principal {
    public static void crearGUI() {
		JFrame ventana = new JFrame("Sorting lists");
		Panel panel = new Panel();
		Controller ctr = new Controller(panel);
		panel.setController(ctr);
		ventana.setContentPane(panel);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.pack();
		ventana.setVisible(true);
	}

	public static void main(String[] args) {
		// Hebra dispacher...
		SwingUtilities.invokeLater(new Runnable() {
            @Override
			public void run() {
				crearGUI();
			}
		});

	}
}
