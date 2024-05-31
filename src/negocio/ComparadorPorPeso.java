package negocio;

import java.util.Comparator;

public class ComparadorPorPeso implements Comparator<Objeto>
{
	@Override
	public int compare(Objeto uno, Objeto otro)
	{
		return uno.getPeso() - otro.getPeso();
	}
}
