package negocio;

import java.util.ArrayList;

public class Instancia
{
	private int _capacidad;
	private ArrayList<Objeto> _objetos;
	
	public Instancia(int capacidad)
	{
		_capacidad = capacidad;
		_objetos = new ArrayList<Objeto>();
	}
	
	public void agregar(Objeto objeto)
	{
		_objetos.add(objeto);
	}
	
	public int getTamano()
	{
		return _objetos.size();
	}
	
	public int getCapacidad()
	{
		return _capacidad;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Objeto> getObjetos()
	{
		return (ArrayList<Objeto>) _objetos.clone();
	}

	public Objeto getObjeto(int i)
	{
		return _objetos.get(i);
	}
}
