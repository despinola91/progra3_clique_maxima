package negocio;

import java.util.Comparator;

public class ComparadorPorPeso implements Comparator<Nodo> {

	@Override
	public int compare(Nodo uno, Nodo otro) {
		if (uno.obtenerPeso() == otro.obtenerPeso())//
			return 0;
		if (uno.obtenerPeso() > otro.obtenerPeso())
			return -1;
		else
			return 1;
	}

}