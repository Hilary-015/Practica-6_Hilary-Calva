package Controlador.TDA_Grafo;

import Controlador.Exceptions.GrafoConexionException;
import Controlador.Exceptions.PosicionException;
import Controlador.Exceptions.VerticeException;
import Controlador.TDA_Lista.ListaEnlazada;

/**
 *
 * @author HMCC
 */
public abstract class Grafo {

    public Integer visitados[];

    public Integer ordenVisita;

    public ListaEnlazada<Integer> q;
    
    public abstract Integer numVertices();

    public abstract Integer numAristas();

    public abstract Object[] existeArista(Integer i, Integer f) throws VerticeException;

    public abstract Double pesoArista(Integer i, Integer f) throws VerticeException;

    public abstract void insertarArista(Integer i, Integer j) throws VerticeException;

    public abstract void insertarArista(Integer i, Integer j, Double peso) throws VerticeException;

    public abstract ListaEnlazada<Adyacencia> adyacente(Integer i) throws VerticeException;

    @Override
    public String toString() {
        StringBuilder grafo = new StringBuilder();
        for (int i = 1; i <= numVertices(); i++) {
            try {
                grafo.append("VERTICE " + i);
                ListaEnlazada<Adyacencia> lista = adyacente(i);
                for (int j = 0; j < lista.getSize(); j++) {
                    try {
                        Adyacencia aux = lista.obtenerDato(j);
                        if (aux.getPeso().toString().equalsIgnoreCase(String.valueOf(Double.NaN))) {
                            grafo.append("------- VERTICE DESTINO " + aux.getDestino());
                        }else{
                            grafo.append("------- VERTICE DESTINO " + aux.getDestino()+"------------ peso---"+aux.getPeso());
                        }
                        
                    } catch (PosicionException ex) {
                    }
                }
                grafo.append("\n");
            } catch (VerticeException ex) {
                System.out.println("ERROR: " + ex);
            }
        }
        return grafo.toString();
    }
    
    public Boolean estaConectado(){
        Boolean bad=true;
        for (int i = 1; i <= numVertices(); i++) {
            try {
                ListaEnlazada<Adyacencia> lista = adyacente(i);
                for (int j = 0; j < lista.getSize(); j++) {
                    try {
                        Adyacencia aux = lista.obtenerDato(j);  
                        if (lista.getSize()==0){
                            bad=false;
                            break;
                        }
                    } catch (PosicionException ex) {
                        System.out.println("ERROR: "+ex);
                    }
                }
            } catch (VerticeException ex) {
                System.out.println("ERROR: " + ex);
            }
        }
        return bad;
    }
    
    private Boolean estaPintado(ListaEnlazada<Integer> lista, Integer vertice) throws PosicionException{
        Boolean band = false;
        
        for (int i = 0; i < lista.getSize(); i++) {
            if (lista.obtenerDato(i).intValue()== vertice.intValue()) {
                band = true;
                break;
            }
        }
        
        return band;
    }
    
    public ListaEnlazada caminoMinimo(Integer verticeInicial, Integer verticeFinal) throws Exception {
        ListaEnlazada<Integer> camino = new ListaEnlazada();
        if (estaConectado()) {
            ListaEnlazada pesos = new ListaEnlazada();
            Boolean finalizar = false;
            Integer inicial = verticeInicial;
            camino.insertarCabecera(inicial);
            while(!finalizar){
                ListaEnlazada<Adyacencia> adycencias = adyacente(inicial);
                Integer T = -1;
                Double peso = 100000000.0;
                for (int i = 0; i < adycencias.getSize(); i++) {
                    Adyacencia ad = adycencias.obtenerDato(i);
                    if (!estaPintado(camino, ad.getDestino())) {
                        Double pesoArista = ad.getPeso();
                        if (verticeFinal.intValue()==ad.getDestino()) {
                            T = ad.getDestino();
                            peso = ad.getPeso();
                            break;
                        }else if(pesoArista < peso){
                            T = ad.getDestino();
                            peso = pesoArista;
                        }
                    }
                }
                if (T > -1) {
                    pesos.insertarCabecera(peso);
                    camino.insertarCabecera(T);
                    inicial = T;
                } else {
                    throw new GrafoConexionException("NO SE ENCUENTRA EL CAMINO");
                }
                
                if (verticeFinal.intValue() == inicial.intValue()) {
                    finalizar=true;
                }
            }
        }else{
            throw new GrafoConexionException("EL GRAFO NO ESTA CONECTADO");
        }
        return camino;
    }
    
