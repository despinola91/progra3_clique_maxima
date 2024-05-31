package interfaz;

import negocio.Individuo;
import negocio.Observador;
import negocio.Poblacion;

public class ObservadorPorConsola implements Observador
{
	private Poblacion _poblacion;
	
	public ObservadorPorConsola(Poblacion poblacion)
	{
		_poblacion = poblacion;
	}
	
	@Override
	public void notificar()
	{
		Individuo mejor = _poblacion.mejorIndividuo();
		System.out.print( "It:" + _poblacion.iteracion() + 
				          " Mejor: " + mejor.fitness());
			
		System.out.print(" - Prom: " + _poblacion.fitnessPromedio());
			
		Individuo peor = _poblacion.peorIndividuo();
		System.out.println( " - Peor: " + peor.fitness());
	}
}
