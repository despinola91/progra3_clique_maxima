package interfaz;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;

import negocio.Grafo;
import negocio.Nodo;
import negocio.Arista;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.awt.Point;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JComboBox;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import java.awt.Font;

import javax.swing.table.DefaultTableModel;
import javax.swing.JCheckBox;



public class MainForm 
{
	public JFrame frmNodosGolosos;

	private JPanel panelgrafo;
	private JPanel panelGrafo;
	private JPanel panelDetalles;

	private JComboBox<String> comboBox_Nodo2;
	private JComboBox<String> comboBox_Nodo1;
	private JComboBox<String> comboBox_Algoritmo;
	private JButton btnReset;
	private JTextPane textCantidadRegiones;

	private JMapViewer _grafo;
	private JLabel lblBandera;
	
	private Grafo grafo;

	private JButton btnUnirNodos;
	private JButton btnEliminarNodos;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() {
				try {
					MainForm window = new MainForm();
					window.frmNodosGolosos.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainForm() 
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() 
	{
		frmNodosGolosos = new JFrame();
		frmNodosGolosos.setResizable(false);
		frmNodosGolosos.setTitle("Clique Maxima");
		frmNodosGolosos.setBounds(200, 25, 792, 575);
		frmNodosGolosos.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmNodosGolosos.getContentPane().setLayout(null);
		
		panelgrafo = new JPanel();
		panelgrafo.setBounds(8, 10, 437, 512);
		frmNodosGolosos.getContentPane().add(panelgrafo);
		
		panelGrafo = new JPanel();
		panelGrafo.setToolTipText("");
		panelGrafo.setBounds(457, 11, 297, 377);
		frmNodosGolosos.getContentPane().add(panelGrafo);		
		panelGrafo.setLayout(null);
		
		_grafo = new JMapViewer();
		_grafo.setCenter(new Point(1075, 700));
		_grafo.setDisplayPosition(new Coordinate(-41.362952, -33.382671), 4);
		_grafo.setPreferredSize(new Dimension(437, 512));
				
		panelgrafo.add(_grafo);
		
		panelDetalles = new JPanel();
		panelDetalles.setBounds(455, 399, 299, 123);
		frmNodosGolosos.getContentPane().add(panelDetalles);
		panelDetalles.setLayout(null);
		
		lblBandera = new JLabel("");
		lblBandera.setIcon(new ImageIcon("fondo.jpg"));
		lblBandera.setBounds(8, 0, 773, 536);
		frmNodosGolosos.getContentPane().add(lblBandera);
		
		grafo = new Grafo();

		detectarCoordenadas();	
		cargarRelaciones();
		//dividirRegiones();
		//reset();
	}
	
	private void detectarCoordenadas() 
	{	
		_grafo.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent e) 
			{
			if (e.getButton() == MouseEvent.BUTTON1)
			{
				Coordinate coordenadas = (Coordinate)_grafo.getPosition(e.getPoint());
				String nombre = JOptionPane.showInputDialog("Nombre provincia: ");

				if (nombre != null && !nombre.isEmpty()) {
					try {
						grafo.agregarNodo(nombre, coordenadas);
						_grafo.addMapMarker(new MapMarkerDot(nombre, coordenadas));
                    } catch (IllegalArgumentException ex) {
						JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }

					cargarDesplegablesNodos();
				}
			}}
		});
	}
	
	private void dibujarArista(Coordinate coordenadaProv1, Coordinate coordenadaProv2) {
	    ArrayList<Coordinate> listaCoordenadas = new ArrayList<>();
	    
		//Para dibujar una arista entre dos vertices, el objeto MapPolygonImpl requiere unir A con B y luego A nuevamente.
	    listaCoordenadas.add(coordenadaProv1);
	    listaCoordenadas.add(coordenadaProv2);
	    listaCoordenadas.add(coordenadaProv1);
	    
		MapPolygonImpl relacion = new MapPolygonImpl(listaCoordenadas);
	    _grafo.addMapPolygon(relacion);
	}
	
	private void dibujarAristaRegiones(Coordinate coordenadaProv1, Coordinate coordenadaProv2, Color color) {
	    ArrayList<Coordinate> listaCoordenadas = new ArrayList<>();
	    
	    listaCoordenadas.add(coordenadaProv1);
	    listaCoordenadas.add(coordenadaProv2);
	    listaCoordenadas.add(coordenadaProv1);
	    
	    MapPolygonImpl relacion = new MapPolygonImpl(listaCoordenadas);
	    relacion.setColor(color);
	    _grafo.addMapPolygon(relacion);
	}
	
	private void dividirRegiones() {
		
	    JLabel lblTituloRegiones = new JLabel("Creacion de regiones");
	    lblTituloRegiones.setFont(new Font("Tahoma", Font.ITALIC, 16));
	    lblTituloRegiones.setBounds(25, 11, 208, 22);
	    panelDetalles.add(lblTituloRegiones);
	    
	    JLabel lblRegiones = new JLabel("Regiones");
	    lblRegiones.setBounds(25, 54, 66, 23);
	    panelDetalles.add(lblRegiones);
	    
	    textCantidadRegiones = new JTextPane();
	    textCantidadRegiones.setBounds(133, 54, 62, 22);
	    panelDetalles.add(textCantidadRegiones);
	    
	    JLabel lblAlgoritmo = new JLabel("Algoritmo");
	    lblAlgoritmo.setBounds(25, 97, 86, 23);
	    panelDetalles.add(lblAlgoritmo);
	    
	    comboBox_Algoritmo = new JComboBox();
	    comboBox_Algoritmo.setToolTipText("");
	    comboBox_Algoritmo.setBounds(133, 97, 138, 22);
	    panelDetalles.add(comboBox_Algoritmo);

	    
	    
		JButton btnCrearRegiones = new JButton("Crear Regiones");
		btnCrearRegiones.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String input = textCantidadRegiones.getText();
		        try {
		            int numRegiones = Integer.parseInt(input);
		            if (numRegiones > 0 && numRegiones <= grafo.obtenerDimensionMatrizArista()) {
		                System.out.println("Numero de regiones: " + numRegiones);
		                String algoritmo = (String) comboBox_Algoritmo.getSelectedItem();
		                
		                if (grafo.esGrafoConexo(grafo.obtenerMatrizArista())) {
	
		                }else {
							JOptionPane.showMessageDialog(null, "Todas los Nodos deben tener al menos una similitud cargada (Grafo inconexo!)", "Error", JOptionPane.ERROR_MESSAGE);
						}
		            } else {
		                JOptionPane.showMessageDialog(null, "La cantidad de regiones debe ser entre 1 y la cantidad de Nodos creadas.", "Error", JOptionPane.ERROR_MESSAGE);
		            }
		        } catch (NumberFormatException ex) {
		            JOptionPane.showMessageDialog(null, "Debe ingresar un numero entero.", "Error", JOptionPane.ERROR_MESSAGE);
		        }
		    }
		});
		btnCrearRegiones.setBounds(25, 149, 136, 23);
		panelDetalles.add(btnCrearRegiones);
		
				btnReset = new JButton("Reiniciar grafo");
				btnReset.setBounds(68, 216, 190, 23);
				panelDetalles.add(btnReset);
	}
	
	private void cargarRelaciones() {
	    
	    comboBox_Nodo1 = new JComboBox();
	    comboBox_Nodo1.setToolTipText("Provincia");
	    comboBox_Nodo1.setBounds(133, 44, 138, 22);
	    panelGrafo.add(comboBox_Nodo1);
	    
	    comboBox_Nodo2 = new JComboBox();
	    comboBox_Nodo2.setBounds(133, 80, 138, 22);
	    panelGrafo.add(comboBox_Nodo2);
	    
	    JLabel lblProvincia1 = new JLabel("Nodo 1");
	    lblProvincia1.setFont(new Font("Tahoma", Font.PLAIN, 14));
	    lblProvincia1.setBounds(25, 44, 77, 23);
	    panelGrafo.add(lblProvincia1);
	    
	    JLabel lblProvincia2 = new JLabel("Nodo 2");
	    lblProvincia2.setFont(new Font("Tahoma", Font.PLAIN, 14));
	    lblProvincia2.setBounds(25, 80, 77, 23);
	    panelGrafo.add(lblProvincia2);
	    
	    btnUnirNodos = new JButton("Unir Nodos");
		btnUnirNodos.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            String nombreNodo1 = comboBox_Nodo1.getSelectedItem().toString();	            
	            String nombreNodo2 = comboBox_Nodo2.getSelectedItem().toString();
	            
	        }
	    });
	    btnUnirNodos.setBounds(10, 117, 134, 23);
	    panelGrafo.add(btnUnirNodos);
	    
	    btnEliminarNodos = new JButton("Eliminar Nodos");
		btnEliminarNodos.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            String nodo1 = comboBox_Nodo1.getSelectedItem().toString();            
	            String nodo2 = comboBox_Nodo2.getSelectedItem().toString();
	            
	            grafo.eliminarArista(nodo1, nodo2);
				dibujargrafo(grafo.obtenerMatrizArista());
	        }
	    });
	    btnEliminarNodos.setBounds(154, 117, 138, 23);
	    panelGrafo.add(btnEliminarNodos);
	    
	    JLabel lblTituloGrafo = new JLabel("Creacion de Grafo");
	    lblTituloGrafo.setFont(new Font("Tahoma", Font.ITALIC, 16));
	    lblTituloGrafo.setBounds(25, 11, 208, 22);
	    panelGrafo.add(lblTituloGrafo);
	    
	    JComboBox comboBox_Criterio = new JComboBox();
	    comboBox_Criterio.setBounds(25, 209, 134, 22);
	    panelGrafo.add(comboBox_Criterio);
        comboBox_Criterio.addItem("Peso");
        comboBox_Criterio.addItem("Grado");
	    
	    JLabel lblCriterio = new JLabel("Criterio");
	    lblCriterio.setFont(new Font("Tahoma", Font.PLAIN, 14));
	    lblCriterio.setBounds(25, 176, 77, 22);
	    panelGrafo.add(lblCriterio);
	    
	    JCheckBox check_Random = new JCheckBox("Random");
	    check_Random.setFont(new Font("Tahoma", Font.PLAIN, 14));
	    check_Random.setBounds(174, 208, 97, 23);
	    panelGrafo.add(check_Random);
	    
	    JButton btnEjecutar = new JButton("Ejecutar");
	    btnEjecutar.setBounds(25, 292, 89, 23);
	    panelGrafo.add(btnEjecutar);
	}

	private void dibujargrafo(int[][] matrizDeRelacion) {

		_grafo.removeAllMapPolygons();
		for (int i = 0; i < matrizDeRelacion.length; i++) {
			for (int j = 0; j < matrizDeRelacion.length; j++) {  
				if (matrizDeRelacion[i][j] > 0) {
					dibujarArista(grafo.obtenerNodoPorId(i).obtenerCoordenadas(), grafo.obtenerNodoPorId(j).obtenerCoordenadas());
				}
			}
		}
	}

