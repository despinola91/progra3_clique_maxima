package negocio;

import java.util.ArrayList;

public class Clique {

	ArrayList<Nodo> _listaNodo;
	private int _peso;

	public Clique() {
		_listaNodo = new ArrayList<Nodo>();
		this._peso = 0;
	}

	public void agregarNodoAClique(Nodo nodo, Grafo grafo, Arista arista) {  //falta agregar peso arista?
		if (!_listaNodo.contains(nodo) && esVecinodeTodos(nodo, grafo)) {
			_listaNodo.add(nodo);
			
		}
	}

	private boolean esVecinodeTodos(Nodo nodo, Grafo grafo) {
		boolean ret = true;
		for (Nodo iteracion : _listaNodo) {
			ret = ret && grafo.obtenerindiceConVecinos().get(iteracion.obtenerIndiceNodo()).contains(nodo);
		}
		return ret;
	}

	public Clique cliqueConMasPeso(Clique otra) {
		if (this._peso > otra.obtenerPeso()) {
			return this;
		}
		return otra;
	}

	public ArrayList<Nodo> obtenerListaNodo() {
		return _listaNodo;
	}

	public int obtenerPeso() {
		return _peso;
	}

	public int obtenerGrado() {
		return this._listaNodo.size();
	}

}
