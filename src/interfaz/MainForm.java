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



public class MainForm 
{
	public JFrame frmNodosGolosos;

	private JPanel panelgrafo;
	private JPanel panelControlRelaciones;
	private JPanel panelControlRegiones;

	private JComboBox<String> comboBox_Provincia2;
	private JComboBox<String> comboBox_Provincia1;
	private JComboBox<String> comboBox_Algoritmo;
	private JButton btnReset;
	
	private JTextPane textSimilitud;
	private JTextPane textCantidadRegiones;

	private JMapViewer _grafo;
	private JLabel lblBandera;
	
	private Grafo grafo;

	private JButton btnCrearRelacion;
	private JButton btnEliminarRelacion;

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
		
		panelControlRelaciones = new JPanel();
		panelControlRelaciones.setToolTipText("");
		panelControlRelaciones.setBounds(457, 11, 297, 251);
		frmNodosGolosos.getContentPane().add(panelControlRelaciones);		
		panelControlRelaciones.setLayout(null);
		
		_grafo = new JMapViewer();
		_grafo.setCenter(new Point(1075, 700));
		_grafo.setDisplayPosition(new Coordinate(-41.362952, -33.382671), 4);
		_grafo.setPreferredSize(new Dimension(437, 512));
				
		panelgrafo.add(_grafo);
		
		panelControlRegiones = new JPanel();
		panelControlRegiones.setBounds(455, 273, 299, 249);
		frmNodosGolosos.getContentPane().add(panelControlRegiones);
		panelControlRegiones.setLayout(null);
		
		lblBandera = new JLabel("");
		lblBandera.setIcon(new ImageIcon("fondo.jpg"));
		lblBandera.setBounds(8, 0, 773, 536);
		frmNodosGolosos.getContentPane().add(lblBandera);
		
		grafo = new Grafo();

		detectarCoordenadas();	
		cargarRelaciones();
		//dividirRegiones();
		mostrarRelaciones(false);
		reset();
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
	    panelControlRegiones.add(lblTituloRegiones);
	    
	    JLabel lblRegiones = new JLabel("Regiones");
	    lblRegiones.setBounds(25, 54, 66, 23);
	    panelControlRegiones.add(lblRegiones);
	    
	    textCantidadRegiones = new JTextPane();
	    textCantidadRegiones.setBounds(133, 54, 62, 22);
	    panelControlRegiones.add(textCantidadRegiones);
	    
	    JLabel lblAlgoritmo = new JLabel("Algoritmo");
	    lblAlgoritmo.setBounds(25, 97, 86, 23);
	    panelControlRegiones.add(lblAlgoritmo);
	    
	    comboBox_Algoritmo = new JComboBox();
	    comboBox_Algoritmo.setToolTipText("");
	    comboBox_Algoritmo.setBounds(133, 97, 138, 22);
	    panelControlRegiones.add(comboBox_Algoritmo);

	    
	    
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
		panelControlRegiones.add(btnCrearRegiones);
		
