package negocio;

import org.openstreetmap.gui.jmapviewer.Coordinate;

/**
 
 */
public class Nodo {

    private int id;
    private String nombre;
    private Coordinate coordenadas;
    
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

	public Object getCantidadVecinos() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object obtenerPeso(Arista arista) {
		arista.obtenerPeso();
		return null;
	}

	public Object getIndiceNodo() {
		// TODO Auto-generated method stub
		return null;
	}




}
