package negocio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.openstreetmap.gui.jmapviewer.Coordinate;

public class Grafo {

    private HashMap<String, Nodo> nodos = new HashMap<>();
    private ArrayList<Arista> aristas = new ArrayList<>();
    private int[][] matrizDeArista;
    private int[][] matrizDeRegiones;
    
    public Grafo()
	{
        matrizDeArista = new int[0][0];
	}

    /**
     * Agrega una relación al mapa entre dos Nodos.
     * @param nombreNodo1
     * @param nombreNodo2
     * @param peso
     */
    public void agregarArista(String nombreNodo1, String nombreNodo2, int peso)
	{
		validarArista(nombreNodo1, nombreNodo2);

        int idProv1 = nodos.get(nombreNodo1).obtenerId();
        int idProv2 = nodos.get(nombreNodo2).obtenerId();

        matrizDeArista[idProv1][idProv2] = peso;
        matrizDeArista[idProv2][idProv1] = peso;

        Arista arista = new Arista(nodos.get(nombreNodo1), nodos.get(nombreNodo2), peso);
        aristas.add(arista);
	}

    /**
     * Elimina una relación entre dos Nodos.
     * @param nombreNodo1
     * @param nombreNodo2
     */
    public void eliminarArista(String nombreNodo1, String nombreNodo2)
	{
        Nodo NodoA = nodos.get(nombreNodo1);
        Nodo NodoB = nodos.get(nombreNodo2);

        int idNodo1 = NodoA.obtenerId();
        int idNodo2 = NodoB.obtenerId();

		matrizDeArista[idNodo1][idNodo2] = 0;
        matrizDeArista[idNodo2][idNodo1] = 0;
        
        aristas.remove(Arista.obtenerArista(aristas, NodoA, NodoB));
	}

    /**
     * Valida si una relación entre dos Nodos es correcta.
     * @param nombreNodo1
     * @param nombreNodo2
     */
    private void validarArista (String nombreNodo1, String nombreNodo2) {
        if (!existeNodo(nombreNodo1) || !existeNodo(nombreNodo2) || nombreNodo1 == nombreNodo2) {
            throw new IllegalArgumentException("La relación es inválida");
        }

        if (existeArista(nombreNodo1, nombreNodo2)) {
            throw new IllegalArgumentException("La relación ya existe");
        }
    }

    /**
     * Informa si ya existe una relación entre dos Nodos.
     * @param nombreNodo1
     * @param nombreNodo2
     * @return booleano indicando si existe la relación.
     */
    public boolean existeArista(String nombreNodo1, String nombreNodo2)
	{
        int idProv1 = nodos.get(nombreNodo1).obtenerId();
        int idProv2 = nodos.get(nombreNodo2).obtenerId();

		return matrizDeArista[idProv1][idProv2] > 0;
	}

    /**
     * Informa si ya existe una relación entre dos Nodos dentro de las regiones.
     * @param nombreNodo1
     * @param nombreNodo2
     * @return booleano indicando si existe la relación.
     */
    public boolean existeAristaRegiones(String nombreNodo1, String nombreNodo2)
	{
        int idNodo1 = nodos.get(nombreNodo1).obtenerId();
        int idNodo2 = nodos.get(nombreNodo2).obtenerId();

		return matrizDeRegiones[idNodo1][idNodo2] > 0;
	}

    /**
     * Agrega una Nodo al mapa.
     * @param nombreNodo
     * @param coordenadas
     */
    public void agregarNodo (String nombreNodo, Coordinate coordenadas) {
        
        if (existeNodo(nombreNodo)) {
            throw new IllegalArgumentException("La Nodo ya existe");
        }

        Nodo Nodo = new Nodo(nodos.size(), nombreNodo, coordenadas);
        nodos.put(nombreNodo, Nodo);

        int tamanioActual = matrizDeArista.length;
        int nuevoTamanio = matrizDeArista.length + 1;
        int[][] nuevaMatrizArista = new int[nuevoTamanio][nuevoTamanio];

        for (int i = 0; i < tamanioActual; i++) {
            for (int j = 0; j < tamanioActual; j++) {
                nuevaMatrizArista[i][j] = matrizDeArista[i][j];
            }
        }

        matrizDeArista = nuevaMatrizArista;
    }

    /**
     * Elimina Nodo del mapa.
     * @param nombreNodo
     */
    public void eliminarNodo (String nombreNodo) {
        if (!existeNodo(nombreNodo)) {
            throw new IllegalArgumentException("La Nodo no existe");
        }
        
        nodos.remove(nombreNodo);
        int nuevoTamanio = matrizDeArista.length - 1;
        int[][] nuevaMatrizArista = new int[nuevoTamanio][nuevoTamanio];

        for (int i = 0; i < nuevoTamanio; i++) {
            for (int j = 0; j < nuevoTamanio; j++) {
                nuevaMatrizArista[i][j] = matrizDeArista[i][j];
            }
        }

        matrizDeArista = nuevaMatrizArista;
    }