				btnReset = new JButton("Reiniciar grafo");
				btnReset.setBounds(68, 216, 190, 23);
				panelControlRegiones.add(btnReset);
	}
	
	private void cargarRelaciones() {
	    
	    comboBox_Provincia1 = new JComboBox();
	    comboBox_Provincia1.setToolTipText("Provincia");
	    comboBox_Provincia1.setBounds(133, 44, 138, 22);
	    panelControlRelaciones.add(comboBox_Provincia1);
	    
	    comboBox_Provincia2 = new JComboBox();
	    comboBox_Provincia2.setBounds(133, 80, 138, 22);
	    panelControlRelaciones.add(comboBox_Provincia2);
	    
	    textSimilitud = new JTextPane();
	    textSimilitud.setBounds(133, 130, 62, 22);
	    panelControlRelaciones.add(textSimilitud);
	    
	    JLabel lblProvincia1 = new JLabel("Provincia 1");
	    lblProvincia1.setBounds(25, 44, 77, 23);
	    panelControlRelaciones.add(lblProvincia1);
	    
	    JLabel lblProvincia2 = new JLabel("Provincia 2");
	    lblProvincia2.setBounds(25, 80, 77, 23);
	    panelControlRelaciones.add(lblProvincia2);
	    
	    JLabel lblSimilitud = new JLabel("Similitud");
	    lblSimilitud.setBounds(25, 130, 77, 23);
	    panelControlRelaciones.add(lblSimilitud);
	    
	    btnCrearRelacion = new JButton("Crear Relacion");
		btnCrearRelacion.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            String nombreNodo1 = comboBox_Provincia1.getSelectedItem().toString();	            
	            String nombreNodo2 = comboBox_Provincia2.getSelectedItem().toString();
				
	            String similitudText = textSimilitud.getText();
	            try {
	                int peso = Integer.parseInt(similitudText);
	                if (peso > 0) {
	                    if (!nombreNodo1.equals(nombreNodo2)) {
	                        grafo.agregarArista(nombreNodo1, nombreNodo2, peso);
							dibujargrafo(grafo.obtenerMatrizArista());
							mostrarRelaciones(false);
	                    } else {
	                        JOptionPane.showMessageDialog(null, "Las dos Nodos seleccionadas son iguales, por favor seleccione Nodos diferentes.", "Error", JOptionPane.ERROR_MESSAGE);
	                    }
	                } else {
	                    JOptionPane.showMessageDialog(null, "Debe ingresar un numero entero mayor a 0", "Error", JOptionPane.ERROR_MESSAGE);
	                }
	            } catch (NumberFormatException ex) {
	                JOptionPane.showMessageDialog(null, "Debe ingresar un numero entero", "Error", JOptionPane.ERROR_MESSAGE);
	            }
				catch (IllegalArgumentException ex) {
	                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	            }
	        }
	    });
	    btnCrearRelacion.setBounds(9, 185, 134, 23);
	    panelControlRelaciones.add(btnCrearRelacion);
	    
	    btnEliminarRelacion = new JButton("Eliminar Relacion");
		btnEliminarRelacion.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            String nombreProvincia1 = comboBox_Provincia1.getSelectedItem().toString();            
	            String nombreProvincia2 = comboBox_Provincia2.getSelectedItem().toString();
	            
	            grafo.eliminarArista(nombreProvincia1, nombreProvincia2);
				dibujargrafo(grafo.obtenerMatrizArista());
				mostrarRelaciones(false);
	        }
	    });
	    btnEliminarRelacion.setBounds(151, 185, 138, 23);
	    panelControlRelaciones.add(btnEliminarRelacion);
	    
	    JLabel lblTituloRelaciones = new JLabel("Creacion de relaciones");
	    lblTituloRelaciones.setFont(new Font("Tahoma", Font.ITALIC, 16));
	    lblTituloRelaciones.setBounds(25, 11, 208, 22);
	    panelControlRelaciones.add(lblTituloRelaciones);
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

	private void dibujarRegiones(int[][] matrizDeRelacion) {
		_grafo.removeAllMapPolygons();
		for (int i = 0; i < matrizDeRelacion.length; i++) {
			for (int j = 0; j < matrizDeRelacion.length; j++) {  
				if (matrizDeRelacion[i][j] > 0) {
					dibujarAristaRegiones(grafo.obtenerNodoPorId(i).obtenerCoordenadas(), grafo.obtenerNodoPorId(j).obtenerCoordenadas(), Color.RED);
				}
			}
		}
	}

	private void mostrarRelaciones(boolean soloRegiones) {
		
	    String[] columnas = {"Origen", "Destino", "Similaridad"};
	    DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
	    
		ArrayList<Arista> aristas = new ArrayList<Arista>();
		if(soloRegiones) {
			aristas = grafo.obtenerAristasRegiones();
		}
		else {
			aristas = grafo.obtenerAristas();
		}

	    for (Arista arista : aristas) {
	        Nodo nodoA = arista.obtenerNodos().get(0);
	        Nodo nodoB = arista.obtenerNodos().get(1);
	        int peso = arista.obtenerPeso();
	        Object[] fila = {nodoA.obtenerNombre(), nodoB.obtenerNombre(), peso};
	        modelo.addRow(fila);
	    }
	}
	
	private void reset() {   
	    btnReset.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent arg0) {
	            _grafo.removeAllMapMarkers();
	            _grafo.removeAllMapPolygons();
	            grafo.reiniciarGrafo();
	      
				cargarDesplegablesNodos();

				btnCrearRelacion.setEnabled(true);
				btnEliminarRelacion.setEnabled(true);
				textSimilitud.setText(null);
				textCantidadRegiones.setText(null);
	        }
	    });
	}

	private void cargarDesplegablesNodos() {
		comboBox_Provincia1.setModel(new DefaultComboBoxModel<>(grafo.obtenerNodos().toArray(new String[0])));
		comboBox_Provincia2.setModel(new DefaultComboBoxModel<>(grafo.obtenerNodos().toArray(new String[0])));
	}	
}