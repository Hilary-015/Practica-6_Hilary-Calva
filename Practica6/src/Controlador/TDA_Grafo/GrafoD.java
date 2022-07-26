package Controlador.TDA_Grafo;

import Controlador.Exceptions.PosicionException;
import Controlador.Exceptions.VerticeException;
import Controlador.TDA_Lista.ListaEnlazada;

/**
 *
 * @author HMCC
 */
public class GrafoD extends Grafo {

    protected Integer numV;
    protected Integer numA;
    protected ListaEnlazada<Adyacencia> listaAdyacente[];

    public GrafoD(Integer numV) {
        this.numV = numV;
        this.numA = 0;
        listaAdyacente = new ListaEnlazada[numV + 1];
        for (int i = 0; i <= this.numV; i++) {
            listaAdyacente[i] = new ListaEnlazada<>();
        }
    }

    @Override
    public Integer numVertices() {
        return this.numV;
    }

    @Override
    public Integer numAristas() {
        return this.numA;
    }

    /**
     * Permite verificar si existe una conexion entre aristas
     *
     * @param i vertice inicial
     * @param f vertice final
     * @return arreglo de objetos: en la posicion 0 regresa un boolean y en la 1
     * el peso
     * @throws VerticeException
     */
    @Override
    public Object[] existeArista(Integer i, Integer f) throws VerticeException {
        Object[] resultado = {Boolean.FALSE, Double.NaN};
        if (i > 0 && f > 0 && i <= numV && f <= numV) {
            ListaEnlazada<Adyacencia> lista = listaAdyacente[i];
            for (int j = 0; j < lista.getSize(); j++) {
                try {
                    Adyacencia aux = lista.obtenerDato(j);
                    if (aux.getDestino().intValue() == f.intValue()) {
                        resultado[0] = Boolean.TRUE;
                        resultado[1] = aux.getPeso();
                        break;
                    }
                } catch (PosicionException ex) {

                }
            }
        } else {
            throw new VerticeException("Algun vertice ingresado no existe");
        }
        return resultado;
    }

    @Override
    public Double pesoArista(Integer i, Integer f) throws VerticeException {
        Double peso = Double.NaN;
        Object[] existe = existeArista(i, f);
        if (((Boolean) existe[0])) {
            peso = (Double) existe[1];
        }
        return peso;
    }

    @Override
    public void insertarArista(Integer i, Integer j) throws VerticeException {
        insertarArista(i, j, Double.NaN);
    }

    @Override
    public void insertarArista(Integer i, Integer j, Double peso) throws VerticeException {
        if (i > 0 && j > 0 && i <= numV && j <= numV) {
            Object[] existe = existeArista(i, j);
            if (!((Boolean) existe[0])) {
                numA++;
                listaAdyacente[i].insertarCabecera(new Adyacencia(j, peso));
            }
        } else {
            throw new VerticeException("Algun vertice ingresado no existe");
        }

    }

    @Override
    public ListaEnlazada<Adyacencia> adyacente(Integer i) throws VerticeException {
        return listaAdyacente[i];
    }

    public void generarAumento(Integer numV, Integer numA, ListaEnlazada<Adyacencia>[] listaAdyacente) {
        this.numV = numV;
        this.numA = numA;
        this.listaAdyacente = listaAdyacente;
    }

    public void nuevoGrafo() {
        GrafoD copia = new GrafoD(numVertices() + 1);
        try {
            for (int i = 0; i < numVertices(); i++) {
                ListaEnlazada<Adyacencia> lista = adyacente(i);
                for (int j = 0; j < lista.getSize(); j++) {
                    Adyacencia aux = lista.obtenerDato(j);
                    copia.insertarArista(i, aux.getDestino(), aux.getPeso());
                }
            }
            generarAumento(copia.numVertices(), copia.numAristas(), copia.listaAdyacente);
        } catch (Exception e) {
            System.out.println("ERROR COPIA: " + e);
        }
    }

    public Double[][] matrizAdyacencia() throws Exception {
        Double[][] pesos = new Double[numV + 1][numV + 1];
        for (int i = 1; i <= numV; i++) {
            for (int j = 1; j <= numV; j++) {
                if (i == j) {
                    pesos[i][j] = 0.0;
                } else {
                    if (!(Boolean) existeArista(i, j)[0]) {
                        pesos[i][j] = Double.POSITIVE_INFINITY;
                    } else {
                        pesos[i][j] = pesoArista(i, j);
                    }
                }

            }
        }
        return pesos;
    }

