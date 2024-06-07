package negocio;

import java.util.Comparator;

public class ComparadorPorGrado implements Comparator<Vertice> {

	@Override
	public int compare(Vertice uno, Vertice otro) {
		if (uno.obtenerGrado() == otro.obtenerGrado())
			return 0;
		if (uno.obtenerGrado() > otro.obtenerGrado())
			return -1;
		else
			return 1;
	}

}
