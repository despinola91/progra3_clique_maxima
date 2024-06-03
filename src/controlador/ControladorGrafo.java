package controlador;

import modelo.Grafo;
import vista.MainView;

public class ControladorGrafo {
	private Grafo grafo; // Modelo
    private MainView vista; //vista
    
    public ControladorGrafo(Grafo grafo, MainView vista) {
        this.grafo = grafo;
        this.vista = vista;
        inicializar();
    }

    private void inicializar() {
        //vista.setController(this);
    }

}
