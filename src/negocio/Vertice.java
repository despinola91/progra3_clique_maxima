package negocio;

import org.openstreetmap.gui.jmapviewer.Coordinate;

public class Vertice {

    private int _id;
    private String _nombre;
    private Coordinate _coordenadas;
    private double _peso;
    
    public Vertice(int id, String nombre, Coordinate coordenadas, double peso) {
        this._id = id;
        this._nombre = nombre;
        this._coordenadas = coordenadas;
        this._peso = peso;
    }

    public int obtenerId() {
        return _id;
    }

    public String obtenerNombre() {
        return _nombre;
    }

    public Coordinate obtenerCoordenadas() {
        return _coordenadas;
    }
	
	public double obtenerPeso() {
		return _peso;
	}

    public Integer obtenerGrado() {
        // TODO Auto-generated method stub
        Integer grado = 0;
        return grado;
    }

    public boolean perteneceAClique(Clique clique, Grafo _grafo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'perteneceAClique'");
    }
}
