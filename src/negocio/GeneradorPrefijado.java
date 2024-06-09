package negocio;

public class GeneradorPrefijado implements Generador
{
	private boolean _flag;
	private int _entero;
	
	public GeneradorPrefijado(boolean flag)
	{
		_flag = flag;
	}
	
	public GeneradorPrefijado(int entero)
	{
		_entero = entero;
	}
	
	@Override
	public boolean nextBoolean()
	{
		return _flag;
	}

	@Override
	public int nextInt(int rango)
	{
		return _entero;
	}
}
