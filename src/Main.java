import java.util.HashMap;

import controlador.Controlador;
import modelo.Grafo;
import modelo.Vertice;
import vista.Vista;

public class Main {
	
	// Programa principal para ejecutar MVC
	public static void main(String[] args)
	{
		
		int[][] matrizAdyacencia = null;
		HashMap<String, Vertice> vertices = null;
		
		Grafo modelo = new Grafo(matrizAdyacencia, vertices);
		Vista vista = new Vista();
		Controlador controlador = new Controlador(modelo, vista);
		
		vista.setControlador(controlador);
		controlador.mostrarPantallaBienvenida();
	}
}
