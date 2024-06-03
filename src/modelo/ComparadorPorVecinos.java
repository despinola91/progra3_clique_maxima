package modelo;

import java.util.Comparator;

import controlador.Objeto;

public class ComparadorPorVecinos implements Comparator<Objeto>
{
	@Override
	public int compare(Objeto uno, Objeto otro)
	{
		return -uno.getBeneficio() + otro.getBeneficio();
	}
}
