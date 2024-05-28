package negocio;

import java.util.Comparator;

public class ComparadorPorPeso implements Comparator<Nodo> {

	@Override
	public int compare(Nodo uno, Nodo otro) {
		if (uno.obtenerPeso() == otro.obtenerPeso())//AGREGAR PARA LA ARISTA
			return 0;
		if (uno.getPeso() > otro.getPeso())
			return -1;
		else
			return 1;
	}

}