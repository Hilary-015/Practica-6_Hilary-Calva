package Controlador.TDA_Grafo;

import Controlador.Exceptions.VerticeException;

/**
 *
 * @author HMCC
 */
public class GrafoEND<E> extends GrafoDE<E> {

    public GrafoEND(Integer numV, Class clazz) {
        super(numV, clazz);
    }

    @Override
    public void insertarArista(Integer i, Integer j, Double peso) throws VerticeException {
        if (i > 0 && j > 0 && i <= numV && j <= numV) {
            Object[] existe = existeArista(i, j);
            if (!((Boolean) existe[0])) {
                numA++;
                listaAdyacente[i].insertarCabecera(new Adyacencia(j, peso));
                listaAdyacente[j].insertarCabecera(new Adyacencia(i, peso));
            }
        } else {
            throw new VerticeException("Algun vertice ingresado no existe");
        }
    }

    @Override
    public void insertarAristaE(E i, E j, Double peso) throws Exception {
        insertarArista(obtenerCodigo(i), obtenerCodigo(j), peso);
        insertarArista(obtenerCodigo(j), obtenerCodigo(i), peso);
    }

//    public static void main(String[] args) throws Exception {
//        GrafoDE<String> gde = new GrafoDE<>(9, String.class);
//        gde.etiquetarVertice(1, "Karen");
//        gde.etiquetarVertice(2, "Adrián");
//        gde.etiquetarVertice(3, "Thais");
//        gde.etiquetarVertice(4, "Hilary");
//        gde.etiquetarVertice(5, "Eda");
//        gde.etiquetarVertice(6, "Miko");
//        gde.etiquetarVertice(8, "Noche");
//
//        try {
//            gde.insertarAristaE("Karen", "Adrián", 4.5);
//            gde.insertarAristaE("Hilary", "Eda", 2.3);
//            gde.insertarAristaE("Thais", "Karen", 1.2);
//            gde.insertarAristaE("Eda", "Karen", 7.3);
//            gde.insertarAristaE("Adrián", "Hilary", 3.3);
//            gde.insertarAristaE("Thais", "Eda", 5.3);
//            gde.insertarAristaE("Hilary", "Karen", 6.3);
//            gde.insertarAristaE("Karen", "Thais", 10.3);
//            gde.insertarAristaE("Hilary", "Thais", 16.3);
//            gde.insertarAristaE("Eda", "Miko", 17.3);
//            gde.insertarAristaE("Noche", "Hilary", 33.3);
//            gde.insertarAristaE("Karen", "Noche", 145.3);
//            gde.insertarAristaE("Thais", "Miko", 66.3);
//            gde.insertarAristaE("Karen", "Hilary", 22.3);
//            gde.insertarAristaE("Noche", "Miko", 76.3);
//
//        } catch (Exception ex) {
//        }
//        System.out.println(gde.toString());
//        System.out.println("-----------------------------------------------");
//        Integer holi[] = gde.bpa(1);
//        for (int i = 0; i < holi.length; i++) {
//            System.out.println("DATO: "+ holi[i]);
//        }
//        
//        System.out.println("===========================================================");
//        System.out.println("ALGORITMO FLOYD");
//        gde.algoritmoEtiquetas("Karen", "Noche").imprimir();
//        System.out.println("===========================================================");
//        System.out.println("CAMINOS MINIMOS");
//        gde.caminoMinimo(1, 8).imprimir();
//        System.out.println("===========================================================");
//    }
}