//	private void dibujarRegiones(int[][] matrizDeRelacion) {
//		_grafo.removeAllMapPolygons();
//		for (int i = 0; i < matrizDeRelacion.length; i++) {
//			for (int j = 0; j < matrizDeRelacion.length; j++) {  
//				if (matrizDeRelacion[i][j] > 0) {
//					dibujarAristaRegiones(grafo.obtenerNodoPorId(i).obtenerCoordenadas(), grafo.obtenerNodoPorId(j).obtenerCoordenadas(), Color.RED);
//				}
//			}
//		}
//	}
	
//	private void reset() {   ver mas tarde si reusamos
//	    btnReset.addActionListener(new ActionListener() {
//	        public void actionPerformed(ActionEvent arg0) {
//	            _grafo.removeAllMapMarkers();
//	            _grafo.removeAllMapPolygons();
//	            grafo.reiniciarGrafo();
//	      
//				cargarDesplegablesNodos();
//
//				btnUnirNodos.setEnabled(true);
//				btnEliminarNodos.setEnabled(true);
//				textSimilitud.setText(null);
//				textCantidadRegiones.setText(null);
//	        }
//	    });
//	}

	private void cargarDesplegablesNodos() {
		comboBox_Nodo1.setModel(new DefaultComboBoxModel<>(grafo.obtenerNodos().toArray(new String[0])));
		comboBox_Nodo2.setModel(new DefaultComboBoxModel<>(grafo.obtenerNodos().toArray(new String[0])));
	}	
}