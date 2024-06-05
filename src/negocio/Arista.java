package negocio;

import java.util.ArrayList;

public class Arista implements Comparable<Arista> {
    
    ArrayList<Nodo> nodos = new ArrayList<>();
    int relacion;


    @Override
    public int compareTo(Arista otra) {
        return otra.obtenerRelacion() - this.relacion; // Orden descendente por similitud
    }

    public Arista(Nodo nodoA, Nodo nodoB, int relacion) {
        this.nodos.add(nodoA);
        this.nodos.add(nodoB);
        this.relacion = relacion;
    }

    public ArrayList<Nodo> obtenerNodos() {
        return this.nodos;
    }

    public int obtenerRelacion() {
        return relacion;
    }

    public static Arista obtenerArista(ArrayList<Arista> aristas, Nodo nodoA, Nodo nodoB) {
        ArrayList<Nodo> nodos = new ArrayList<>();
        nodos.add(nodoA);
        nodos.add(nodoB);

        for (Arista arista : aristas) {

            if (arista.obtenerNodos().containsAll(nodos)) {
                return arista;
            }
        }
        return null;
    }
}
