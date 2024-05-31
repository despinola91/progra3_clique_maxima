package negocio;

public class Individuo implements Comparable<Individuo>
{
	// Representamos un individuo por una secuencia de bits
	private boolean[] _bits;

	// Instancia asociada
	private Instancia _instancia;
	
	// Generador de numeros aleatorios para los individuos
	private static Generador _random;

	// Setter para el generador
	public static void setGenerador(Generador generador)
	{
		_random = generador;
	}
	
	// Factory method para construir un individuo aleatoriamente
	public static Individuo aleatorio(Instancia instancia)
	{
		Individuo ret = new Individuo(instancia);
		
		for(int i=0; i<instancia.getTamano(); ++i)
			ret.set(i, _random.nextBoolean());
		
		return ret;
	}

	// Constructor para generar un individuo "vacio"
	private Individuo(Instancia instancia)
	{
		_instancia = instancia;
		_bits = new boolean[instancia.getTamano()];
	}

	// Mutacion
	public void mutar()
	{
		int i = _random.nextInt(_bits.length);
		_bits[i] = !_bits[i];
	}
	
	// Recombinacion
	public Individuo[] recombinar(Individuo that)
	{
		int k = _random.nextInt(_bits.length);
		
		Individuo hijo1 = new Individuo(_instancia);
		Individuo hijo2 = new Individuo(_instancia);
		
		for(int i=0; i<k; ++i)
		{
			hijo1.set(i, this.get(i));
			hijo2.set(i, that.get(i));
		}
		
		for(int i=k; i<_bits.length; ++i)
		{
			hijo1.set(i, that.get(i));
			hijo2.set(i, this.get(i));
		}
		
		return new Individuo[] { hijo1, hijo2 };
	}
	
	// Función de fitness
	public double fitness()
	{
		if (peso() > _instancia.getCapacidad() )
			return _instancia.getCapacidad() - peso();
		else
			return beneficio();
	}
	
	// Peso y beneficio total
	public double peso()
	{
		double ret = 0;
		for(int i=0; i<_bits.length; ++i) if( get(i) == true )
			ret += _instancia.getObjeto(i).getPeso();
		
		return ret;
	}
	public double beneficio()
	{
		double ret = 0;
		for(int i=0; i<_bits.length; ++i) if( get(i) == true )
			ret += _instancia.getObjeto(i).getBeneficio();
		
		return ret;
	}
	
	// Getters y setters (privados!)
	boolean get(int i)
	{
		return _bits[i];
	}
	private void set(int i, boolean valor)
	{
		_bits[i] = valor;
	}


	@Override
	public int compareTo(Individuo otro)
	{
		if( this.fitness() < otro.fitness() )
			return -1;
		else if( this.fitness() == otro.fitness() )
			return 0;
		else
			return 1;
	}
}
