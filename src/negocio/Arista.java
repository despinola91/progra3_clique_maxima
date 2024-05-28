package negocio;

import java.util.ArrayList;

public class Arista implements Comparable<Arista> {
    
    ArrayList<Nodo> nodos = new ArrayList<>();
    int peso;


    @Override
    public int compareTo(Arista otra) {
        return otra.obtenerPeso() - this.peso; // Orden descendente por similitud
    }

    public Arista(Nodo nodoA, Nodo nodoB, int peso) {
        this.nodos.add(nodoA);
        this.nodos.add(nodoB);
        this.peso = peso;
    }

    public ArrayList<Nodo> obtenerNodos() {
        return this.nodos;
    }

    public int obtenerPeso() {
        return peso;
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
