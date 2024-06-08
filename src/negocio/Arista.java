package negocio;

import java.util.ArrayList;

public class Arista {
    
    ArrayList<Vertice> _vertices = new ArrayList<>();

    public Arista(Vertice verticeA, Vertice verticeB) {
        _vertices.add(verticeA);
        _vertices.add(verticeB);
    }

    public ArrayList<Vertice> obtenerVertices() {
        return _vertices;
    }

    public static Arista obtenerArista(ArrayList<Arista> aristas, Vertice verticeA, Vertice verticeB) {
        ArrayList<Vertice> vertices = new ArrayList<>();
        vertices.add(verticeA);
        vertices.add(verticeB);

        for (Arista arista : aristas) {

            if (arista.obtenerVertices().containsAll(vertices)) {
                return arista;
            }
        }
        return null;
    }
}
