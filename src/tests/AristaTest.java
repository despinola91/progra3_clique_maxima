package tests;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.openstreetmap.gui.jmapviewer.Coordinate;

import negocio.Nodo;
import negocio.Arista;

public class AristaTest {
    
    @Test
    public void compareToTest() {
        
        Nodo NodoA = new Nodo(0, "Buenos Aires", new Coordinate(10.00, 20.00));
        Nodo NodoB = new Nodo(1, "La Pampa", new Coordinate(20.00, 10.00));
        Arista Arista1 = new Arista(NodoA, NodoB, 20);

        Nodo NodoC = new Nodo(2, "Corrientes", new Coordinate(10.00, 20.00));
        Nodo NodoD = new Nodo(3, "Santa Fe", new Coordinate(30.00, 40.00));
        Arista Arista2 = new Arista(NodoC, NodoD, 10);

        Nodo NodoE = new Nodo(2, "Chaco", new Coordinate(-50.00, 70.00));
        Nodo NodoF = new Nodo(3, "Formosa", new Coordinate(-30.00, 80.00));
        Arista Arista3 = new Arista(NodoE, NodoF, 30);

        ArrayList<Arista> Aristaes = new ArrayList<>();
        Aristaes.add(Arista1);
        Aristaes.add(Arista2);
        Aristaes.add(Arista3);
        Collections.sort(Aristaes); //Ordenamos de mayor a menor similitud

        assertTrue(Aristaes.get(0) == Arista3);
        assertTrue(Aristaes.get(1) == Arista1);
        assertTrue(Aristaes.get(2) == Arista2);
    }
}