    public ListaEnlazada<Integer> floyd(Integer origen, Integer destino) throws Exception {
        ListaEnlazada<Integer> camino = new ListaEnlazada<>();
        Double[][] distancias = matrizAdyacencia();
        Integer[][] caminos = new Integer[numV + 1][numV + 1];
        Integer[][] verticesRecorridos = new Integer[numV + 1][numV + 1];

        for (int i = 1; i <= numV; i++) {
            for (int j = 1; j <= numV; j++) {
                verticesRecorridos[i][j] = j;
                caminos[i][j] = i;
                if (i == j) {
                    verticesRecorridos[i][j] = 0;
                    caminos[i][j] = 0;
                }
            }
        }

        for (int k = 1; k <= numV; k++) {
            for (int i = 1; i <= numV; i++) {
                for (int j = 1; j <= numV; j++) {
                    if (distancias[i][k] + distancias[k][j] < distancias[i][j]) {
                        distancias[i][j] = distancias[i][k] + distancias[k][j];
                        verticesRecorridos[i][j] = caminos[k][j];
                    }
                }
            }
        }

        Integer i = origen;
        Integer j = destino;

        while (i != j) {
            camino.insertarCabecera(i);
            i = verticesRecorridos[i][j];
        }

        camino.insertarCabecera(i);
        return camino;

    }

    public Integer[] busquedaAnchura(Integer origen) throws PosicionException {
        Integer[] visitados = new Integer[numV];
        visitados[0] = origen;
        Integer cont = 1;
        int i = 0;
        boolean band = false;
        while (cont < numV) {
            i++;
            for (int j = 0; j < listaAdyacente[origen].getSize(); j++) {
                for (int k = 0; k < visitados.length; k++) {
                    if (visitados[k] == listaAdyacente[origen].obtenerDato(j).getDestino()) {
                        band = true;
                        break;
                    } else {
                        band = false;
                    }
                }
                //System.out.println(!band);
                if (!band) {
                    visitados[cont] = listaAdyacente[origen].obtenerDato(j).getDestino();
                    cont++;
                }
            }

            if (origen == numV) {
                origen = 0;
            }
            if (cont == numV) {
                break;
            }
            origen++;
            if (i > numV) {
                break;
            }
        }
        return visitados;
    }
    
    //Busqueda profundidad
    public Integer[] busquedaProfundidad(Integer origen) throws Exception {
        Integer res[] = new Integer[numVertices() + 1];
        visitados = new Integer[numVertices() + 1];
        ordenVisita = 0;
        
        for (int i = 0; i <= numVertices(); i++) {
            visitados[i] = 0;
        }

        for (int i = 1; i <= numVertices(); i++) {
            if (visitados[i] == 0) {
                toArrayDFS(i, res);
            }
        }
        Integer[] v = res;
        for (int j = 0; j < res.length; j++) {
            for (int k = 0; k < v.length; k++) {
                if (j != k) {
                    if (res[j] == v[k]) {
                        for (int i = k; i < v.length-1; i++) {
                                res[i] = v[i + 1];
                           
                        }
                    }
                }
            }
        }
        
        return res;
    }

    public void toArrayDFS(Integer origen, Integer res[]) throws Exception {
        res[ordenVisita] = origen;
        visitados[origen] = ordenVisita++;
        ListaEnlazada<Adyacencia> lista = listaAdyacente[origen];
        for (int i = 0; i < lista.getSize(); i++) {
            Adyacencia ad;          
            if (lista.comprobarDato(i)) {
                break;
            } else {
                ad = lista.obtenerDato(i);
            }
            
            lista.setCabecera(lista.getCabecera().getSiguiente());
            if (visitados[ad.getDestino()] == 0) {
                toArrayDFS(ad.getDestino(), res);
            }
        }
    }
    
    

//    public static void main(String[] args) {
//        GrafoD grafo = new GrafoD(5);
//
//        try {
//            grafo.insertarArista(1, 2, 4.0);
//            grafo.insertarArista(2, 1, 4.0);
//            grafo.insertarArista(1, 3, 8.0);
//            grafo.insertarArista(3, 1, 8.0);
//            grafo.insertarArista(2, 4, 2.0);
//            grafo.insertarArista(4, 2, 2.0);
//            grafo.insertarArista(2, 3, 1.0);
//            grafo.insertarArista(3, 4, 4.0);
//            grafo.insertarArista(4, 3, 4.0);
//            grafo.insertarArista(3, 5, 2.0);
//            grafo.insertarArista(5, 3, 2.0);
//            grafo.insertarArista(4, 5, 7.0);
//            grafo.insertarArista(5, 4, 7.0);
//
//            grafo.floyd(3, 2).imprimir();
//            System.out.println(grafo.toString());
//            grafo.caminoMinimo(3, 2).imprimir();
//        } catch (Exception e) {
//            System.out.println("ERROR: " + e);
//        }
//    }
}
