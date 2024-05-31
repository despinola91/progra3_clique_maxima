package negocio;

public class GeneradorPrefijado implements Generador
{
	private boolean[] _bits;
	private int _indice;
	private int _entero;
	
	public GeneradorPrefijado(boolean[] bits)
	{
		_bits = bits;
		_indice = 0;
		_entero = 0;
	}
	
	public GeneradorPrefijado(int entero)
	{
		_entero = entero;
	}
	
	@Override
	public boolean nextBoolean()
	{
		return _bits[_indice++];
	}

	@Override
	public int nextInt(int rango)
	{
		return _entero;
	}
}
