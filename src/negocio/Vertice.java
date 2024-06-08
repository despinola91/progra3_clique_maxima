package negocio;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.openstreetmap.gui.jmapviewer.Coordinate;

public class Vertice {

    private int _id;
    private String _nombre;
    private Coordinate _coordenadas;
    private double _peso;
    private Set<Vertice> _vecinos;
    
    public Vertice(int id, String nombre, Coordinate coordenadas, double peso) {
        _id = id;
        _nombre = nombre;
        _coordenadas = coordenadas;
        _peso = peso;
        _vecinos = new HashSet<>();
    }

    @Override
    public boolean equals(Object o) {
            if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertice vertice = (Vertice) o;
        return _id == vertice._id &&
                Double.compare(vertice._peso, _peso) == 0 &&
                Objects.equals(_nombre, vertice._nombre) &&
                Objects.equals(_coordenadas, vertice._coordenadas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_id, _nombre, _coordenadas, _peso);
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

    public int obtenerGrado() {
        return _vecinos.size();
    }
    
    public void agregarVecino(Vertice vertice) {
        _vecinos.add(vertice);
    }

    public void eliminarVecino(Vertice vertice) {
        _vecinos.remove(vertice);
    }
}
