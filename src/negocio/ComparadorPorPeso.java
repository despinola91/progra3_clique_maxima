package negocio;

import java.util.Comparator;

public class ComparadorPorPeso implements Comparator<Vertice> {

	@Override
	public int compare(Vertice uno, Vertice otro) {
		if (uno.obtenerPeso() == otro.obtenerPeso())
			return 0;
		if (uno.obtenerPeso() > otro.obtenerPeso())
			return -1;
		else
			return 1;
	}

}