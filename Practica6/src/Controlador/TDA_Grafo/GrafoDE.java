package Controlador.TDA_Grafo;

import Controlador.Exceptions.PosicionException;
import Controlador.Exceptions.VerticeException;
import Controlador.TDA_Lista.ListaEnlazada;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HMCC
 */
public class GrafoDE<E> extends GrafoD {

    protected Class<E> clazz;
    protected E etiquetas[];
    protected HashMap<E, Integer> dicVertices;

    public GrafoDE(Integer numV, Class clazz) {
        super(numV);
        this.clazz = clazz;
        etiquetas = (E[]) java.lang.reflect.Array.newInstance(this.clazz, numV + 1);
        dicVertices = new HashMap<>(numV);
    }

    public Object[] existeAristaE(E i, E j) throws Exception {
        return this.existeArista(obtenerCodigo(i), obtenerCodigo(j));
    }

    public void insertarAristaE(E i, E j, Double peso) throws Exception {
        this.insertarArista(obtenerCodigo(i), obtenerCodigo(j), peso);
    }

    public void insertarAristaE(E i, E j) throws Exception {
        this.insertarArista(obtenerCodigo(i), obtenerCodigo(j), Double.NaN);
    }

    public Integer obtenerCodigo(E etiqueta) throws Exception {
        Integer key = dicVertices.get(etiqueta);
        if (key != null) {
            return key;
        } else {
            throw new VerticeException("NO SE ENCUENTRA ETIQUETA CORRESPONDIENTE");
        }
    }

    public E obtenerEtiqueta(Integer codigo) throws Exception {
        return etiquetas[codigo];
    }

    public ListaEnlazada<Adyacencia> adyacentesDEE(E i) throws Exception {
        return adyacente(obtenerCodigo(i));
    }

    public void etiquetarVertice(Integer codigo, E etiqueta) {
        etiquetas[codigo] = etiqueta;
        dicVertices.put(etiqueta, codigo);
    }

    public Boolean modificar(E viejo, E nuevo) throws Exception {
        Integer pos = obtenerCodigo(viejo);
        etiquetas[pos] = nuevo;
        dicVertices.remove(viejo);
        dicVertices.put(nuevo, pos);
        return true;
    }

    @Override
    public String toString() {
        StringBuilder grafo = new StringBuilder();
        try {
            for (int i = 1; i <= numVertices(); i++) {
                grafo.append("VERTICE " + i + " --E-- " + obtenerEtiqueta(i).toString());
                try {
                    ListaEnlazada<Adyacencia> lista = adyacente(i);

                    for (int j = 0; j < lista.getSize(); j++) {
                        Adyacencia aux = lista.obtenerDato(j);
                        if (aux.getPeso().toString().equalsIgnoreCase(String.valueOf(Double.NaN))) {
                            grafo.append(" --- VERTICE DESTINO " + aux.getDestino() + " --E-- " + obtenerEtiqueta(aux.getDestino()));
                        } else {
                            grafo.append(" --- VERTICE DESTINO " + aux.getDestino() + " --E-- " + obtenerEtiqueta(aux.getDestino()) + " --peso-- " + aux.getPeso());
                        }
                    }
                    grafo.append("\n");
                } catch (Exception e) {
                    System.out.println("Error en toString " + e);

                }
            }
        } catch (Exception e) {
            System.out.println("Error en toString" + e);
        }
        return grafo.toString();
    }

    public void nuevoGrafoE() {
        super.nuevoGrafo();
        E aux[] = (E[]) java.lang.reflect.Array.newInstance(this.clazz, numV + 1);
        HashMap<E, Integer> nuevoDic = new HashMap<>(numV);
        for (int i = 0; i < etiquetas.length; i++) {
            aux[i] = etiquetas[i];
            nuevoDic.put(aux[i], i);
        }
        etiquetas = aux;
        dicVertices = nuevoDic;
    }

//    public static void main(String[] args){
//        try {
//            GrafoDE<String> gde = new GrafoDE<>(9, String.class);
//            gde.etiquetarVertice(1, "Karen");
//            gde.etiquetarVertice(2, "Adrián");
//            gde.etiquetarVertice(3, "Thais");
//            gde.etiquetarVertice(4, "Hilary");
//            gde.etiquetarVertice(5, "Eda");
//            gde.etiquetarVertice(6, "Miko");
//            gde.etiquetarVertice(8, "Noche");
//            
//            try {
//                gde.insertarAristaE("Karen", "Adrián", 4.5);
//                gde.insertarAristaE("Hilary", "Eda", 2.3);
//                gde.insertarAristaE("Thais", "Karen", 1.2);
//                gde.insertarAristaE("Eda", "Karen", 7.3);
//                gde.insertarAristaE("Adrián", "Hilary", 3.3);
//                gde.insertarAristaE("Thais", "Eda", 5.3);
//                gde.insertarAristaE("Hilary", "Karen", 6.3);
//                gde.insertarAristaE("Karen", "Thais", 10.3);
//                gde.insertarAristaE("Hilary", "Thais", 16.3);
//                gde.insertarAristaE("Eda", "Miko", 17.3);
//                gde.insertarAristaE("Noche", "Hilary", 33.3);
//                gde.insertarAristaE("Karen", "Noche", 145.3);
//                gde.insertarAristaE("Thais", "Miko", 66.3);
//                gde.insertarAristaE("Karen", "Hilary", 22.3);
//                gde.insertarAristaE("Noche", "Miko", 76.3);
//                
//            } catch (Exception ex) {
//            }
//            System.out.println(gde.toString());
//            System.out.println("-----------------------------------------------");
//            gde.busquedaAnchura(1).imprimir();
//            
//            
//            System.out.println("===========================================================");
//            System.out.println("ALGORITMO FLOYD");
//            gde.algoritmoEtiquetas("Karen", "Noche").imprimir();
//            System.out.println("===========================================================");
//            System.out.println("CAMINOS MINIMOS");
//            gde.caminoMinimo(1, 8).imprimir();
//            System.out.println("===========================================================");
//        } catch (Exception ex) {
//            Logger.getLogger(GrafoDE.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    
//    public static void main(String[] args) {
//        GrafoDE<String> gde = new GrafoDE<>(7, String.class);
//        gde.etiquetarVertice(1, "D");
//        gde.etiquetarVertice(2, "B");
//        gde.etiquetarVertice(3, "C");
//        gde.etiquetarVertice(4, "H");
//        gde.etiquetarVertice(5, "A");
//        gde.etiquetarVertice(6, "T");
//        gde.etiquetarVertice(7, "R");
//
//        try {
//            gde.insertarAristaE("D", "B");
//            gde.insertarAristaE("D", "C");
//            gde.insertarAristaE("B", "H");
//            gde.insertarAristaE("C", "R");
//            gde.insertarAristaE("H", "A");
//            gde.insertarAristaE("H", "T");
//            gde.insertarAristaE("R", "H");
//            gde.insertarAristaE("H", "D");
//            Integer[] anchura = gde.busquedaAnchura(1);
//            for (int i = 0; i < anchura.length; i++) {
//                System.out.println("\t" + anchura[i]);
//
//            }
//            
//            System.out.println("======================================");
//            Integer [] m = gde.toArrayDFS(4);
//            for (int i = 0; i < m.length; i++) {
//                System.out.println("-" + m[i]);
//            }
//
//        } catch (Exception ex) {
//        }
//
//        System.out.println(gde.toString());
//    }
}
