package negocio;

import org.openstreetmap.gui.jmapviewer.Coordinate;

/**
 
 */
public class Nodo {

    private int id;
    private String nombre;
    private Coordinate coordenadas;
    private Arista arista;
    
    public Nodo(int id, String nombre, Coordinate coordenadas) {
        this.id = id;
        this.nombre = nombre;
        this.coordenadas = coordenadas;
    }

    public int obtenerId() {
        return id;
    }

    public String obtenerNombre() {
        return nombre;
    }

    public Coordinate obtenerCoordenadas() {
        return coordenadas;
    }

	public Object obtenerCantidadVecinos() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object obtenerPeso(Nodo nodo1, Nodo nodo2) {
		arista.obtenerPeso();  //agregar en arista
		return null;
	}

	public int obtenerIndiceNodo() {
		// TODO Auto-generated method stub
		
		return id;
	}







}