    //Busqueda profundidad
//    public Integer[] toArrayDFS() throws Exception {
//        Integer res[] = new Integer[numVertices() + 1];
//        visitados = new Integer[numVertices() + 1];
//        ordenVisita = 0;
//        
//        for (int i = 0; i <= numVertices(); i++) {
//            visitados[i] = 0;
//            //System.out.println("SI ESTA AQUI HOLI");
//        }
//
//        for (int i = 1; i <= numVertices(); i++) {
//            if (visitados[i] == 0) {
//                toArrayDFS(i, res);
//                //System.out.println("SI ESTA AQUI PORFIN");
//            }
//        }
//        Integer aux[] = res;
//        for (int j = 0; j < res.length; j++) {
//            for (int k = 0; k < aux.length; k++) {
//                if (j != k) {
//                    if (res[j] == aux[k]) {
//                        for (int i = k; i < aux.length-1; i++) {
//                                res[i] = aux[i + 1];
//                           
//                        }
//                    }
//                }
//            }
//        }
//        
//        return res;
//    }
//
//    public void toArrayDFS(Integer origen, Integer res[]) throws Exception {
//        res[ordenVisita] = origen;
//        visitados[origen] = ordenVisita++;
//        ListaEnlazada<Adyacencia> lista = adyacente(origen);
//        //System.out.println("SI ESTA AQUI");
//        for (int i = 0; i < lista.getSize(); i++) {
//            System.out.println(lista.obtenerDato(i));
//            Adyacencia a;
//            if (lista.obtenerDato(i) == null) {
//                break;
//            } else {
//                a = lista.obtenerDato(i);
////                System.out.println("*****************");
////                System.out.println(a);
//            }
//            lista.setCabecera(lista.getCabecera().getSiguiente());
//            if (visitados[a.getDestino()] == 0) {
//                toArrayDFS(a.getDestino(), res);
//            }
//        }
//    }
//
//    public String toStringDFS() throws Exception {
//        return arrayToString(toArrayDFS());
//    }
//
//    public String arrayToString(Integer v[]) {
//        StringBuilder sb = new StringBuilder();
//        //System.out.println("SI ESTA AQUI");
//        for (int i = 0; i < v.length; i++) {
//            sb.append(v[i] + "\n");
//            //System.out.println("SI ESTA AQUI");
//        }
//        return sb.toString();
//    }

    //Busqueda en anchura
//    public String toStringBFS() {
//        try {
//            return arrayToString(toArrayBFS());
//        } catch (Exception ex) {
//            return null;
//        }
//    }
//
//    public Integer[] toArrayBFS() throws Exception {
//        Integer res[] = new Integer[numVertices() + 1];
//        visitados = new Integer[numVertices() + 1];
//        ordenVisita = 1;
//        q = new ListaEnlazada<>();
//
//        for (int i = 0; i < numVertices(); i++) {
//            for (int j = 0; j < numVertices(); j++) {
//                if (visitados[i] == 0) {
//                    toArrayBFS(i, res);
//                }
//            }
//        }
//        return res;
//    }
//
//    public void toArrayBFS(int origen, Integer res[]) throws PosicionException, VerticeException {
//        res[ordenVisita] = origen;
//        visitados[origen] = ordenVisita++;
//        q.insertarCabecera(origen);
//        while (!q.estaVacia()) {
//            Integer u = q.eliminarDato(0).intValue();
//            ListaEnlazada<Adyacencia> lista = adyacente(u);
//            for (int i = 0; i < lista.getSize(); i++) {
//                Adyacencia a = lista.obtenerDato(i);
//                if (visitados[a.getDestino()] == 0) {
//                    res[ordenVisita] = a.getDestino();
//                    visitados[a.getDestino()] = ordenVisita++;
//                    q.insertarCabecera(a.getDestino());
//                }
//            }
//        }
//    }           
}
