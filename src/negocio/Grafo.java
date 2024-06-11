package negocio;

import java.util.ArrayList;
import java.util.HashMap;

import org.openstreetmap.gui.jmapviewer.Coordinate;

public class Grafo {

    private HashMap<String, Vertice> _mapeoVertices = new HashMap<>();
    private ArrayList<Vertice> _vertices = new ArrayList<>();
    private ArrayList<Arista> _aristas = new ArrayList<>();
    private int[][] _matrizDeArista;
    
    public Grafo()
	{
        _matrizDeArista = new int[0][0];
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

        Vertice verticeA = _mapeoVertices.get(nombreVertice1);
        Vertice verticeB = _mapeoVertices.get(nombreVertice2);
        
        int idVertice1 = verticeA.obtenerId();
        int idVertice2 = verticeB.obtenerId();

        _matrizDeArista[idVertice1][idVertice2] = 1;
        _matrizDeArista[idVertice2][idVertice1] = 1;

        verticeA.agregarVecino(verticeB);
        verticeB.agregarVecino(verticeA);

        Arista arista = new Arista(_mapeoVertices.get(nombreVertice1), _mapeoVertices.get(nombreVertice2));
        _aristas.add(arista);
	}

    /**
     * Elimina una arista entre dos vertices.
     * @param nombreVertice1
     * @param nombreVertice2
     */
    public void eliminarArista(String nombreVertice1, String nombreVertice2)
	{
        if (!existeArista(nombreVertice1, nombreVertice2)) {
            throw new IllegalArgumentException("La arista no existe");
        }

        Vertice verticeA = _mapeoVertices.get(nombreVertice1);
        Vertice verticeB = _mapeoVertices.get(nombreVertice2);

        int idvertice1 = verticeA.obtenerId();
        int idvertice2 = verticeB.obtenerId();

		_matrizDeArista[idvertice1][idvertice2] = 0;
        _matrizDeArista[idvertice2][idvertice1] = 0;
        
        _aristas.remove(Arista.obtenerArista(_aristas, verticeA, verticeB));
        verticeA.eliminarVecino(verticeB);
        verticeB.eliminarVecino(verticeA);
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

		return _matrizDeArista[idVertice1][idVertice2] > 0;
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
        _vertices.add(Vertice);
        _mapeoVertices.put(nombreVertice, Vertice);

        int tamanioActual = _matrizDeArista.length;
        int nuevoTamanio = _matrizDeArista.length + 1;
        int[][] nuevaMatrizArista = new int[nuevoTamanio][nuevoTamanio];

        for (int i = 0; i < tamanioActual; i++) {
            for (int j = 0; j < tamanioActual; j++) {
                nuevaMatrizArista[i][j] = _matrizDeArista[i][j];
            }
        }

        _matrizDeArista = nuevaMatrizArista;
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
        int nuevoTamanio = _matrizDeArista.length - 1;
        int[][] nuevaMatrizArista = new int[nuevoTamanio][nuevoTamanio];

        for (int i = 0; i < nuevoTamanio; i++) {
            for (int j = 0; j < nuevoTamanio; j++) {
                nuevaMatrizArista[i][j] = _matrizDeArista[i][j];
            }
        }

        _matrizDeArista = nuevaMatrizArista;
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
     * Obtiene lista vertices del grafo.
     * @return  lista de vertices agregadas al grafo.
     */
    public ArrayList<Vertice> obtenerVertices() {
        return _vertices;
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

    /**
     * Obtiene matriz de arista.
     * @return matriz de arista.
     */
    public int[][] obtenerMatrizArista() {
        return _matrizDeArista;
    }
    
    /**
     * Obtiene lista de Aristaes creadas hasta el momento por el usuario.
     * @return lista de objetos de tipo arista.
     */
    public ArrayList<Arista> obtenerAristas() {
        return _aristas;
    }
    
    /**
     * Indica si un vertice es vercino de todos los vertices de una lista.
     * @param vertices 
     * @param nuevoVertice 
     * @return
     */
    public boolean verticeVecinoDeTodos(ArrayList<Vertice> vertices, Vertice nuevoVertice) {
        for(Vertice vertice: vertices) {
            if (!existeArista(vertice.obtenerNombre(), nuevoVertice.obtenerNombre())) {
                return false;
            }
        }
        return true;
    }

     /**
     * Reinicia el grafo.
     */
    public void reiniciarGrafo() {

        _matrizDeArista = new int[0][0];
        _mapeoVertices.clear();
        _aristas.clear();
        _vertices.clear();
    }
}
