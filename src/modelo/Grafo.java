package modelo;
import java.util.HashMap;

public class Grafo {
	private int[][] matrizDeRelaciones;
	private HashMap<String, Vertice> vertices;
	
	public Grafo(int[][] matrizDeRelaciones, HashMap<String, Vertice> vertices) {
		this.matrizDeRelaciones = matrizDeRelaciones;
		this.vertices = vertices;
	}
	
	public HashMap<String, Vertice> getVertices() {
		return vertices;
	}
	public void setVertices(HashMap<String, Vertice> vertices) {
		this.vertices = vertices;
	}
	public int[][] getMatrizDeRelaciones() {
		return matrizDeRelaciones;
	}
	public void setMatrizDeRelaciones(int[][] matrizDeRelaciones) {
		this.matrizDeRelaciones = matrizDeRelaciones;
	} 
	
	public int[][] encontrarCliqueMaxima() { // Añadir tipo de comparadores como un strategy
 		return null;
	}
}
