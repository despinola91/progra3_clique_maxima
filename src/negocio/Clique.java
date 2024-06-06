package negocio;

import java.util.ArrayList;

public class Clique {

	ArrayList<Vertice> _vertices;
	private int _peso;

	public Clique() {
		_vertices = new ArrayList<Vertice>();
		this._peso = 0;
	}

	public ArrayList<Vertice> obtenerVertices() {
		return _vertices;
	}

	public int obtenerPeso() {
		for (Vertice vertice :_vertices) {
			_peso += vertice.obtenerPeso();
		}
		return _peso;
	}

	public int obtenerGrado() {
		return this._vertices.size();
	}

    public void agregarVertice(Vertice vertice) {
        _vertices.add(vertice);
    }
}
