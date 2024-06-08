package negocio;

import java.util.ArrayList;
import java.util.HashMap;

import org.openstreetmap.gui.jmapviewer.Coordinate;

public class Grafo {

    private HashMap<String, Vertice> _mapeoVertices = new HashMap<>();
    private ArrayList<Vertice> _vertices = new ArrayList<>();
    private ArrayList<Arista> aristas = new ArrayList<>();
    private int[][] matrizDeArista;
    private int[][] matrizDeRegiones;
	private HashMap<Integer, ArrayList<Vertice>> indiceConVecinos;
    
    public Grafo()
	{
        matrizDeArista = new int[0][0];
	}

    /**
     * Agrega una arista al grafo entre dos vertices.
     * @param nombreVertice1
     * @param nombreVertice2
     * @param peso
     */
    public void agregarArista(String nombreVertice1, String nombreVertice2)
	{
		validarArista(nombreVertice1, nombreVertice2);

        int idVertice1 = _mapeoVertices.get(nombreVertice1).obtenerId();
        int idVertice2 = _mapeoVertices.get(nombreVertice2).obtenerId();

        matrizDeArista[idVertice1][idVertice2] = 1;
        matrizDeArista[idVertice2][idVertice1] = 1;

        Arista arista = new Arista(_mapeoVertices.get(nombreVertice1), _mapeoVertices.get(nombreVertice2));
        aristas.add(arista);
	}

    /**
     * Elimina una arista entre dos vertices.
     * @param nombreVertice1
     * @param nombreVertice2
     */
    public void eliminarArista(String nombreVertice1, String nombreVertice2)
	{
        Vertice VerticeA = _mapeoVertices.get(nombreVertice1);
        Vertice VerticeB = _mapeoVertices.get(nombreVertice2);

        int idVertice1 = VerticeA.obtenerId();
        int idVertice2 = VerticeB.obtenerId();

		matrizDeArista[idVertice1][idVertice2] = 0;
        matrizDeArista[idVertice2][idVertice1] = 0;
        
        aristas.remove(Arista.obtenerArista(aristas, VerticeA, VerticeB));
	}

    /**
     * Valida si una arista entre dos vertices es correcta.
     * @param nombreVertice1
     * @param nombreVertice2
     */
    private void validarArista (String nombreVertice1, String nombreVertice2) {
        if (!existeVertice(nombreVertice1) || !existeVertice(nombreVertice2) || nombreVertice1 == nombreVertice2) {
            throw new IllegalArgumentException("La arista es inválida");
        }

        if (existeArista(nombreVertice1, nombreVertice2)) {
            throw new IllegalArgumentException("La arista ya existe");
        }
    }

    /**
     * Informa si ya existe una arista entre dos vertices.
     * @param nombreVertice1
     * @param nombreVertice2
     * @return booleano indicando si existe la arista.
     */
    public boolean existeArista(String nombreVertice1, String nombreVertice2)
	{
        int idVertice1 = _mapeoVertices.get(nombreVertice1).obtenerId();
        int idVertice2 = _mapeoVertices.get(nombreVertice2).obtenerId();

		return matrizDeArista[idVertice1][idVertice2] > 0;
	}

    /**
     * Informa si ya existe una arista entre dos vertices dentro de las regiones.
     * @param nombreVertice1
     * @param nombreVertice2
     * @return booleano indicando si existe la arista.
     */
    public boolean existeAristaRegiones(String nombreVertice1, String nombreVertice2)
	{
        int idVertice1 = _mapeoVertices.get(nombreVertice1).obtenerId();
        int idVertice2 = _mapeoVertices.get(nombreVertice2).obtenerId();

		return matrizDeRegiones[idVertice1][idVertice2] > 0;
	}

