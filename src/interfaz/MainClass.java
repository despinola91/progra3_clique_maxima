package interfaz;

import java.util.Random;

import negocio.Instancia;
import negocio.Objeto;
import negocio.Observador;
import negocio.Poblacion;

public class MainClass
{
	public static void main(String[] args)
	{
		Instancia instancia = aleatoria(200);
		Poblacion poblacion = new Poblacion(instancia);
		Observador observador = new ObservadorPorConsola(poblacion);
		//Observador observadorVisual = new ObservadorVisual(poblacion);

		poblacion.registrarObservador(observador);
		//poblacion.registrarObservador(observadorVisual);
		poblacion.simular();
	}

	private static Instancia aleatoria(int n)
	{
		Random random = new Random(0);

		Instancia instancia = new Instancia();
		instancia.setPeso(100);
		
		for(int i=0; i<n; ++i)
		{
			int peso = random.nextInt(10) + 1;
			int benef = random.nextInt(10) + 1;

			instancia.addObjeto(new Objeto("Obj" + i, peso, benef));
		}
		return instancia;
	}
}
