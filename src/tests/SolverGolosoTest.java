package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Comparator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openstreetmap.gui.jmapviewer.Coordinate;

import negocio.Clique;
import negocio.Grafo;
import negocio.SolverGoloso;
import negocio.Vertice;

public class SolverGolosoTest {

    @Test
    public void resolverPorGradoTest() {

        SolverGoloso solver = new SolverGoloso(ejecutarEjemplo(), new Comparator<Vertice>() {

			@Override
			public int compare(Vertice uno, Vertice otro) 
			{
				return -uno.obtenerGrado() + otro.obtenerGrado();
			}
		});

        Clique clique = solver.resolver();

        ArrayList<Vertice> verticesResultado = clique.obtenerVertices();
        double pesoResultado = clique.obtenerPeso();
        int gradoResultado = clique.obtenerGrado();

        ArrayList<Vertice> verticesEsperados = new ArrayList<>();
        verticesEsperados.add(new Vertice(1, "2", new Coordinate(20.20, 50.50), 5.5));
        verticesEsperados.add(new Vertice(3, "4", new Coordinate(40.20, 70.50), 7.0));
        verticesEsperados.add(new Vertice(4, "5", new Coordinate(50.20, 80.50), 2.5));
        verticesEsperados.add(new Vertice(5,"6", new Coordinate(60.20, 90.50), 3.5));
        
        assertTrue(verticesEsperados.equals(verticesResultado));
        assertEquals(18.5, pesoResultado);
        assertEquals(4, gradoResultado);
    }

    @Test
    public void resolverPorPesoTest() {

    }

    private Grafo ejecutarEjemplo() {

        Grafo grafo = new Grafo();

        grafo.agregarVertice("1", new Coordinate(10.20, 40.50), 11.0);
        grafo.agregarVertice("2", new Coordinate(20.20, 50.50), 5.5);
        grafo.agregarVertice("3", new Coordinate(30.20, 60.50), 1.1);
        grafo.agregarVertice("4", new Coordinate(40.20, 70.50), 7.0);
        grafo.agregarVertice("5", new Coordinate(50.20, 80.50), 2.5);
        grafo.agregarVertice("6", new Coordinate(60.20, 90.50), 3.5);

        grafo.agregarArista("1", "2");
        grafo.agregarArista("1", "4");
        grafo.agregarArista("4", "2");
        grafo.agregarArista("3", "2");
        grafo.agregarArista("3", "5");
        grafo.agregarArista("6", "5");
        grafo.agregarArista("6", "4");
        grafo.agregarArista("6", "2");
        grafo.agregarArista("5", "4");
        grafo.agregarArista("5", "2");

        return grafo;
    }


}