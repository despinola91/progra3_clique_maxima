package negocio;

import java.util.ArrayList;

public class Solucion
{
	private ArrayList<Objeto> _objetos;
	
	public Solucion()
	{
		_objetos = new ArrayList<Objeto>();
	}
	
	public void agregar(Objeto objeto)
	{
		_objetos.add(objeto);
	}

	public int cardinal()
	{
		return _objetos.size();
	}

	public int peso()
	{
		int ret = 0;
		for(Objeto objeto: _objetos)
			ret += objeto.getPeso();
		
		return ret;
	}

	public int beneficio()
	{
		int ret = 0;
		for(Objeto objeto: _objetos)
			ret += objeto.getBeneficio();
		
		return ret;
	}

	@Override
	public String toString() 
	{
		String ret = ""; 
		for (Objeto objeto: _objetos)
			ret = ret + " " + objeto.getNombre();
			
		return " Solucion [objetos=" + ret + " ]";
	}
}
