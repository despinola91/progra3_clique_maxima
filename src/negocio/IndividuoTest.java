package negocio;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class IndividuoTest
{
	private Instancia _instancia;
	
	@Before
	public void inicializar()
	{
		_instancia = new Instancia(10);
		_instancia.agregar(new Objeto("Mapa", 2, 8));
		_instancia.agregar(new Objeto("Calentador", 3, 9));
		_instancia.agregar(new Objeto("Carpa", 4, 10));
		_instancia.agregar(new Objeto("Botas", 3, 6));
		_instancia.agregar(new Objeto("Hacha", 4, 8));
		_instancia.agregar(new Objeto("Lena", 5, 7));
	}
	
	@Test
	public void beneficioTest()
	{
		Individuo individuo = crear("011010");
		assertEquals(27, individuo.beneficio(), 1e-3);
	}
	
	@Test
	public void beneficioVacioTest()
	{
		Individuo individuo = crear("000000");
		assertEquals(0, individuo.beneficio(), 1e-3);
	}
	
	@Test
	public void beneficioCompletoTest()
	{
		Individuo individuo = crear("111111");
		assertEquals(48, individuo.beneficio(), 1e-3);
	}
	
	@Test
	public void pesoTest()
	{
		Individuo individuo = crear("100001");
		assertEquals(7, individuo.peso(), 1e-3);
	}
	
	@Test
	public void pesoVacioTest()
	{
		Individuo individuo = crear("000000");
		assertEquals(0, individuo.peso(), 1e-3);
	}
	
	@Test
	public void pesoCompletoTest()
	{
		Individuo individuo = crear("111111");
		assertEquals(21, individuo.peso(), 1e-3);
	}
	
	@Test
	public void fitnessExcedidoTest()
	{
		Individuo individuo = crear("111111");
		assertEquals(-11, individuo.fitness(), 1e-3);
	}
	
	@Test
	public void fitnessFactibleTest()
	{
		Individuo individuo = crear("011100");
		assertEquals(25, individuo.fitness(), 1e-3);
	}

	@Test
	public void mutarTest()
	{
		Individuo individuo = crear("110001");
		mutar(individuo, 2);
		assertIndividuo("111001", individuo);
	}

	@Test
	public void mutarPrimeroTest()
	{
		Individuo individuo = crear("110001");
		mutar(individuo, 0);
		assertIndividuo("010001", individuo);
	}

	@Test
	public void mutarUltimoTest()
	{
		Individuo individuo = crear("110001");
		mutar(individuo, 5);
		assertIndividuo("110000", individuo);
	}

	@Test
	public void mutarCerosTest()
	{
		Individuo individuo = crear("000000");
		mutar(individuo, 1);
		assertIndividuo("010000", individuo);
	}

	@Test
	public void recombinacionTest()
	{
		Individuo padre1 = crear("111000");
		Individuo padre2 = crear("000111");
		Individuo[] hijos = recombinar(padre1, padre2, 3);
		
		assertIndividuo("111111", hijos[0]);
		assertIndividuo("000000", hijos[1]);
	}

	@Test
	public void recombinacionAlInicioTest()
	{
		Individuo padre1 = crear("111000");
		Individuo padre2 = crear("000111");
		Individuo[] hijos = recombinar(padre1, padre2, 0);
		
		assertIndividuo("000111", hijos[0]);
		assertIndividuo("111000", hijos[1]);
	}

	@Test
	public void recombinacionAlFinalTest()
	{
		Individuo padre1 = crear("111000");
		Individuo padre2 = crear("000111");
		Individuo[] hijos = recombinar(padre1, padre2, 6);
		
		assertIndividuo("111000", hijos[0]);
		assertIndividuo("000111", hijos[1]);
	}

	private void assertIndividuo(String str, Individuo individuo)
	{
		boolean[] bits = toBooleanArray(str);
		
		for(int i=0; i<bits.length; ++i)
			assertEquals(bits[i], individuo.get(i));
	}

	private Individuo crear(String str)
	{
		boolean[] bits = toBooleanArray(str);
		
		GeneradorPrefijado generador = new GeneradorPrefijado(bits);
		Individuo.setGenerador(generador);
		
		return Individuo.aleatorio(_instancia);
	}
	
	private void mutar(Individuo individuo, int posicion)
	{
		GeneradorPrefijado generador = new GeneradorPrefijado(posicion);
		Individuo.setGenerador(generador);
		
		individuo.mutar();
	}

	private Individuo[] recombinar(Individuo padre1, Individuo padre2, int posicion)
	{
		GeneradorPrefijado generador = new GeneradorPrefijado(posicion);
		Individuo.setGenerador(generador);
		
		return padre1.recombinar(padre2);
	}

	private boolean[] toBooleanArray(String str)
	{
		boolean[] bits = new boolean[str.length()];
		
		for(int i=0; i<str.length(); ++i)
			bits[i] = str.charAt(i) == '1';
		
		return bits;
	}
}
