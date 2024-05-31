package negocio;

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
		System.out.print("It: " + _poblacion.getIteracion());
		System.out.print(" - Mejor: " + _poblacion.mejorIndividuo().fitness());
		System.out.print(" - Prom: " + _poblacion.fitnessPromedio());
		System.out.print(" - Peor: " + _poblacion.peorIndividuo().fitness());
		System.out.println();
	}
}
