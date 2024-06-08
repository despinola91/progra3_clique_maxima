package tests;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import negocio.Grafo;
import negocio.Vertice;
import negocio.Clique;

public class CliqueTest {
	@Test
	public void testPerteneceClique() {
		Grafo grafo = new Grafo();
		Clique clique = new Clique();
		
		grafo.agregarVertice("A", null, 1);
		grafo.agregarVertice("B", null, 2);
		grafo.agregarVertice("C", null, 3);
		
		// Aristas (clique)
		grafo.agregarArista("A", "B");
		grafo.agregarArista("A", "C");
		
		// vertices para clique
		Vertice verticeA = grafo.obtenerVerticePorNombre("A");
		Vertice verticeB = grafo.obtenerVerticePorNombre("B");
		Vertice verticeC = grafo.obtenerVerticePorNombre("C");
		
		clique.agregarVertice(verticeA);
		clique.agregarVertice(verticeB);
		//clique.agregarVertice(verticeC); // No agrego verticeC a clique para que no la tome como vecino vertice C no es vecino con vertice C
		
		assertTrue(verticeC.perteneceAClique(clique, grafo));
	}
	
}
