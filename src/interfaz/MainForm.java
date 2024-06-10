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
import negocio.SolverGoloso;
import negocio.Vertice;
import negocio.Clique;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.awt.Point;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.JCheckBox;

public class MainForm 
{
	public JFrame frmNodosGolosos;

	private JPanel panelgrafo;
	private JPanel panelControlRelaciones;

	private JComboBox<String> comboBox_Vertice2;
	private JComboBox<String> comboBox_Vertice1;
	private JButton btnReset;

	private JMapViewer _grafo;
	private JLabel lblBandera;
	
	private Grafo grafo;

	private JButton btnUnirVertices;
	private JButton btnEliminarUnion;

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
		panelControlRelaciones.setBounds(453, 88, 313, 348);
		frmNodosGolosos.getContentPane().add(panelControlRelaciones);		
		panelControlRelaciones.setLayout(null);
		
		_grafo = new JMapViewer();
		_grafo.setCenter(new Point(1075, 700));
		_grafo.setDisplayPosition(new Coordinate(-41.362952, -33.382671), 9);
		_grafo.setPreferredSize(new Dimension(437, 512));
				
		panelgrafo.add(_grafo);
		
		lblBandera = new JLabel("");
		lblBandera.setIcon(new ImageIcon("fondo.jpg"));
		lblBandera.setBounds(8, 0, 784, 538);
		frmNodosGolosos.getContentPane().add(lblBandera);
		
		grafo = new Grafo();

		detectarCoordenadas();	
		cargarRelaciones();
		
