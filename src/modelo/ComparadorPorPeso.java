package modelo;

import java.util.Comparator;

import controlador.Objeto;

public class ComparadorPorPeso implements Comparator<Objeto>
{
	@Override
	public int compare(Objeto uno, Objeto otro)
	{
		return uno.getPeso() - otro.getPeso();
	}
}