    /**
     * Informa si ya existe la Nodo dentro del mapa.
     * @param nombreNodo
     * @return booleano indicando si la Nodo ya existe.
     */
    public boolean existeNodo (String nombreNodo) {
        return nodos.containsKey(nombreNodo);
    }

    /**
     * Obtiene la dimensión de la matriz relación actual.
     * @return dimensión de la matriz.
     */
    public int obtenerDimensionMatrizArista() {
        return matrizDeArista.length;
    }

    public Nodo obtenerNodo(String nombreNodo) {
        Nodo nodo = nodos.get(nombreNodo);
        return nodo;
    }

    /**
     * Obtiene lista Nodos del mapa.
     * @return  lista de Nodos agregadas al mapa.
     */
    public ArrayList<String> obtenerNodos() {
        ArrayList<String> listaNodos = new ArrayList<>();
        listaNodos.addAll(nodos.keySet());
        Collections.sort(listaNodos);
        
        return listaNodos;
    }

    /**
     * Obtiene el objeto Nodo a partir de un nombre de Nodo.
     * @param nombreNodo
     * @return objeto Nodo.
     */
    public Nodo obtenerNodoPorNombre(String nombreNodo) {
        return nodos.get(nombreNodo);
    }

    /**
     * Obtiene Nodo por id.
     * @param id
     * @return  objeto Nodo.
     */
    public Nodo obtenerNodoPorId(int id) {
        
        for (Nodo Nodo : nodos.values()) {
            if (Nodo.obtenerId() == id) {
                return Nodo;
            }
        }

        throw new IllegalArgumentException("Nodo not found for id: " + id);
    }

    
    public int[][] dividirRegiones(int[][] matrizDeRegiones, int cantidadRegiones) {
        int[][] matrizResultado = new int[matrizDeRegiones.length][matrizDeRegiones.length];

        // Copiar la matriz original a la matriz resultado
        for (int i = 0; i < matrizDeRegiones.length; i++) {
            for (int j = 0; j < matrizDeRegiones[0].length; j++) {
                matrizResultado[i][j] = matrizDeRegiones[i][j];
            }
        }

        // Poner en 0 los valores más altos de la matriz resultado
        for (int k = 0; k < cantidadRegiones-1; k++) {
            int maximo = Integer.MIN_VALUE;
            int filaMaximo = -1;
            int columnaMaximo = -1;

            // Encontrar el valor más alto en la matriz resultado
            for (int i = 0; i < matrizResultado.length; i++) {
                for (int j = 0; j < matrizResultado[0].length; j++) {
                    if (matrizResultado[i][j] > maximo) {
                        maximo = matrizResultado[i][j];
                        filaMaximo = i;
                        columnaMaximo = j;
                    }
                }
            }

            // Poner en 0 el valor más alto encontrado
            matrizResultado[filaMaximo][columnaMaximo] = 0;
            matrizResultado[columnaMaximo][filaMaximo] = 0;
        }

        return matrizResultado;
    }



    /**
     * Obtiene matriz de relación.
     * @return matriz de relación.
     */
    public int[][] obtenerMatrizArista() {
        return matrizDeArista;
    }

    /**
     * Indica si el mapa es conexo utilizando BFS.
     * @param matriz
     * @return booleano indicando si el mapa es conexo o no.
     */
    public boolean esGrafoConexo(int[][] matriz) {
        
        return BFS.grafoEsConexo(matriz);
    }
    
    /**
     * Obtiene lista de Aristaes creadas hasta el momento por el usuario.
     * @return lista de objetos de tipo Relación.
     */
    public ArrayList<Arista> obtenerAristas() {
        return aristas;
    }
    
    /**
     * Obtiene una lista de Aristaes resultantes luego de crear las regiones
     * @return lista de Aristaes
     */
    public ArrayList<Arista> obtenerAristasRegiones() {
        
        ArrayList<Arista> Aristas = new ArrayList<>();

        for (int i = 0; i < matrizDeRegiones.length; i++) {
            for (int j = i+1; j < matrizDeRegiones.length; j++) {
                if (matrizDeRegiones[i][j] > 0){
                    
                    Nodo NodoA = obtenerNodoPorId(i);
                    Nodo NodoB = obtenerNodoPorId(j);

                    Aristas.add(new Arista(NodoA, NodoB, matrizDeRegiones[i][j]));
                }
            }
        }
        return Aristas;
    }

    /**
     * Reinicia el mapa.
     */
    public void reiniciarGrafo() {

        matrizDeArista = new int[0][0];
        matrizDeRegiones = new int[0][0];
        nodos.clear();
        aristas.clear();
    }

	public ArrayList<Nodo> obtenerindiceConVecinos() {
		// TODO Auto-generated method stub
		return null;
	}



}
