package negocio;

import org.openstreetmap.gui.jmapviewer.Coordinate;

/**
 
 */
public class Nodo {

    private int id;
    private String nombre;
    private Coordinate coordenadas;
    private Arista arista;
    private double peso;
    private int cantidadVecinos;
    
    public Nodo(int id, String nombre, Coordinate coordenadas, double peso) {
        this.id = id;
        this.nombre = nombre;
        this.coordenadas = coordenadas;
        this.peso=peso;
        this.cantidadVecinos = 0;
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
		return cantidadVecinos;
	}
	/*
	public void aumentarCantVecinos() {
		this.setCantidadVecinos(this.obtenerCantidadVecinos() + 1);
	}
*/
	public Object obtenerPeso() {
		return peso;
	}

}
