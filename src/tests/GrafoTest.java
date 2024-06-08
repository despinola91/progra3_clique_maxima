package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openstreetmap.gui.jmapviewer.Coordinate;

import negocio.Grafo;
import negocio.Vertice;


class GrafoTest {

	@Test
	void agregarVerticeTest() {

		Grafo grafo = new Grafo();
		grafo.agregarVertice("1", new Coordinate(10.00, 20.00), 5.50);
		grafo.agregarVertice("2", new Coordinate(13.00, 70.00), 10.00);
		grafo.agregarVertice("3", new Coordinate(15.00, 60.00), 2.50);

		assertEquals(3, grafo.obtenerVertices().size());
		assertTrue((grafo.existeVertice("1")));
		assertTrue((grafo.existeVertice("2")));
		assertTrue((grafo.existeVertice("3")));
		assertFalse((grafo.existeVertice("4")));

		Assertions.assertThrows(IllegalArgumentException.class, () -> grafo.agregarVertice("1", new Coordinate(50., 50), 4.00));
	}

	@Test
	void agregarAristaTest() {

		Grafo grafo = new Grafo();
		grafo.agregarVertice("1", new Coordinate(10.00, 20.00), 5.50);
		grafo.agregarVertice("2", new Coordinate(13.00, 70.00), 10.00);

		grafo.agregarArista("1", "2");
		assertTrue(grafo.existeArista("1", "2"));
		assertTrue(grafo.existeArista("2", "1"));

		Assertions.assertThrows(IllegalArgumentException.class, () -> grafo.agregarArista("1", "1"));
		Assertions.assertThrows(IllegalArgumentException.class, () -> grafo.agregarArista("1", "3"));
	}

	@Test
	void eliminarVerticeTest() {

		Grafo grafo = new Grafo();
		grafo.agregarVertice("1", new Coordinate(10.00, 20.00), 5.50);
		grafo.agregarVertice("2", new Coordinate(13.00, 70.00), 10.00);

		grafo.eliminarVertice("1");
		assertFalse(grafo.existeVertice("1"));
		Assertions.assertThrows(IllegalArgumentException.class, () -> grafo.eliminarVertice("1"));

	}

	@Test
	void eliminarAristaTest() {
		Grafo grafo = new Grafo();
		grafo.agregarVertice("1", new Coordinate(10.00, 20.00), 5.50);
		grafo.agregarVertice("2", new Coordinate(13.00, 70.00), 10.00);

		grafo.agregarArista("1", "2");
		assertTrue(grafo.existeArista("1", "2"));
		grafo.eliminarArista("1", "2");
		assertFalse(grafo.existeArista("1", "2"));

		Assertions.assertThrows(IllegalArgumentException.class, () -> grafo.eliminarArista("1", "2"));
	}

	@Test
	void obtenerVerticesTest() {
		Grafo grafo = new Grafo();

		grafo.agregarVertice("1", new Coordinate(10.00, 20.00), 5.50);
		grafo.agregarVertice("2", new Coordinate(13.00, 70.00), 10.00);
		grafo.agregarVertice("3", new Coordinate(15.00, 60.00), 2.50);

		ArrayList<Vertice> verticesResultado = grafo.obtenerVertices();
		ArrayList<Vertice> verticesEsperados = new ArrayList<>();

		
        verticesEsperados.add(new Vertice(0, "1", new Coordinate(10.00, 20.00), 5.50));
		verticesEsperados.add(new Vertice(1, "2", new Coordinate(13.00, 70.00), 10.00));
		verticesEsperados.add(new Vertice(2, "3", new Coordinate(15.00, 60.00), 2.50));

 		assertTrue(verticesEsperados.equals(verticesResultado));
	}


	@Test
	void obtenerVerticePorNombreTest() {
	
		Grafo grafo = new Grafo();
		grafo.agregarVertice("1", new Coordinate(50, 60), 19.80);
		Vertice vertice = grafo.obtenerVerticePorNombre("1");
		
		assertTrue(vertice.obtenerId() == 0);
		assertTrue(vertice.obtenerNombre() == "1");
		assertTrue(vertice.obtenerCoordenadas().getLat() == 50.00);
		assertTrue(vertice.obtenerCoordenadas().getLon() == 60.00);
		assertTrue(vertice.obtenerPeso() == 19.80);
	}

	@Test
	void obtenerVerticePorIdTest() {
	
		Grafo grafo = new Grafo();
		grafo.agregarVertice("1", new Coordinate(50, 60), 90.00);
		Vertice vertice = grafo.obtenerVerticePorId(0);

		assertTrue(vertice.obtenerId() == 0);
		assertTrue(vertice.obtenerNombre() == "1");
		assertTrue(vertice.obtenerCoordenadas().getLat() == 50);
		assertTrue(vertice.obtenerCoordenadas().getLon() == 60);
		assertTrue(vertice.obtenerPeso() == 90.00);

		Assertions.assertThrows(IllegalArgumentException.class, () -> grafo.obtenerVerticePorId(1));
	}	

	@Test
	void obtenerMatrizRelacionTest() {

		Grafo grafo = new Grafo();
		grafo.agregarVertice("1", new Coordinate(10.00, 20.00), 5.50);
		grafo.agregarVertice("2", new Coordinate(13.00, 70.00), 10.00);
		grafo.agregarVertice("3", new Coordinate(15.00, 60.00), 2.50);

		grafo.agregarArista("1", "2");
		int[][] expectedMatrix = {{0, 1, 0}, 
								  {1, 0, 0},
								  {0, 0, 0}};
		assertArrayEquals(expectedMatrix, grafo.obtenerMatrizArista());
	}

}
