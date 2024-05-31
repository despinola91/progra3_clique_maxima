package negocio;

import static org.junit.Assert.*;

import java.util.Comparator;

import org.junit.Test;

public class SolverGolosoTest
{
	@Test
	public void resolverPorBeneficioTest()
	{
		SolverGoloso solver = new SolverGoloso(ejemplo(), new Comparator<Objeto>() {

			@Override
			public int compare(Objeto uno, Objeto otro) 
			{
				return -uno.getBeneficio() + otro.getBeneficio();
			}
		});

		Solucion solucion = solver.resolver();

		assertEquals(3, solucion.cardinal());
		assertEquals(9, solucion.peso());
		assertEquals(27, solucion.beneficio());		
	}

	@Test
	public void resolverPorPesoTest()
	{
		SolverGoloso solver = new SolverGoloso(ejemplo(), new Comparator<Objeto>() {

			@Override
			public int compare(Objeto uno, Objeto otro) {
				return uno.getPeso() - otro.getPeso();
			}
		});
		
		Solucion solucion = solver.resolver();

		assertEquals(3, solucion.cardinal());
		assertEquals(8, solucion.peso());
		assertEquals(23, solucion.beneficio());		
	}

	@Test
	public void resolverPorCocienteTest()
	{
		SolverGoloso solver = new SolverGoloso(ejemplo(), new Comparator<Objeto>() {

			@Override
			public int compare(Objeto uno, Objeto otro) 
			{
				double cocienteUno = uno.getBeneficio() / (double)uno.getPeso();
				double cocienteOtro = otro.getBeneficio() / (double)otro.getPeso();
				return ((cocienteUno < cocienteOtro) ? 1 : ((cocienteUno == cocienteOtro ) ? 0:-1));
			}
		});		

		Solucion solucion = solver.resolver();

		assertEquals(3, solucion.cardinal());
		assertEquals(9, solucion.peso());
		assertEquals(27, solucion.beneficio());		
	}

	private Instancia ejemplo()
	{
		Instancia ret = new Instancia(10);
		ret.agregar(new Objeto("Mapa", 2, 8));
		ret.agregar(new Objeto("Calentador", 3, 9));
		ret.agregar(new Objeto("Carpa", 4, 10));
		ret.agregar(new Objeto("Botas", 3, 6));
		ret.agregar(new Objeto("Hacha", 4, 8));
		ret.agregar(new Objeto("Lena", 5, 7));

		return ret;
	}
}
