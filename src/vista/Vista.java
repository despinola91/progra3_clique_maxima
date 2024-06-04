package vista;

import controlador.Controlador;

public class Vista {
	private Controlador controlador; // Referencia a controlador para usar en botones de accion
	
	public Vista() {}
	
	public void setControlador(Controlador controlador) {
		this.controlador = controlador;
	}
	
	/*
	 * Metodos para renderización de pantallas/componentes
	 */
	public void mostrarCliqueMaxima(int[][] matrizGrafo) {
		// Codigo de windows builder para mostrar grafo en pantalla
	}
	
	public void mostrarPantallaBienvenida() {
		// Inicio de pantalla de bienvenida, la pantalla tendrá botones que llaman al controlador para mostrar pantalla de grafo
	}

	public void mostrarPantallaGrafo() {
		// TODO Auto-generated method stub
		
	}
}
