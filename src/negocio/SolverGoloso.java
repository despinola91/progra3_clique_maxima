package negocio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SolverGoloso {

    private Grafo _grafo;
	private Comparator<Vertice> _comparador;
	
	public SolverGoloso(Grafo grafo, Comparator<Vertice> comparador)
	{
		_grafo = grafo;
		_comparador = comparador;
	}
	
	public Clique resolver()
	{
		Clique clique = new Clique();
		for(Vertice vertice: verticesOrdenados())
		{
			if (clique.obtenerGrado() == 0) {
				clique.agregarVertice(vertice);
			} 
			else {
				if(_grafo.verticeVecinoDeTodos(clique.obtenerVertices(), vertice))
					clique.agregarVertice(vertice);
			}
		}
		
		return clique;
	}

	private ArrayList<Vertice> verticesOrdenados()
	{
		ArrayList<Vertice> vertices = _grafo.obtenerVertices();
		Collections.sort(vertices, _comparador);
		
		return vertices;
	}
}
