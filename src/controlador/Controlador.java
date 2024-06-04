package controlador;

import modelo.Grafo;
import vista.Vista;

public class Controlador {
	private Grafo grafo; // Modelo
    private Vista vista; // Vista
    
    public Controlador(Grafo grafo, Vista vista) {
        this.grafo = grafo;
        this.vista = vista;
    }
    
    public void agregarVertice(String nombre, int peso) {
    	this.grafo.agregarVertice(nombre, peso);
    }

	public void mostrarPantallaBienvenida() {
		// TODO Auto-generated method stub
		this.vista.mostrarPantallaBienvenida();
	}
	
	public void mostrarPantallaGrafo() {
		this.vista.mostrarPantallaGrafo();
	}
}