		reset();
	}
	
	private void detectarCoordenadas() {	
	    _grafo.addMouseListener(new MouseAdapter() {
	        @Override
	        public void mouseClicked(MouseEvent e) {
	            if (e.getButton() == MouseEvent.BUTTON1) {
	                Coordinate coordenadas = (Coordinate) _grafo.getPosition(e.getPoint());
	                String nombre = String.valueOf(grafo.obtenerVertices().size() + 1);
	                String tituloDialogo = "Vertice nro " + nombre;
	                String pesoString = JOptionPane.showInputDialog(null,
	                        "Peso del vertice (utilizar separación por punto, ej.: 3.5):",
	                        tituloDialogo,
	                        JOptionPane.PLAIN_MESSAGE);

	                if (pesoString != null && !pesoString.trim().isEmpty()) {
	                    try {
	                        float peso = Float.parseFloat(pesoString);
	                        nombre = nombre + " (peso: " + pesoString + ")";

	                        grafo.agregarVertice(nombre, coordenadas, peso);
	                        _grafo.addMapMarker(new MapMarkerDot(nombre, coordenadas));

	                        cargarDesplegablesVertices();
	                    } catch (NumberFormatException ex) {
	                        JOptionPane.showMessageDialog(null, "Debe ingresar un nemero valido", "Error", JOptionPane.ERROR_MESSAGE);
	                    } catch (IllegalArgumentException ex) {
	                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	                    }
	                } else {
	                    JOptionPane.showMessageDialog(null, "El peso del vertice no puede estar vacio", "Error", JOptionPane.ERROR_MESSAGE);
	                }
	            }
	        }
	    });
	}

	
	private void dibujarArista(Coordinate coordenadaVertice1, Coordinate coordenadaVertice2) {
	    ArrayList<Coordinate> listaCoordenadas = new ArrayList<>();
	    
		//Para dibujar una arista entre dos vertices, el objeto MapPolygonImpl requiere unir A con B y luego A nuevamente.
	    listaCoordenadas.add(coordenadaVertice1);
	    listaCoordenadas.add(coordenadaVertice2);
	    listaCoordenadas.add(coordenadaVertice1);
	    
		MapPolygonImpl relacion = new MapPolygonImpl(listaCoordenadas);
	    _grafo.addMapPolygon(relacion);
	}
	
	private void dibujarAristaDeClique(Coordinate coordenadaVertice1, Coordinate coordenadaVertice2, Color color) {
	    ArrayList<Coordinate> listaCoordenadas = new ArrayList<>();
	    
	    listaCoordenadas.add(coordenadaVertice1);
	    listaCoordenadas.add(coordenadaVertice2);
	    listaCoordenadas.add(coordenadaVertice1);
	    
	    MapPolygonImpl relacion = new MapPolygonImpl(listaCoordenadas);
	    relacion.setColor(color);
	    _grafo.addMapPolygon(relacion);
	}
	
	private void cargarRelaciones() {
	    
	    JLabel lblTituloGrafo = new JLabel("Creacion de Grafo");
	    lblTituloGrafo.setForeground(new Color(102, 205, 170));
	    lblTituloGrafo.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 16));
	    lblTituloGrafo.setBounds(25, 12, 208, 22);
	    panelControlRelaciones.add(lblTituloGrafo);
	    
	    comboBox_Vertice1 = new JComboBox<String>();
	    comboBox_Vertice1.setBounds(141, 57, 138, 22);
	    panelControlRelaciones.add(comboBox_Vertice1);
	    
	    comboBox_Vertice2 = new JComboBox<String>();
	    comboBox_Vertice2.setBounds(141, 91, 138, 22);
	    panelControlRelaciones.add(comboBox_Vertice2);
	    
	    JLabel lblVertice1 = new JLabel("Vertice 1");
	    lblVertice1.setFont(new Font("Tahoma", Font.PLAIN, 14));
	    lblVertice1.setBounds(25, 56, 62, 23);
	    panelControlRelaciones.add(lblVertice1);
	    
	    JLabel lblVertice2 = new JLabel("Vertice 2");
	    lblVertice2.setFont(new Font("Tahoma", Font.PLAIN, 14));
	    lblVertice2.setBounds(25, 90, 62, 23);
	    panelControlRelaciones.add(lblVertice2);
	    
	    JCheckBox chckbxRandom = new JCheckBox("Random");
		chckbxRandom.setToolTipText("Agrega un elemento random al algoritmo que sugiere una clique.");
	    chckbxRandom.setFont(new Font("Tahoma", Font.PLAIN, 14));
	    chckbxRandom.setBounds(183, 255, 97, 23);
	    panelControlRelaciones.add(chckbxRandom);
	    
	    JLabel lblCriterio = new JLabel("Criterio");
	    lblCriterio.setFont(new Font("Tahoma", Font.PLAIN, 14));
	    lblCriterio.setBounds(26, 231, 46, 14);
	    panelControlRelaciones.add(lblCriterio);
    
        JComboBox<String> comboBox_Criterio = new JComboBox<String>();
	    comboBox_Criterio.setFont(new Font("Tahoma", Font.PLAIN, 12));
	    comboBox_Criterio.setBounds(25, 256, 120, 22);
	    panelControlRelaciones.add(comboBox_Criterio);
        comboBox_Criterio.addItem("Peso");
        comboBox_Criterio.addItem("Grado");
	    
        //UNIR VERTICES
        btnUnirVertices = new JButton("Agregar arco");
        btnUnirVertices.setBackground(new Color(102, 205, 170));
        btnUnirVertices.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnUnirVertices.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (comboBox_Vertice1.getSelectedItem() == null || comboBox_Vertice2.getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(null, "Deben haber vertices para seleccionar", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String nombreVertice1 = comboBox_Vertice1.getSelectedItem().toString();	            
                String nombreVertice2 = comboBox_Vertice2.getSelectedItem().toString();

                try {
                    if (!nombreVertice1.equals(nombreVertice2)) {
                        grafo.agregarArista(nombreVertice1, nombreVertice2);
                        dibujargrafo(grafo.obtenerMatrizArista());
                    } else {
                        JOptionPane.showMessageDialog(null, "Los dos Vertices seleccionados son iguales, por favor seleccione Vertices diferentes.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Debe ingresar un numero", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnUnirVertices.setBounds(10, 140, 134, 23);
        panelControlRelaciones.add(btnUnirVertices);

	    
	  //ELIMINAR UNION DE VERTICES
	    btnEliminarUnion = new JButton("Eliminar arco");
	    btnEliminarUnion.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnEliminarUnion.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            String nombreVertice1 = comboBox_Vertice1.getSelectedItem().toString();            
	            String nombreVertice2 = comboBox_Vertice2.getSelectedItem().toString();
	            
	            grafo.eliminarArista(nombreVertice1, nombreVertice2);
				dibujargrafo(grafo.obtenerMatrizArista());
	        }
	    });
	    btnEliminarUnion.setBounds(165, 140, 138, 23);
	    panelControlRelaciones.add(btnEliminarUnion);
	    
	    //EJECUTAR SOLVER
	    JButton btnEjecutar = new JButton("Ejecutar");
	    btnEjecutar.setBackground(new Color(244, 164, 96));
	    btnEjecutar.setFont(new Font("Tahoma", Font.PLAIN, 12));
	    btnEjecutar.setBounds(197, 315, 89, 23);
	    panelControlRelaciones.add(btnEjecutar);
	    
	    // Inicializa el botón de reset aquí
	    btnReset = new JButton("Reiniciar");
	    btnReset.setBounds(25, 276, 80, 25);
	    btnReset.setFont(new Font("Tahoma", Font.PLAIN, 12));
	    btnReset.setBounds(25, 313, 102, 25);
	    panelControlRelaciones.add(btnReset);
	    
	    JLabel lblTituloCliqueMax = new JLabel("Clique Maxima con Golosos");
	    lblTituloCliqueMax.setForeground(new Color(244, 164, 96));
	    lblTituloCliqueMax.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 16));
	    lblTituloCliqueMax.setBounds(25, 199, 278, 22);
	    panelControlRelaciones.add(lblTituloCliqueMax);
	    
	    btnEjecutar.addActionListener(new ActionListener() {	
	        public void actionPerformed(ActionEvent e) {
	            String criterio = comboBox_Criterio.getSelectedItem().toString();
	            Clique clique;
	            SolverGoloso solver;

	            if (criterio.equals("Peso")) {
	                solver = new SolverGoloso(grafo, new Comparator<Vertice>() {
	                    @Override
	        			public int compare(Vertice uno, Vertice otro) 
	        			{
	                        if (uno.obtenerPeso() > otro.obtenerPeso()) {
	                            return -1;
	                        }
	                        if (uno.obtenerPeso() < otro.obtenerPeso()) {
	                            return 1;
	                        }
	                        else {
	                            return 0;
	                        }
	        			}
	                });
	            }
	            else {
	                solver = new SolverGoloso(grafo, new Comparator<Vertice>() {
	                    @Override
	                    public int compare(Vertice uno, Vertice otro) {
	                    	return -uno.obtenerGrado() + otro.obtenerGrado();
	                    }
	                });
	            }

	            if (chckbxRandom.isSelected()) {
	                clique = solver.resolverConElementoRandom();
	            } else {
	                clique = solver.resolver();
	            }

	            //Se dibujan las aristas de la clique
	            Color colorClique = Color.GREEN;
	            ArrayList<Vertice> vertices = clique.obtenerVertices();
	            for (int i = 0; i < vertices.size(); i++) {
	                for (int j = i + 1; j < vertices.size(); j++) {
	                    Vertice verticeA = vertices.get(i);
	                    Vertice verticeB = vertices.get(j);
	                    dibujarAristaDeClique(verticeA.obtenerCoordenadas(), verticeB.obtenerCoordenadas(), colorClique);
	                }
	            }
	            _grafo.repaint();
	        }
	    });
	}

	private void dibujargrafo(int[][] matrizDeRelacion) {

		_grafo.removeAllMapPolygons();
		for (int i = 0; i < matrizDeRelacion.length; i++) {
			for (int j = 0; j < matrizDeRelacion.length; j++) {  
				if (matrizDeRelacion[i][j] > 0) {
					dibujarArista(grafo.obtenerVerticePorId(i).obtenerCoordenadas(), grafo.obtenerVerticePorId(j).obtenerCoordenadas());
				}
			}
		}
	}
	
	private void reset() {   
	    btnReset.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent arg0) {
	        	_grafo.removeAllMapMarkers();
	            _grafo.removeAllMapPolygons();
	            grafo.reiniciarGrafo();
	            
	            // Vaciar los modelos de las listas desplegables
	            comboBox_Vertice1.setModel(new DefaultComboBoxModel<>());
	            comboBox_Vertice2.setModel(new DefaultComboBoxModel<>());

	            btnUnirVertices.setEnabled(true);
	            btnEliminarUnion.setEnabled(true);
	        }
	    });
	}

	private void cargarDesplegablesVertices() {
	    ArrayList<Vertice> vertices = grafo.obtenerVertices();
	    String[] nombresVertices = new String[vertices.size()];
	    
	    for (int i = 0; i < vertices.size(); i++) {
	        nombresVertices[i] = vertices.get(i).obtenerNombre();
	    }

	    comboBox_Vertice1.setModel(new DefaultComboBoxModel<>(nombresVertices));
	    comboBox_Vertice2.setModel(new DefaultComboBoxModel<>(nombresVertices));
	}
}