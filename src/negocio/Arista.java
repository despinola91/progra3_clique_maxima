package negocio;

import java.util.ArrayList;

public class Arista {
    
    ArrayList<Vertice> _vertices = new ArrayList<>();

    public Arista(Vertice verticeA, Vertice verticeB) {
        this._vertices.add(verticeA);
        this._vertices.add(verticeB);
    }

    public ArrayList<Vertice> obtenerVertices() {
        return this._vertices;
    }

    public static Object obtenerArista(ArrayList<Arista> aristas, Vertice verticeA, Vertice verticeB) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'obtenerArista'");
    }
}