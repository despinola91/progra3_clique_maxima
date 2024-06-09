package negocio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SolverGoloso {

    private Grafo _grafo;
	private Comparator<Vertice> _comparador;
	private GeneradorRandom _random;
	
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

	public Clique resolverConElementoRandom()
	{
		Clique clique = new Clique();
		for(Vertice vertice: verticesOrdenadosConElementoRandom())
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

	private ArrayList<Vertice> verticesOrdenadosConElementoRandom()
	{
		ArrayList<Vertice> vertices = _grafo.obtenerVertices();
		Collections.sort(vertices, _comparador);

		_random = new GeneradorRandom();
		for (int i = 0; i < vertices.size() - 1; i++) { // -1 para evitar el último elemento sin siguiente
            if (_random.nextBoolean()) {
                Collections.swap(vertices, i, i + 1);
                i++; // Saltar el siguiente elemento ya que ya ha sido intercambiado
            }
        }
		
		return vertices;
	}
}
