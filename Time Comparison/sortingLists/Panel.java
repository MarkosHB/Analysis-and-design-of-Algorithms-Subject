package sortingLists;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Autor: Marcos Hidalgo Baños
 * 2ºD Ingenieria Informatica
 */

public class Panel extends JPanel{

	private static final long serialVersionUID = 1L;
	

	private JLabel label = new JLabel("List size (1--60000 elems):");

	private JTextField size = new JTextField(8);
	private JButton createButton = new JButton("Create");
	private JButton sortButton = new JButton("Sort");
	private JButton cancelButton = new JButton("Cancel");
	
	private JTextArea area = new JTextArea(20,35);
	private JTextArea areaSelection = new JTextArea(20,35);
	private JTextArea areaBubble = new JTextArea(20,35);
	private JProgressBar progress = new JProgressBar(0,100);
	private JProgressBar progressSelection = new JProgressBar(0,100);
	private JProgressBar progressBubble = new JProgressBar(0,100);

	private JLabel messageArea = new JLabel("");
	private JLabel messageAreaSelection = new JLabel("");
	private JLabel messageAreaBubble = new JLabel("");
	private JLabel comment = new JLabel("GUI created");

	private String crear = "crear";
	private String ordenar = "ordenar";
	private String cancelar = "cancelar";
	private String progresoSelecion = "progressSelection";
	private String progresoBurbuja = "progressBubble";
	
	public Panel(){
		this.setLayout(new BorderLayout());
		comment.setForeground(Color.RED);
		setCreateButton();
		setSortButton();
		setCancelButton();
		disableSortButton();
		disableCancelButton();
		JPanel topRow=new JPanel();
		topRow.add(label);
		topRow.add(size);
		topRow.add(createButton);
		topRow.add(sortButton);
		topRow.add(cancelButton);
		
		
		this.add(topRow,BorderLayout.NORTH);
		
		JPanel center = new JPanel();
		JPanel c1=new JPanel();
		c1.setLayout(new BorderLayout());
		JScrollPane scrol1 = new JScrollPane(area);
		scrol1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrol1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		c1.add(scrol1,BorderLayout.CENTER);
		JPanel c11 = new JPanel();
		c11.setLayout(new GridLayout(3,1));
		c11.add(new JLabel("Array to be sorted"));
		c11.add(progress);
		c11.add(messageArea);
		c1.add(c11,BorderLayout.SOUTH);
		
		JPanel c2=new JPanel();
		c2.setLayout(new BorderLayout());
		JScrollPane scrol2 = new JScrollPane(this.areaSelection);
		scrol2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrol2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		c2.add(scrol2,BorderLayout.NORTH);
		JPanel c21 = new JPanel();
		c21.setLayout(new GridLayout(3,1) );
		c21.add((new JLabel("Selection sort")));
		c21.add(progressSelection);
		progressSelection.setStringPainted(true);
		c21.add(messageAreaSelection);
		c2.add(c21,BorderLayout.SOUTH);
		
		
		JPanel c3=new JPanel();
		c3.setLayout(new BorderLayout());
		JScrollPane scrol3 = new JScrollPane(this.areaBubble);
		scrol3.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrol3.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		c3.add(scrol3,BorderLayout.NORTH);
		JPanel c31 = new JPanel();
		c31.setLayout(new GridLayout(3,1) );
		c31.add((new JLabel("Bubble sort")));
		c31.add(progressBubble);
		progressBubble.setStringPainted(true);
		c31.add(messageAreaBubble);
		c3.add(c31,BorderLayout.SOUTH);

		
		center.add(c1);
		center.add(c2);
		center.add(c3);
		this.add(center,BorderLayout.CENTER);
		this.add(comment,BorderLayout.SOUTH);
	
	
	}

	/* ---------------------------------------------- */
	/* Metodos para activar o desactivar los JButtons */
	/* ---------------------------------------------- */
	public void disableCreateButton() {
		createButton.setEnabled(false);
	}
	public void disableSortButton() {
		sortButton.setEnabled(false);
	}
	public void disableCancelButton() {
		cancelButton.setEnabled(false);
	}
	public void enableCreateButton() {
		createButton.setEnabled(true);
	}
	public void enableSortButton() {
		sortButton.setEnabled(true);
	}
	public void enableCancelButton() {
		cancelButton.setEnabled(true);
	}

