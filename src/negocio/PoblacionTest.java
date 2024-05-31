package negocio;

import java.util.Random;

public class PoblacionTest
{
	public static void main(String[] args)
	{
		Instancia instancia = aleatoria(400);
		Generador generador = new GeneradorRandom();
		
		Poblacion poblacion = new Poblacion(instancia, generador);
		poblacion.registrar(new ObservadorPorConsola(poblacion));
		poblacion.registrar(new ObservadorVisual(poblacion));

		poblacion.simular();
	}

	private static Instancia aleatoria(int n)
	{
		Random random = new Random(0);
		Instancia instancia = new Instancia(200);
		
		for(int i=0; i<n; ++i)
		{
			int peso = random.nextInt(10) + 1;
			int beneficio = random.nextInt(10) + 1;
			
			instancia.agregar(new Objeto("", peso, beneficio));
		}
		
		return instancia;
	}
}
