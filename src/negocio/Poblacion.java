package mochila;

import java.util.ArrayList;
import java.util.Collections;

public class Poblacion
{
	// Los individuos de la poblacion
	private ArrayList<Individuo> _individuos;
	
	// Instancia asociada
	private Instancia _instancia;
	
	// Parametros de la poblacion
	private int _tamano = 1000;
	private int _mutadosPorIteracion = 200;
	private int _recombinadosPorIteracion = 200;
	private int _eliminadosPorIteracion = 410;
	private int _maxIteraciones = 1000;
	
	// Estadisticas
	private int _iteracion;
	
	// Generador de numeros aleatorios
	private Generador _random;
	
	// Observadores registrados
	private ArrayList<Observador> _observadores;
	
	// Constructor	
	public Poblacion(Instancia instancia, Generador generador)
	{
		_instancia = instancia;
		_random = generador;
		_observadores = new ArrayList<Observador>();
	}
	
	// Registra un observador
	public void registrar(Observador observador)
	{
		_observadores.add(observador);
	}
	
	// Ejecuta el proceso de simulacion
	public void simular()
	{
		_iteracion = 0;
		generarIndividuos();
		
		while( !satisfactoria() )
		{
			mutarAlgunos();
			recombinarAlgunos();
			eliminarPeores();
			agregarNuevos();
			notificarObservadores();
			
			_iteracion++;
		}
	}

	private void generarIndividuos()
	{
		_individuos = new ArrayList<Individuo>(_tamano);
		Individuo.setGenerador(new GeneradorRandom());
		
		for(int i=0; i<_tamano; ++i)
			_individuos.add( Individuo.aleatorio(_instancia) );
	}

	private boolean satisfactoria()
	{
		return _iteracion == _maxIteraciones;
	}

	private void mutarAlgunos()
	{
		for(int j=0; j<_mutadosPorIteracion; ++j)
			individuoAleatorio().mutar();
	}

	private void recombinarAlgunos()
	{
		for(int j=0; j<_recombinadosPorIteracion; ++j)
		{
			Individuo padre1 = individuoAleatorio();
			Individuo padre2 = individuoAleatorio();

			for(Individuo hijo: padre1.recombinar(padre2))
				_individuos.add(hijo);
		}
	}

	private void eliminarPeores()
	{
		Collections.sort(_individuos);
		Collections.reverse(_individuos); // Para que cada remove() sea O(1) 

		for(int j=0; j<_eliminadosPorIteracion; ++j)
			_individuos.remove(_individuos.size()-1);
	}

	private void agregarNuevos()
	{
		while( _individuos.size() < _tamano )
			_individuos.add( Individuo.aleatorio(_instancia) );
	}

	public Individuo mejorIndividuo()
	{
		return Collections.max(_individuos);
	}

	public Individuo peorIndividuo()
	{
		return Collections.min(_individuos);
	}
	
	public double fitnessPromedio()
	{
		double suma = 0;
		for(Individuo individuo: _individuos)
			suma += individuo.fitness();
		
		return suma / _individuos.size();
	}

	private Individuo individuoAleatorio()
	{
		int i = _random.nextInt(_individuos.size());
		return _individuos.get(i);
	}
	
	private void notificarObservadores()
	{
		for(Observador observador: _observadores)
			observador.notificar();
	}

	public int getIteracion()
	{
		return _iteracion;
	}
	
	// Retorna los individuos ordenados por fitness
		ArrayList<Individuo> individuosOrdenados()
		{
			Collections.sort(_individuos);
			return _individuos;
		}
}
