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
	void agregarNodoTest() {

		Grafo mapa = new Grafo();
		mapa.agregarNodo("Buenos Aires", new Coordinate(-43, 64));
		mapa.agregarNodo("Santa Fe", new Coordinate(87, -93));
		mapa.agregarNodo("Corrientes", new Coordinate(12, -65));

		assertEquals(3, mapa.obtenerDimensionMatrizArista());
		assertTrue((mapa.existeNodo("Buenos Aires")));
		assertTrue((mapa.existeNodo("Santa Fe")));
		assertTrue((mapa.existeNodo("Corrientes")));
		assertFalse((mapa.existeNodo("Chaco")));

		Assertions.assertThrows(IllegalArgumentException.class, () -> mapa.agregarNodo("Buenos Aires", new Coordinate(50., 50)));
	}

	@Test
	void agregarAristaTest() {

		Grafo mapa = new Grafo();
		mapa.agregarNodo("Buenos Aires", new Coordinate(-43, 64));
		mapa.agregarNodo("Santa Fe", new Coordinate(87, -93));

		mapa.agregarArista("Buenos Aires", "Santa Fe", 5);
		assertTrue(mapa.existeArista("Buenos Aires", "Santa Fe"));
		assertTrue(mapa.existeArista("Santa Fe", "Buenos Aires"));

		Assertions.assertThrows(IllegalArgumentException.class, () -> mapa.agregarArista("Buenos Aires", "Buenos Aires", 5));
		Assertions.assertThrows(IllegalArgumentException.class, () -> mapa.agregarArista("Buenos Aires", "Chaco", 5));
	}

	@Test
	void eliminarProvinciaTest() {

		Grafo mapa = new Grafo();
		mapa.agregarNodo("Buenos Aires", new Coordinate(87, -34));
		mapa.agregarNodo("Santa Fe", new Coordinate(-12, 65));

		mapa.eliminarNodo("Santa Fe");
		assertFalse(mapa.existeNodo("Santa Fe"));
		Assertions.assertThrows(IllegalArgumentException.class, () -> mapa.eliminarNodo("Chaco"));

	}

	@Test
	void eliminarRelacionTest() {
		Grafo mapa = new Grafo();
		mapa.agregarNodo("Buenos Aires", new Coordinate(98, 89));
		mapa.agregarNodo("Santa Fe", new Coordinate(-93, 72));

		mapa.agregarArista("Santa Fe", "Buenos Aires", 1);
		assertTrue(mapa.existeArista("Santa Fe", "Buenos Aires"));
		mapa.eliminarArista("Buenos Aires", "Santa Fe");
		assertFalse(mapa.existeArista("Santa Fe", "Buenos Aires"));
	}

	@Test
	void obtenerProvinciasTest() {
		Grafo grafo = new Grafo();

		grafo.agregarNodo("Salta", new Coordinate(72, 12));
		grafo.agregarNodo("Tucuman", new Coordinate(-12, 23));
		grafo.agregarNodo("Catamarca", new Coordinate(-12, 12));
		grafo.agregarNodo("Buenos Aires", new Coordinate(-34, 34));

		ArrayList<String> nodosResultado = grafo.obtenerNodos();

		ArrayList<String> provinciasEsperadas = new ArrayList<>();
        provinciasEsperadas.add("Buenos Aires");
		provinciasEsperadas.add("Catamarca");
		provinciasEsperadas.add("Salta");
		provinciasEsperadas.add("Tucuman");


 		assertTrue(provinciasEsperadas.equals(nodosResultado));
	}


	@Test
	void obtenerProvinciaporNombreTest() {
	
		Grafo grafo = new Grafo();
		grafo.agregarNodo("Buenos Aires", new Coordinate(50, 60));
		Vertice nodo = grafo.obtenerNodoPorNombre("Buenos Aires");
		
		assertTrue(nodo.obtenerId() ==0);
		assertTrue(nodo.obtenerNombre() == "Buenos Aires");
		assertTrue(nodo.obtenerCoordenadas().getLat() == 50.00);
		assertTrue(nodo.obtenerCoordenadas().getLon() == 60.00);
	}

	@Test
	void obtenerProvinciaporIdTest() {
	
		Grafo grafo = new Grafo();
		grafo.agregarNodo("Buenos Aires", new Coordinate(50, 60));
		Vertice nodo = grafo.obtenerNodoPorId(0);

		assertTrue(nodo.obtenerId() ==0);
		assertTrue(nodo.obtenerNombre() == "Buenos Aires");
		assertTrue(nodo.obtenerCoordenadas().getLat() == 50);
		assertTrue(nodo.obtenerCoordenadas().getLon() == 60);
		Assertions.assertThrows(IllegalArgumentException.class, () -> grafo.obtenerNodoPorId(1));
	}	

	@Test
	void obtenerMatrizRelacionTest() {

		Grafo mapa = new Grafo();
		mapa.agregarNodo("Buenos Aires", new Coordinate(-43, 64));
		mapa.agregarNodo("Santa Fe", new Coordinate(87, -93));
		mapa.agregarNodo("Chaco", new Coordinate(37, -73));

		mapa.agregarArista("Buenos Aires", "Santa Fe", 5);
		int[][] expectedMatrix = {{0, 5, 0}, 
								  {5, 0, 0},
								  {0, 0, 0}};
		assertArrayEquals(expectedMatrix, mapa.obtenerMatrizArista());
	}

	@Test
	void dividirRegionesTest() {
		Grafo mapa = new Grafo();
		
		int[][] matrizDeRegiones = {
			{0, 2, 0, 6, 0},
			{2, 0, 3, 0, 5},
			{0, 3, 0, 0, 0},
			{6, 0, 0, 0, 0},
			{0, 5, 0, 0, 0}
		};

		int[][] matrizEsperada = {
			{0, 2, 0, 0, 0},
			{2, 0, 3, 0, 5},
			{0, 3, 0, 0, 0},
			{0, 0, 0, 0, 0},
			{0, 5, 0, 0, 0}
		};

		assertArrayEquals(matrizEsperada, mapa.dividirRegiones(matrizDeRegiones, 2));
	}
}
