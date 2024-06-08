package negocio;

import java.util.ArrayList;

public class Clique {

	ArrayList<Vertice> _vertices;
	private double _peso;

	public Clique() {
		_vertices = new ArrayList<Vertice>();
		_peso = 0;
	}

	public ArrayList<Vertice> obtenerVertices() {
		return _vertices;
	}

	public double obtenerPeso() {		
		return _peso;
	}

	public int obtenerGrado() {
		return _vertices.size();
	}

    public void agregarVertice(Vertice vertice) {
        _vertices.add(vertice);
		_peso += vertice.obtenerPeso();
    }
}