	/* -------------------------------------------------*/
	/* Metodos desde los que se establecen los comandos */
	/* -------------------------------------------------*/
	public void setCreateButton() {
		//set action command
		createButton.setActionCommand(crear);
	}

	public void setSortButton() {
		//set action command
		sortButton.setActionCommand(ordenar);
	}

	public void setCancelButton() {
		//set action command
		cancelButton.setActionCommand(cancelar);

	}
	
	/* --------------------------------------------------- */
	/* Metodo para enlazar los comandos con el controlador */
	/* --------------------------------------------------- */
	public void setController(ActionListener ctr){
		createButton.addActionListener(ctr);
		setCreateButton();

		sortButton.addActionListener(ctr);
		setSortButton();

		cancelButton.addActionListener(ctr);
		setCancelButton();
	}

	/* -------------------------------- */
	/* Metodos utiles para otras clases */
	/* -------------------------------- */
	public String crear() {
		return crear;
	}
	public String ordenar() {
		return ordenar;
	}
	public String cancelar() {
		return cancelar;
	}

	public int getTam(){
		//return the number introduced in the textfield
		//must be between 1 and 60000
		int tam = Integer.parseInt(size.getText());
		return tam;
	}
	public String progresoSelecion() {
		return progresoSelecion;
	}
	public String progresoBurbuja() {
		return progresoBurbuja;
	}
	
	/* --------------------------------------------------- */
	/* Metodos para resetear el contenido de los JTextArea */
	/* --------------------------------------------------- */
	public void clearArea(){
		area.setText("");
	
	}
	public void clearAreaSelection(){
		areaSelection.setText("");
	
	}
	public void clearAreaBubble(){
		this.areaBubble.setText("");
	
	}
	public void clearSize() {
		size.setText("");
	}

	/* ------------------------------------------------------- */
	/*  Metodos para hacer display de las listas segun su area */
	/* ------------------------------------------------------- */
	public void writeTextArea(java.util.List<Integer> lista) {
		int cont = 1;
		for (int pos = 0; pos < lista.size(); pos++) {
			area.append(Integer.toString(lista.get(pos)) + " ");
			if (cont % 10 == 0) {
				area.append("\n");
				cont = 0;
			} 
			cont++;
		}
	}
	public void writeTextAreaSelection(java.util.List<Integer> lista) {
		int cont = 1;
		for (int pos = 0; pos < lista.size(); pos++) {
			areaSelection.append(Integer.toString(lista.get(pos)) + " ");
			if (cont % 10 == 0) {
				areaSelection.append("\n");
				cont = 0;
			} 
			cont++;
		}
	}
	public void writeTextAreaBubble(java.util.List<Integer> lista) {
		int cont = 1;
		for (int pos = 0; pos < lista.size(); pos++) {
			areaBubble.append(Integer.toString(lista.get(pos)) + " ");
			if (cont % 10 == 0) {
				areaBubble.append("\n");
				cont = 0;
			} 
			cont++;
		}
	}
	
	/* ------------------------------------- */
	/* Metodos para escibir sobre los JLabel */
	/* ------------------------------------- */
	public void messageArea(String texto) {
		this.messageArea.setText(texto);
	}
	public void messageAreaSelection(String texto) {
		this.messageAreaSelection.setText(texto);
	}
	public void messageAreaBubble(String texto) {
		this.messageAreaBubble.setText(texto);
	}
	public void comment(String texto) {
		comment.setText(texto);
	}

	/* --------------------------------------------- */
	/* Metodos para controlar las barras de progreso */
	/* --------------------------------------------- */
	public void establecerProgresoSelection (Integer newValue) {
		progressSelection.setValue(newValue);
	}

	public void establecerProgresoBubble (Integer newValue) {
		progressBubble.setValue(newValue);
	}
	
}
