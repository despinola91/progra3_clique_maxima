package interfaz;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.Color;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class PantallaBienvenida {

	private JFrame frame;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PantallaBienvenida window = new PantallaBienvenida();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public PantallaBienvenida() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("Clique Maxima"); // Titulo de la ventana
		frame.getContentPane().setBackground(new Color(210, 180, 140));
		frame.setBounds(300, 20, 628, 399);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JButton botonPlay = new JButton("Comenzar!");
		botonPlay.setForeground(SystemColor.textHighlight);
		botonPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarPantallaMapa();
			}
		});
		botonPlay.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 31));
		botonPlay.setBounds(194, 188, 237, 65);
		frame.getContentPane().add(botonPlay);

		botonPlay.setBackground(new Color(192, 224, 255));
		// llamar a revalidate() y repaint() sino no cambia el color de fondo...
		botonPlay.revalidate();
		botonPlay.repaint();
		
		JLabel lblNewLabel = new JLabel("Buscando la Clique Maxima");
		lblNewLabel.setFont(new Font("Segoe Print", Font.PLAIN, 38));
		lblNewLabel.setForeground(new Color(230, 230, 250));
		lblNewLabel.setBounds(47, 67, 540, 65);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Buscando la Clique Maxima");
		lblNewLabel_1.setForeground(new Color(0, 0, 0));
		lblNewLabel_1.setFont(new Font("Segoe Print", Font.BOLD, 38));
		lblNewLabel_1.setBounds(47, 67, 540, 65);
		frame.getContentPane().add(lblNewLabel_1);
		
		// agregamos la figura de fondo
		JLabel lblFondo = new JLabel("");
		lblFondo.setForeground(SystemColor.controlHighlight);
		lblFondo.setIcon(new ImageIcon("fondo.jpg"));
		lblFondo.setBounds(0, -56, 622, 433);
		frame.getContentPane().add(lblFondo);

	}

	protected void mostrarPantallaMapa() { // Crea la nueva ventana, la hace visible y cierra la primera
		MainForm frame2 = new MainForm();
		frame2.frmNodosGolosos.setVisible(true);
		getFrame().dispose();
	}

	/*
	 * Necesario tener el jFrame de pantallaInical para poder volver a esta pantalla
	 * cuando se cierre la PantallaJuego, ya que se borran todas las referencias
	 */
	public JFrame getFrame() {
		return frame;
	}
}