    /**
     * Agrega una Vertice al grafo.
     * @param nombreVertice
     * @param coordenadas
     */
    public void agregarVertice (String nombreVertice, Coordinate coordenadas, double peso) {
        
        if (existeVertice(nombreVertice)) {
            throw new IllegalArgumentException("El Vertice ya existe");
        }

        Vertice Vertice = new Vertice(_mapeoVertices.size(), nombreVertice, coordenadas, peso);
        _mapeoVertices.put(nombreVertice, Vertice);

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
     * Elimina Vertice del grafo.
     * @param nombreVertice
     */
    public void eliminarVertice (String nombreVertice) {
        if (!existeVertice(nombreVertice)) {
            throw new IllegalArgumentException("El Vertice no existe");
        }
        
        _mapeoVertices.remove(nombreVertice);
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
     * Informa si ya existe el vertice dentro del grafo.
     * @param nombreVertice
     * @return booleano indicando si el vertice ya existe.
     */
    public boolean existeVertice (String nombreVertice) {
        return _mapeoVertices.containsKey(nombreVertice);
    }

    /**
     * Obtiene la dimensión de la matriz arista actual.
     * @return dimensión de la matriz.
     */
    public int obtenerDimensionMatrizArista() {
        return matrizDeArista.length;
    }

    public Vertice obtenerVertice(String nombreVertice) {
        Vertice nodo = _mapeoVertices.get(nombreVertice);
        return nodo;
    }

    /**
     * Obtiene lista vertices del grafo.
     * @return  lista de vertices agregadas al grafo.
     */
    public ArrayList<Vertice> obtenerVertices() {
        // ArrayList<String> listaVertices = new ArrayList<>();
        // listaVertices.addAll(vertices.keySet());
        // Collections.sort(listaVertices);
        return _vertices;
    }

    /**
     * Obtiene el objeto Vertice a partir de un nombre de Vertice.
     * @param nombreVertice
     * @return objeto Vertice.
     */
    public Vertice obtenerVerticePorNombre(String nombreVertice) {
        return _mapeoVertices.get(nombreVertice);
    }

    /**
     * Obtiene Vertice por id.
     * @param id
     * @return  objeto Vertice.
     */
    public Vertice obtenerVerticePorId(int id) {
        
        for (Vertice Vertice : _mapeoVertices.values()) {
            if (Vertice.obtenerId() == id) {
                return Vertice;
            }
        }

        throw new IllegalArgumentException("Vertice not found for id: " + id);
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
     * Obtiene matriz de arista.
     * @return matriz de arista.
     */
    public int[][] obtenerMatrizArista() {
        return matrizDeArista;
    }

    /**
     * Indica si el grafo es conexo utilizando BFS.
     * @param matriz
     * @return booleano indicando si el grafo es conexo o no.
     */
    public boolean esGrafoConexo(int[][] matriz) {
        
        return BFS.grafoEsConexo(matriz);
    }
    
    /**
     * Obtiene lista de Aristaes creadas hasta el momento por el usuario.
     * @return lista de objetos de tipo arista.
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
                    
                    Vertice VerticeA = obtenerVerticePorId(i);
                    Vertice VerticeB = obtenerVerticePorId(j);

                    Aristas.add(new Arista(VerticeA, VerticeB));
                }
            }
        }
        return Aristas;
    }

    /**
     * Reinicia el grafo.
     */
    public void reiniciarGrafo() {

        matrizDeArista = new int[0][0];
        matrizDeRegiones = new int[0][0];
        _mapeoVertices.clear();
        aristas.clear();
    }

	public HashMap<Integer, ArrayList<Vertice>> obtenerIndiceConVecinos() {
		return this.indiceConVecinos;
	}

	public ArrayList<Vertice> obtenerVecinosPorNombre(String nombre) {
		Vertice vertice = this.obtenerVerticePorNombre(nombre);
		
		if(vertice == null) {
			throw new Error("Vertice no existente en grafo");
		}
		
		int[] arrayIdVecinos = this.matrizDeArista[vertice.obtenerId()];
		ArrayList<Vertice> verticesVecinos = new ArrayList<Vertice>();
		
		for(int idVecino : arrayIdVecinos) {
			verticesVecinos.add(this.obtenerVerticePorId(idVecino));
		}
		
		return verticesVecinos;
	}
}
