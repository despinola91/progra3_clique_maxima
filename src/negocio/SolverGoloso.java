package negocio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SolverGoloso
{
	private Instancia _instancia;
	private Comparator<Objeto> _comparador;
	
	public SolverGoloso(Instancia instancia, Comparator<Objeto> comparador)
	{
		_instancia = instancia;
		_comparador = comparador;
	}
	
	public Solucion resolver()
	{
		Solucion ret = new Solucion();
		for(Objeto objeto: objetosOrdenados())
		{
			if( ret.peso() + objeto.getPeso() <= _instancia.getCapacidad() )
				ret.agregar(objeto);
		}
		
		return ret;
	}

	private ArrayList<Objeto> objetosOrdenados()
	{
		ArrayList<Objeto> ret = _instancia.getObjetos();
		Collections.sort(ret, _comparador);
		
		return ret;
	}
}
