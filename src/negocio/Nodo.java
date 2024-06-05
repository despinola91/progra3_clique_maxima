package negocio;

import org.openstreetmap.gui.jmapviewer.Coordinate;

/**
 
 */
public class Nodo {

    private int id;
    private String nombre;
    private Coordinate coordenadas;
    private Arista arista;
    private int peso;
    
    public Nodo(int id, String nombre, Coordinate coordenadas, int peso) {
        this.id = id;
        this.nombre = nombre;
        this.coordenadas = coordenadas;
        this.peso=peso;
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

	public Object obtenerPeso() {
		return peso;
	}








}
